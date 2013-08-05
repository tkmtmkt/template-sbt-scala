template-scala-sbt
==================

scala project template using sbt

ディレクトリ構成
----------------

### ソースコード

    template-scala-sbt/
    ├─lib/
    ├─src/
    │  └─dist/
    │      └─bin/
    ├─main/
    │  └─src/
    │      ├─main/
    │      │  ├─java/
    │      │  ├─resources/
    │      │  └─scala/
    │      └─test/
    │          └─scala/
    ├─.gitignore
    └─README.md


### sbt設定

    template-scala-sbt/
    ├─project/
    │  ├─build.properties
    │  ├─Build.scala
    │  ├─Package.scala
    │  └─plugins.sbt
    └─sbt/
        ├─boot/
        ├─repository/
        ├─sbt-launch.jar
        ├─sbt
        └─sbt.bat

<!-- vim: set ts=4 sw=4 et: -->
