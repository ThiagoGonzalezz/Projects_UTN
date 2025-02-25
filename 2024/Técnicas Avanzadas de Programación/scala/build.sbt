name := "scala"

version := "1"

organization := "edu.ar.utn.tadp"

scalaVersion := "3.3.1"

resolvers += "jitpack" at "https://jitpack.io" // Add JitPack repository, to download dependency from github

libraryDependencies ++= Seq(
  "com.github.tadp-utn-frba" % "tp-dibujador-poc" % "-bcc98c0d23-1",
  "org.scalatest" %% "scalatest" % "3.2.19" % "test",
  "org.scalactic" %% "scalactic" % "3.2.19",
  "org.scalafx" %% "scalafx" % "23.0.1-R34"
)
