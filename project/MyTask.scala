import sbt._
import Keys._

import xerial.sbt.Pack._

object MyTask {
  // カスタムタスク
  lazy val dist = TaskKey[Unit]("dist")
  def distTask = dist <<= (
      streams, pack,
      baseDirectory in Compile
    ) map {
      (out, pack, base) =>
      {
        // リソースファイルコピー
        out.log.info("Copy resource files")
        IO.copyDirectory(base / "appData/src/main/resources", pack)
      }
    }
}
// vim: set ts=2 sw=2 et:
