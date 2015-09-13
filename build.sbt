name := "postcodesio"

version := "1.0"

scalaVersion := "2.11.7"

resolvers += "spray repo" at "http://repo.spray.io"

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
//    "org.scalatest" %% "scalatest" % "2.2.4" % "test"
//  "org.specs2" %% "specs2" % "2.2.3" % "test"
//    "com.typesafe.akka" %% "akka-testkit" % akkaV % "test",
  )
}