name := "Monoids"

version := "1.0"

scalaVersion := "2.10.2"

resolvers ++= Seq(
  "Sonatype Releases" at "http://oss.sonatype.org/content/repositories/releases"
)

libraryDependencies ++= Seq(
  "org.scalacheck" %% "scalacheck" % "1.11.0" % "test",
  "org.scalatest" % "scalatest_2.10" % "2.0" % "test"
)