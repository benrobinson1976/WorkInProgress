name := "Monoids"

version := "1.0"

scalaVersion := "2.10.4"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
  "ScalaTools Releases" at "https://oss.sonatype.org/content/groups/scala-tools",
  "Akka Snapshot Repository" at "http://repo.akka.io/releases/"
)

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.0" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test",
  "org.mongodb" %% "casbah" % "2.6.4",
  "com.novus" %% "salat" % "1.9.5",
  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
  "com.typesafe.akka" %% "akka-slf4j" % "2.3.5",
  "com.typesafe.akka" %% "akka-testkit" % "2.3.5" % "test",
  "org.apache.spark" %% "spark-core" % "1.0.2",
  "org.apache.spark" %% "spark-mllib" % "1.0.2"
)