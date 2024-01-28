import sbt.Keys.resolvers

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.1"

lazy val root = (project in file("."))
  .settings(
    name := "qbittorrent-api-wrapper",
    resolvers += "Akka library repository" at "https://repo.akka.io/maven",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http" % "10.6.0"
    )
  )
