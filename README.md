template-sbt-scala
==================

sbt project template for scala

概要
----

### 操作

sbtコンソール起動

    sbtl

コンパイル

    compile

jarファイル作成

    package

サイト生成

    make-site

    preview-site

    package-site

配布ファイル生成

    pack

Eclipseプロジェクト設定ファイル生成

    eclipse

※操作説明は利用者を想定してワークフロー毎に記述しないと
  意味ないので、ここの記述は書き直す。


ディレクトリ構成
----------------

### ソースコード

    template-scala-sbt/
    ├─lib/                    自動管理しない外部ライブラリ
    │  └─.gitkeep
    ├─src/
    │  ├─pack/               packタスクで配布対象に含めるファイル
    │  │  └─bin/
    │  └─sphinx/             make-siteタスクのドキュメントソース
    │      ├─conf.py
    │      ├─chapter1.rst
    │      └─index.rst
    ├─app-data/               サブプロジェクト
    │  └─src/
    │      ├─main/
    │      │  ├─resources/
    │      │  └─scala/
    │      ├─test/
    │      │  └─scala/
    │      └─version.sbt
    ├─app-main/               サブプロジェクト
    │  └─src/
    │      ├─main/
    │      │  ├─resources/
    │      │  └─scala/
    │      ├─test/
    │      │  └─scala/
    │      └─version.sbt
    ├─.gitignore
    └─README.md               このファイル


### sbt設定

    template-scala-sbt/
    ├─project/
    │  ├─build.properties    sbt設定
    │  ├─plugins.sbt         プラグイン設定
    │  ├─Build.scala         ビルド設定
    │  └─MyEclipse.scala     sbteclipseプラグインのカスタマイズ
    ├─sbt/
    │  └─sbt-launch.jar
    ├─sbtl                    sbt起動スクリプト（Unix）
    └─sbtl.bat                sbt起動スクリプト（Windows）


### sbt出力

    template-scala-sbt/
    ├─app-data/
    │  └─target/
    │      ├─classes/        compile タスクの出力
    │      ├─api/            doc タスクの出力
    │      ├─site/           make-site タスクの出力
    │      ├─app-main-0.1-SNAPSHOT.jar           package-bin タスクの出力
    │      ├─app-main-0.1-SNAPSHOT-sources.jar   package-src タスクの出力
    │      ├─app-main-0.1-SNAPSHOT-javadoc.jar   package-doc タスクの出力
    │      └─app-main-0.1-SNAPSHOT-site.zip      package-site タスクの出力
    ├─app-main/
    │  └─target/
    ├─lib_managed/            update タスクの出力
    ├─sbt/
    │  ├─boot/               自動ダウンロードしたsbt本体
    │  └─repository/         依存ライブラリのダウンロードキャッシュ
    └─target/
        ├─dist/               dist タスクの出力
        └─pack/               pack タスクの出力
            ├─bin/            src/pack/binの内容
            └─lib/            依存ライブラリとビルドしたjarファイル


参考
----

### sbt説明
* [sbt - sbt Documentation](http://www.scala-sbt.org/)
* [始めるsbt](http://scalajp.github.io/sbt-getting-started-guide-ja/)

### プラグイン

#### IDE
* [sbteclipse](https://github.com/typesafehub/sbteclipse)

#### コード生成
* [sbt-antlr](https://github.com/stefri/sbt-antlr)

#### ソースコード静的解析
* [cpd4sbt](https://bitbucket.org/jmhofer/cpd4sbt)
* [findbugs4sbt](https://bitbucket.org/jmhofer/findbugs4sbt)
* [scalastyle](https://github.com/scalastyle/scalastyle-sbt-plugin)
* [sbt-stats](https://github.com/orrsella/sbt-stats)

#### テスト

#### カバレッジ
* [cobertura4sbt](https://bitbucket.org/jmhofer/cobertura4sbt)
* [jacoco4sbt](https://bitbucket.org/jmhofer/jacoco4sbt)

#### ドキュメンテーション
* [sbt-site](https://github.com/sbt/sbt-site)

#### リリース
* [sbt-release](https://github.com/sbt/sbt-release)
* [sbt-pack](https://github.com/xerial/sbt-pack)

#### その他
* [ant4sbt](https://bitbucket.org/jmhofer/ant4sbt)

<!-- vim: set ts=4 sw=4 et: -->
