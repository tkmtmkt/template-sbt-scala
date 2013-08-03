import sbt._
import Keys._

object ProjectBuild extends Build {
    override lazy val settings = super.settings ++ buildSettings
    def buildSettings = Seq(
        version := "0.1-SNAPSHOT",
        organization := "com.github.tkmtmkt",
        description := "",
        javaOptions in Compile := Seq(
            "-encoding", "UTF-8", "-source", "1.7", "-target", "1.7", "-Xlint:all,-unchecked"),
        javaOptions in doc := Seq(
            "-encoding", "UTF-8", "-source", "1,7", "-quiet"),
        scalaVersion := "2.10.2",
        scalacOptions ++= Seq("-encoding", "UTF-8", "-deprecation", "-unchecked"),
        fork := true,
        crossPaths := false,
        resolvers += Resolver.mavenLocal
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
    lazy val root: Project = Project("root", file("."), aggregate = nonRoots,
        settings = Defaults.defaultSettings ++
            PackageTask.distSettings ++
            Seq(PackageTask.packageDistTask,
                packageDataTask
            )
    )

    // サブプロジェクト設定
    lazy val appMain = defaultProject("app-main", file("app-main")) dependsOn (appData)
    lazy val appData = defaultProject("app-data", file("app-data"))

    // カスタムタスク
    lazy val packageData = TaskKey[Unit]("package-data")
    def packageDataTask = packageData <<= (streams, PackageTask.packageDist, baseDirectory in appData in Compile) map {
        (out, dist, dataDir) => {
            out.log.info("Copy resource files")
            IO.copyDirectory(dataDir / "src/main/resources", dist)
        }
    }
}

// vim: set ts=4 sw=4 et:
