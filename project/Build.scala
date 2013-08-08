import sbt._
import Keys._

object ProjectBuild extends Build {
  // SETTING: プロジェクト共通設定
  lazy val buildSettings = Seq(
      organization  := "com.github.tkmtmkt",
      version       := "0.1-SNAPSHOT",
      description   := "",
      scalaVersion  := "2.10.2",
      scalacOptions ++= Seq(
        "-encoding", "UTF-8",
        "-deprecation",
        "-unchecked"),
      javacOptions in Compile := Seq(
        "-encoding", "UTF-8",
        "-source", "1.7",
        "-target", "1.7",
        "-Xlint:all,-unchecked"),
      javacOptions in (Compile, doc) := Seq(
        "-encoding", "UTF-8",
        "-source", "1,7",
        "-quiet"),
      fork := true,
      crossPaths := false
    )

  // SETTING: サブプロジェクト共通設定
  def subProject(nameString: String, path: File) =
    Project(nameString, path,
      settings = Defaults.defaultSettings ++ buildSettings ++ eclipseSettings) settings(
        unmanagedBase <<= unmanagedBase in root,
        libraryDependencies ++= Seq(
          "org.slf4j" % "slf4j-log4j12" % "1.7.5",
          "junit" % "junit" % "4.11" % "test",
          "org.specs2" %% "specs2" % "2.1" % "test",
          "org.mockito" % "mockito-core" % "1.9.5" % "test"
        )
      )

  // PROJECT: ルートプロジェクト設定
  import xerial.sbt.Pack._
  lazy val nonRoots = projects.filter(_ != root).map(p => LocalProject(p.id))
  lazy val root: Project = Project("root", file("."), aggregate = nonRoots,
      settings = Defaults.defaultSettings ++ buildSettings ++ packSettings ++ Seq(
        packMain    := Map("launch" -> "com.github.tkmtmkt.Main"),
        packJvmOpts := Map("launch" -> Seq("-Xmx512m")),
        distTask
      )
    )

  // PROJECT: サブプロジェクト設定
  lazy val appMain = subProject("app-main", file("app-main")) dependsOn (appData)
  lazy val appData = subProject("app-data", file("app-data"))

  // カスタムタスク
  lazy val dist = TaskKey[Unit]("dist")
  def distTask = dist <<= (
      streams, pack,
      baseDirectory in root in Compile,
      baseDirectory in appData in Compile
    ) map {
      (out, dist, rootDir, dataDir) =>
      {
        // 配布ファイルコピー
        out.log.info("Copy distribution files")
        IO.copyDirectory(rootDir / "src/dist", dist)

        // リソースファイルコピー
        out.log.info("Copy resource files")
        IO.copyDirectory(dataDir / "src/main/resources", dist)
      }
    }

  // Eclipseクラスパス変数
  val eclipseClasspathVar = "PROJECT_HOME"

  // PLUGIN: sbteclipse設定
  import com.typesafe.sbteclipse.plugin.EclipsePlugin._
  import EclipseKeys._
  lazy val eclipseSettings = Seq(
      executionEnvironment          := Some(EclipseExecutionEnvironment.JavaSE17),
      skipParents in ThisBuild      := true,
      skipProject                   := false,
      projectTransformerFactories   := myProjectTransformerFactories,
      projectFlavor                 := EclipseProjectFlavor.Scala,
      classpathTransformerFactories := myClasspathTransformerFactories,
      createSrc                     := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
      withSource                    := true,
      relativizeLibs                := false
    )

  import scala.xml.{ Attribute, Elem, MetaData, Node, Null, Text }
  import scala.xml.transform.RewriteRule
  import com.typesafe.sbteclipse.core.{ Validation, setting }

  lazy val myClasspathTransformerFactories = Seq(new EclipseTransformerFactory[RewriteRule] {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] =
    {
      setting(Keys.baseDirectory in ThisBuild, state) map (baseDir =>
        new RewriteRule {

          // ビルドツリーのトップディレクトリ
          val buildDir = baseDir.getAbsolutePath

          private val CpEntry = "classpathentry"

          /**
           * classpathentry ノードの変換
           */
          override def transform(node: Node): Seq[Node] =
            node match {
              case Elem(prefix, CpEntry, attributes, scope, child @ _*) if containsBuildDir(attributes) =>
                Elem(prefix, CpEntry, newAttributes(attributes), scope, child: _*)
              case other =>
                other
            }

          /**
           * ライブラリパスがビルドディレクトリを含むか判定
           */
          private def containsBuildDir(attributes: MetaData) =
            attributes("kind") == Text("lib") &&
              (Option(attributes("path").text) map (_ contains buildDir) getOrElse false)

          /**
           * ビルドディレクトリをEclipseクラスパス変数に変換
           */
          private def newAttributes(attributes: MetaData): MetaData =
            attributes match {
              case Attribute("kind", Text("lib"), next) =>
                Attribute("kind", Text("var"), newAttributes(next))
              case Attribute(key, Text(value), next) =>
                val newValue = value.replace(buildDir, eclipseClasspathVar)
                Attribute(key, Text(newValue), newAttributes(next))
              case other =>
                other
            }
        }
      )
    }
  })

  lazy val myProjectTransformerFactories = Seq(new EclipseTransformerFactory[RewriteRule] {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] =
      setting(baseDirectory in ref, state) map (baseDir =>
        new RewriteRule {
        })
  })
}

// vim: set ts=2 sw=2 et:
