template-scala-sbt
==================

scala project template using sbt

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


### 

    template-scala-sbt/
    ├─app-main/
    │  └─target/
    │      ├─app-main-0.1-SNAPSHOT.jar           package-bin で作成する
    │      ├─app-main-0.1-SNAPSHOT-sources.jar   package-src で作成する
    │      ├─app-main-0.1-SNAPSHOT-javadoc.jar   package-doc で作成する
    │      └─app-main-0.1-SNAPSHOT-site.zip      package-site で作成する
    ├─lib_managed/            依存ライブラリのコピー
    ├─sbt/
    │  ├─boot/               自動ダウンロードしたsbt本体
    │  └─repository/         依存ライブラリのダウンロードキャッシュ
    └─target/
        ├─dist/               dist で作成する
        └─pack/               pack で作成する
            ├─bin/            src/pack/binの内容
            └─lib/            依存ライブラリとビルドしたjarファイル


参考
----

* [始めるsbt](http://scalajp.github.io/sbt-getting-started-guide-ja/)
* [sbt - sbt Documentation](http://www.scala-sbt.org/)

<!-- vim: set ts=4 sw=4 et: -->
