import sbt._
import Keys._

import xerial.sbt.Pack._

object MyTask {
  lazy val settings = Seq(listMethodsTask, distTask)

  lazy val listMethods = TaskKey[Unit]("list-methods")
  def listMethodsTask  = listMethods <<= (
    sources in Compile, compile in Compile, target, streams
  ) map {
    (sources, analysis, target, s) => {
      s.log.info("Create Class Methods list")
      printf(sources.toString + "\n")
    }
  }

  // カスタムタスク
  lazy val dist = TaskKey[Unit]("dist")
  def distTask = dist <<= (
      baseDirectory in Compile,
      pack, streams
    ) map {
      (base, pack, s) =>
      {
        // リソースファイルコピー
        s.log.info("Copy resource files")
        IO.copyDirectory(base / "appData/src/main/resources", pack)
      }
    }
}
// vim: set ts=2 sw=2 et:
