import sbt._
import Keys._

import com.typesafe.sbt.SbtSite.site
import de.johoop.ant4sbt.Ant4Sbt._
import de.johoop.cpd4sbt.CopyPasteDetector._
import de.johoop.findbugs4sbt.FindBugs.findbugsSettings
import de.johoop.jacoco4sbt.{ HTMLReport, XMLReport }
import de.johoop.jacoco4sbt.JacocoPlugin.jacoco
import eu.henkelmann.sbt.JUnitXmlTestsListener
import net.ruidoblanco.checkstyle4sbt.CheckStyle._
import xerial.sbt.Pack._

object AppBuild extends Build {
  // SETTING: プロジェクト共通設定
  lazy val buildSettings = Seq(
    organization  := "com.github.tkmtmkt",
    description   := "sbtプロジェクトテンプレート",
    version       := "0.1-SNAPSHOT",
    scalaVersion  := "2.10.3",
    scalacOptions := Seq(
      "-encoding", "UTF-8",
      "-target:jvm-1.7",
      "-deprecation",
      "-unchecked"),
    javacOptions in Compile := Seq(
      "-encoding", "UTF-8",
      "-source", "1.7",
      "-target", "1.7",
      "-Xlint:all"),
    javacOptions in (Compile, doc) := Seq(
      "-encoding", "UTF-8",
      "-source", "1.7",
      "-quiet",
      "-notimestamp",
      "-linksource"),
    crossPaths := false,
    fork := true
  ) ++ antSettings ++ site.settings ++ MyEclipse.eclipseSettings

  // SETTING: サブプロジェクト共通設定
  def subProject(nameString: String, path: File) = Project(
    id = nameString,
    base = file("module/" + path),
    settings = Defaults.defaultSettings ++ buildSettings ++ site.includeScaladoc()
            ++ checkstyleSettings ++ findbugsSettings ++ cpdSettings ++ jacoco.settings)
    .settings(
      unmanagedBase := (unmanagedBase in root).value,
      retrieveManaged := true,
      libraryDependencies ++= Seq(
        "org.slf4j" % "slf4j-log4j12" % "1.7.5",
        "org.specs2" %% "specs2" % "2.3.7" % "test",
        "org.mockito" % "mockito-all" % "1.9.5" % "test",
        "org.powermock" % "powermock-module-junit4" % "1.5.3" % "test",
        "org.powermock" % "powermock-api-mockito" % "1.5.3" % "test",
        "com.novocode" % "junit-interface" % "0.10" % "test",
        "junit" % "junit" % "4.11" % "test"
      ),
      checkstyleConfigurationFile := (baseDirectory in root).value / "project/sun_checks.xml",
      testListeners := Seq(new JUnitXmlTestsListener(crossTarget.value.getAbsolutePath)),
      parallelExecution in Test := false,
      parallelExecution in jacoco.Config := false,
      jacoco.reportTitle in jacoco.Config := s"${thisProject.value.id}-${version.value}",
      jacoco.reportFormats in jacoco.Config := Seq(XMLReport("UTF-8"), HTMLReport("UTF-8")),
      fork in jacoco.Config := false
    )

  // PROJECT: ルートプロジェクト設定
  lazy val nonRoots = projects.filter(_ != root).map(p => LocalProject(p.id))
  lazy val root: Project = Project(
    id = "root",
    base = file("."),
    aggregate = nonRoots,
    settings = Defaults.defaultSettings ++ buildSettings ++ site.sphinxSupport()
            ++ packSettings ++ MyTask.settings)
    .configs(IntegrationTest)
    .settings(Defaults.itSettings : _*)
    .settings(
      libraryDependencies ++= Seq(
        "org.specs2" %% "specs2" % "2.1" % "it"
      ),
      publishArtifact := false,
      packMain        := Map("launch" -> "com.github.tkmtmkt.Main"),
      packJvmOpts     := Map("launch" -> Seq("-Xmx512m")),
      packExclude     := Seq(root.id)
    )

  // PROJECT: サブプロジェクト設定
  lazy val appMain = subProject("app-main", file("app-main")) dependsOn (appData)

  lazy val appData = subProject("app-data", file("app-data"))
}
// vim: set ts=2 sw=2 et:
