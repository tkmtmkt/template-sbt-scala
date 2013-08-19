import sbt._
import Keys._

import com.orrsella.sbtstats.StatsPlugin._
import com.typesafe.sbt.SbtSite.site
import de.johoop.jacoco4sbt.{ HTMLReport, XMLReport }
import de.johoop.jacoco4sbt.JacocoPlugin.jacoco
import xerial.sbt.Pack._

object AppBuild extends Build {
  // SETTING: プロジェクト共通設定
  lazy val buildSettings = Seq(
      organization  := "com.github.tkmtmkt",
      version       := "0.1-SNAPSHOT",
      description   := "sbtプロジェクトテンプレート",
      scalaVersion  := "2.10.2",
      scalacOptions := Seq(
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
        "-source", "1.7",
        "-quiet"),
      crossPaths := false,
      fork := true
    ) ++ site.settings ++ MyEclipse.eclipseSettings

  // SETTING: サブプロジェクト共通設定
  def subProject(nameString: String, path: File) = Project(
    id = nameString,
    base = path,
    settings = Defaults.defaultSettings ++ buildSettings ++ site.includeScaladoc()
            ++ jacoco.settings)
    .settings(
      unmanagedBase <<= unmanagedBase in root,
      retrieveManaged := true,
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-log4j12" % "1.7.5",
        "junit" % "junit" % "4.11" % "test",
        "org.specs2" %% "specs2" % "2.1" % "test",
        "org.mockito" % "mockito-core" % "1.9.5" % "test"
      ),
      jacoco.reportFormats in jacoco.Config := Seq(XMLReport("UTF-8"), HTMLReport("UTF-8"))
    )

  // PROJECT: ルートプロジェクト設定
  lazy val nonRoots = projects.filter(_ != root).map(p => LocalProject(p.id))
  lazy val root: Project = Project(
    id = "root",
    base = file("."),
    aggregate = nonRoots,
    settings = Defaults.defaultSettings ++ buildSettings ++ site.sphinxSupport()
            ++ packSettings ++ Seq(distTask))
    .settings(
      packMain    := Map("launch" -> "com.github.tkmtmkt.Main"),
      packJvmOpts := Map("launch" -> Seq("-Xmx512m")),
      packExclude := Seq(root.id)
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
      (out, pack, rootDir, dataDir) =>
      {
        // リソースファイルコピー
        out.log.info("Copy resource files")
        IO.copyDirectory(dataDir / "src/main/resources", pack)
      }
    }
}
// vim: set ts=2 sw=2 et:
