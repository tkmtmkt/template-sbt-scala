template-scala-sbt
==================

scala project template using sbt

概要
----

Eclipseプロジェクト設定

    eclipse

サイト生成

    make-site
    preview-site
    package-site

配布ファイル生成

    dist


ディレクトリ構成
----------------

### ソースコード

    template-scala-sbt/
    ├─lib/
    ├─src/
    │  └─dist/
    │      └─bin/
    ├─app-main/
    │  └─src/
    │      ├─main/
    │      │  ├─java/
    │      │  ├─resources/
    │      │  └─scala/
    │      ├─sphinx/
    │      ├─test/
    │      │  ├─java/
    │      │  ├─resources/
    │      │  └─scala/
    │      └─build.sbt
    ├─app-main/
    │  └─src/
    │      ├─main/
    │      │  ├─java/
    │      │  ├─resources/
    │      │  └─scala/
    │      └─test/
    │          ├─java/
    │          ├─resources/
    │          └─scala/
    ├─.gitignore
    └─README.md


### sbt設定

    template-scala-sbt/
    ├─project/
    │  ├─Build.scala
    │  ├─MyEclipse.scala
    │  ├─build.properties
    │  └─plugins.sbt
    ├─sbt/
    │  ├─boot/
    │  ├─repository/
    │  └─sbt-launch.jar
    ├─sbtl
    └─sbtl.bat

<!-- vim: set ts=4 sw=4 et: -->
