name := "postcodesio"

version := "0.0.1"

scalaVersion := "2.10.5"

organization := "com.mwrobel"

resolvers += "spray repo" at "http://repo.spray.io"

//break the process in two parts
//bintrayReleaseOnPublish in ThisBuild := false

//publishMavenStyle := true

licenses += ("Apache-2.0", url("http://www.apache.org/licenses/LICENSE-2.0.html"))

publishMavenStyle := false

libraryDependencies ++= {
  val akkaV = "2.3.12"
  val sprayV = "1.3.3"
  val sprayJsonV = "1.3.2"
  val scalaTestV = "2.2.4"
  Seq(
    "io.spray" %% "spray-json" % sprayJsonV,
    "io.spray" %% "spray-can" % sprayV,
    "io.spray" %% "spray-client" % sprayV,
    "com.typesafe.akka" %% "akka-actor" % akkaV,
    "org.scalatest" %% "scalatest" % scalaTestV % "test"
//    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
  )
}
