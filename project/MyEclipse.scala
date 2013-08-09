import sbt._
import Keys._

object MyEclipse
{
  // Eclipseクラスパス変数
  private val eclipseClasspathVar = "PROJECT_HOME"

  // PLUGIN: sbteclipse設定
  import com.typesafe.sbteclipse.plugin.EclipsePlugin._
  import EclipseKeys._
  lazy val eclipseSettings = Seq(
      executionEnvironment          := Some(EclipseExecutionEnvironment.JavaSE17),
      skipParents in ThisBuild      := true,
      skipProject                   := false,
      projectTransformerFactories   := Seq(ProjectTransformerFactory),
      projectFlavor                 := EclipseProjectFlavor.Scala,
      classpathTransformerFactories := Seq(ClasspathEntryTransformerFactory),
      createSrc                     := EclipseCreateSrc.Default + EclipseCreateSrc.Resource,
      withSource                    := true,
      relativizeLibs                := false
    )

  import scala.xml.{ Attribute, Elem, MetaData, Node, Null, Text }
  import scala.xml.transform.RewriteRule
  import com.typesafe.sbteclipse.core.{ Validation, setting }

  /**
   * プラグインが生成する.classpathファイルをカスタマイズするための設定
   */
  private object ClasspathEntryTransformerFactory extends EclipseTransformerFactory[RewriteRule]
  {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] =
      setting(Keys.baseDirectory in ThisBuild, state) map (baseDir =>
        new ClasspathEntryRewriteRule(baseDir)
      )
  }

  private class ClasspathEntryRewriteRule(baseDir: File) extends RewriteRule
  {
    // ビルドツリーのトップディレクトリ
    private val buildDir = baseDir.getAbsolutePath

    private val CpEntry = "classpathentry"

    /**
     * <classpathentry /> ノードを書き換える
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

  /**
   * プラグインが生成する.classpathファイルをカスタマイズするための設定
   */
  private object ProjectTransformerFactory extends EclipseTransformerFactory[RewriteRule]
  {
    override def createTransformer(ref: ProjectRef, state: State): Validation[RewriteRule] =
      setting(Keys.baseDirectory in ThisBuild, state) map (baseDir =>
        new ProjectRewriteRule(baseDir)
      )
  }

  private class ProjectRewriteRule(baseDir: File) extends RewriteRule
  {
    // 何もしない
    override def transform(node: Node): Seq[Node] = node
  }
}
// vim: set ts=2 sw=2 et:
