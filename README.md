template-sbt-scala
==================

sbt project template for scala

概要
----

### 操作

sbtコンソール起動

    sbtl

sbtコンソールで使用するタスク

    eclipse         Eclipseプロジェクト設定ファイルを生成する
    compile         コンパイル
    checkstyle      ソースコードの静的解析を行う
    findbugs        コンパイル後のクラスファイルを解析する
    test            ユニットテスト
    jacoco:cover    カバレッジを計測する
    make-site       サイト用のHTMLファイルを生成する
    preview-site    サイト用のHTMLファイルをブラウザで閲覧する
    package-bin     バイナリのjarファイルを作成する
    package-src     ソースコードのjarファイルを作成する
    package-doc     javadocのjarファイルを作成する
    package-site    サイト用のHTMLファイルのjarファイルを作成する
    pack            配布ファイルを生成する
    stats           ソースコードの統計を表示する

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
    │  ├─build.properties    sbt起動設定
    │  ├─Build.scala         ビルド設定
    │  ├─plugins.sbt         プラグイン設定
    │  ├─MyEclipse.scala     sbteclipseプラグインのカスタマイズ
    │  └─sun_checks.xml      checkstyle設定ファイル
    ├─sbt/
    │  ├─plugins/
    │  │  └─plugins.sbt     グローバルプラグイン設定
    │  ├─build.sbt           グローバルビルド設定
    │  └─sbt-launch.jar
    ├─sbtl                    sbt起動スクリプト（Unix）
    └─sbtl.bat                sbt起動スクリプト（Windows）


### sbt出力

    template-scala-sbt/
    ├─app-data/
    │  └─target/
    ├─app-main/
    │  └─target/
    │      ├─classes/        compile タスクの出力
    │      ├─checkstyle/     checkstyle タスクの出力
    │      │  └─checkstyle.xml
    │      ├─findbugs/       findbugs タスクの出力
    │      │  └─findbugs.xml
    │      ├─jacoco/         jacoco:cover タスクの出力
    │      │  ├─classes/
    │      │  ├─html/
    │      │  ├─jacoco.exec
    │      │  └─jacoco.xml
    │      ├─api/            doc タスクの出力
    │      ├─site/           make-site タスクの出力
    │      ├─app-main-0.1-SNAPSHOT.jar           package-bin タスクの出力
    │      ├─app-main-0.1-SNAPSHOT-sources.jar   package-src タスクの出力
    │      ├─app-main-0.1-SNAPSHOT-javadoc.jar   package-doc タスクの出力
    │      └─app-main-0.1-SNAPSHOT-site.zip      package-site タスクの出力
    ├─lib_managed/            update タスクの出力
    ├─sbt/
    │  ├─boot/               自動ダウンロードしたsbt本体
    │  └─repository/
    │      ├─cache/          依存ライブラリのダウンロードキャッシュ
    │      └─local/          ローカルリポジトリ（publish-local タスク）
    └─target/
        ├─dist/               dist タスクの出力
        ├─pack/               pack タスクの出力
        │  ├─bin/            src/pack/binの内容
        │  └─lib/            依存ライブラリとビルドしたjarファイル
        └─site/               make-site タスクの出力


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
* [checkstyle4sbt](https://github.com/drodriguez/checkstyle4sbt)
* [findbugs4sbt](https://bitbucket.org/jmhofer/findbugs4sbt)
* [scalastyle](https://github.com/scalastyle/scalastyle-sbt-plugin)
* [sbt-stats](https://github.com/orrsella/sbt-stats)
* [cpd4sbt](https://bitbucket.org/jmhofer/cpd4sbt)

#### テスト
* [junit-interface](https://github.com/szeiger/junit-interface)
* [junit_xml_listener](https://github.com/ijuma/junit_xml_listener)
* [sbt-simple-junit-xml-reporter-plugin](https://github.com/bseibel/sbt-simple-junit-xml-reporter-plugin)

#### カバレッジ
* [jacoco4sbt](https://bitbucket.org/jmhofer/jacoco4sbt)
* [cobertura4sbt](https://bitbucket.org/jmhofer/cobertura4sbt)

#### ドキュメンテーション
* [sbt-site](https://github.com/sbt/sbt-site)

#### リリース
* [sbt-pack](https://github.com/xerial/sbt-pack)
* [sbt-release](https://github.com/sbt/sbt-release)

#### その他
* [ant4sbt](https://bitbucket.org/jmhofer/ant4sbt)

<!-- vim: set ts=4 sw=4 et: -->
