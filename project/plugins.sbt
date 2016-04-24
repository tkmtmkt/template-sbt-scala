//eclipse設定ファイル作成
addSbtPlugin("com.typesafe.sbteclipse" % "sbteclipse-plugin" % "2.5.0")

//antタスクを実行する
addSbtPlugin("de.johoop" % "ant4sbt" % "1.1.2")

//Checkstyle（ソースコードの静的解析を行う）
addSbtPlugin("net.ruidoblanco" % "checkstyle4sbt" % "0.0.2")

//FindBugs（コンパイル後のクラスファイルを解析する）
addSbtPlugin("de.johoop" % "findbugs4sbt" % "1.4.0")

//PMD/CPD（コピー／ペースト箇所を検出する）
addSbtPlugin("de.johoop" % "cpd4sbt" % "1.1.5")

//JUnitテスト結果をXMLファイルに出力
addSbtPlugin("eu.henkelmann" % "junit_xml_listener" % "0.4-SNAPSHOT")

//カバレッジ計測（Java Code Coverage Library）
addSbtPlugin("de.johoop" % "jacoco4sbt" % "2.1.6")

//ドキュメント作成
addSbtPlugin("com.typesafe.sbt" % "sbt-site" % "0.8.2")

//配布ファイル作成
addSbtPlugin("org.xerial.sbt" % "sbt-pack" % "0.7.7")

// vim: set ts=2 sw=2 et:
