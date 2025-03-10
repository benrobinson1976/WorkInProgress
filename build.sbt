
name := "Monoids"

version := "1.0"

ThisBuild / scalaVersion := "2.13.15"

//resolvers ++= Seq(
//  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases",
//  "Sonatype Snapshots" at "http://oss.sonatype.org/content/repositories/snapshots",
//  "ScalaTools Releases" at "https://oss.sonatype.org/content/groups/scala-tools",
//  "Akka Snapshot Repository" at "http://repo.akka.io/releases/"
//)

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.scalacheck" %% "scalacheck" % "1.14.1" % "test",
 // "org.mongodb" %% "casbah" % "3.1.1" % "test"
  //"com.novus" %% "salat" % "1.9.5"
//  "com.typesafe.akka" %% "akka-actor" % "2.3.5",
//  "com.typesafe.akka" %% "akka-slf4j" % "2.3.5",
//  "com.typesafe.akka" %% "akka-testkit" % "2.3.5" % "test",
//  "org.apache.spark" %% "spark-core" % "1.0.2",
//  "org.apache.spark" %% "spark-mllib" % "1.0.2"
)