import sbt._
import Keys._
import com.typesafe.sbteclipse.plugin.EclipsePlugin._

object ProjectBuild extends Build {
    override lazy val settings = super.settings ++ buildSettings
    def buildSettings = Seq(
        version := "0.1-SNAPSHOT",
        organization := "com.github.tkmtmkt",
        description := "",
        javacOptions in Compile := Seq(
            "-encoding", "UTF-8",
            "-source", "1.7",
            "-target", "1.7",
            "-Xlint:all,-unchecked"),
        javacOptions in doc := Seq(
            "-encoding", "UTF-8",
            "-source", "1,7",
            "-quiet"),
        scalaVersion := "2.10.2",
        scalacOptions ++= Seq(
            "-encoding", "UTF-8",
            "-deprecation",
            "-unchecked"),
        fork := true,
        crossPaths := false,
        EclipseKeys.executionEnvironment := Some(EclipseExecutionEnvironment.JavaSE17),
        EclipseKeys.skipParents in ThisBuild := true,
        EclipseKeys.withSource := true,
        //EclipseKeys.classpathTransformerFactories := Seq(
        //),
        EclipseKeys.createSrc := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
        EclipseKeys.projectFlavor := EclipseProjectFlavor.Scala,
        EclipseKeys.relativizeLibs := false,
        EclipseKeys.skipProject := false
    )

    // サブプロジェクト共通
    def defaultProject(nameString: String, path: File) = Project(nameString, path) settings(
        unmanagedBase <<= unmanagedBase in root,
        libraryDependencies ++= Seq(
            "org.slf4j" % "slf4j-log4j12" % "1.7.5",
            "junit" % "junit" % "4.11" % "test",
            "org.specs2" %% "specs2" % "2.1" % "test",
            "org.mockito" % "mockito-core" % "1.9.5" % "test"
        )
    )

    // ルートプロジェクト設定
    lazy val nonRoots = projects.filter(_ != root).map(p => LocalProject(p.id))
    lazy val root = Project("root", file("."), aggregate = nonRoots,
        settings = Defaults.defaultSettings ++
            PackageTask.distSettings ++
            Seq(PackageTask.packageDistTask, distTask)
    )

    // サブプロジェクト設定
    lazy val appMain = defaultProject("app-main", file("app-main")) dependsOn (appData)
    lazy val appData = defaultProject("app-data", file("app-data"))

    // カスタムタスク
    lazy val dist = TaskKey[Unit]("dist")
    def distTask = dist <<= (streams, PackageTask.packageDist,
        baseDirectory in root in Compile, baseDirectory in appData in Compile) map {
        (out, dist, rootDir, dataDir) => {

            out.log.info("Copy distribution files")
            IO.copyDirectory(rootDir / "src/dist", dist)

            out.log.info("Copy resource files")
            IO.copyDirectory(dataDir / "src/main/resources", dist)
        }
    }
}

// vim: set ts=4 sw=4 et:
