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
  def subProject(nameString: String, path: File) = Project(nameString, path,
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
  def distTask = dist <<= (streams, pack,
    baseDirectory in root in Compile, baseDirectory in appData in Compile) map {
      (out, dist, rootDir, dataDir) => {

      // 配布ファイルコピー
      out.log.info("Copy distribution files")
      IO.copyDirectory(rootDir / "src/dist", dist)

      // リソースファイルコピー
      out.log.info("Copy resource files")
      IO.copyDirectory(dataDir / "src/main/resources", dist)
    }
  }

  // PLUGIN: sbteclipse設定
  import com.typesafe.sbteclipse.plugin.EclipsePlugin._
  import EclipseKeys._
  lazy val eclipseSettings = Seq(
    executionEnvironment          := Some(EclipseExecutionEnvironment.JavaSE17),
    classpathTransformerFactories := myClasspathTransformerFactories,
    projectTransformerFactories   := myProjectTransformerFactories,
    skipParents in ThisBuild      := true,
    skipProject                   := false,
    createSrc       := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
    projectFlavor   := EclipseProjectFlavor.Scala,
    relativizeLibs  := false,
    withSource      := true
  )

  import scala.xml.{ Attribute, Elem, MetaData, Node, Null, Text }
  import scala.xml.transform.RewriteRule
  import com.typesafe.sbteclipse.core.{ Validation, setting }
  lazy val myClasspathTransformerFactories = Seq(new EclipseTransformerFactory[RewriteRule] {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] = {
      setting(crossTarget in ref, state) map (ct =>
        new RewriteRule {
          val projectHome = ((baseDirectory in root).evaluate _).toString
          println("----------------------------------------")
          println(projectHome)
          override def transform(node: Node): Seq[Node] = node match {
            case elem if (elem.label == "classpathentry") =>
              if (elem.attributes.toString.contains(projectHome)) {
                println(elem.attributes)
                //****************************************
                val newAttributes = elem.attributes map (attribute => attribute match {
                  case Attribute("kind", Text("lib"), next) =>
                    Attribute("kind", Text("var"), null)
                  case Attribute(key, Text(value), next) =>
                    val newValue = value.replace(projectHome, "PROJECT_HOME")
                    Attribute(key, Text(newValue), null)
                  case other => other
                })
                implicit def iterableToMetaData(attributes: Iterable[MetaData]): MetaData = attributes match {
                  case head :: tail => head.copy(next = iterableToMetaData(tail))
                  case Nil => Null
                }
                Elem(elem.prefix, "classpathentry", newAttributes, elem.scope, elem.child: _*)
                //****************************************
              } else {
                elem
              }
            case other => other
          }
        }
      )
    }
  })

  lazy val myProjectTransformerFactories = Seq(new EclipseTransformerFactory[RewriteRule] {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] = {
      setting(baseDirectory in ref, state) map (baseDir =>
        new RewriteRule {
        }
      )
    }
  })
}

// vim: set ts=2 sw=2 et:
