import sbt.Keys.resolvers

ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "3.3.5"

resolvers += "Akka library repository" at "https://repo.akka.io/maven"
lazy val akkaHttpVersion = "10.6.0"
lazy val akkaVersion     = "2.9.0"

lazy val root = (project in file("."))
  .settings(
    name := "qbittorrent-api-wrapper",
    libraryDependencies ++= Seq(
      "com.typesafe.akka" %% "akka-http"        % akkaHttpVersion,
      "com.typesafe.akka" %% "akka-actor-typed" % akkaVersion,
      "com.typesafe.akka" %% "akka-stream"      % akkaVersion
    )
  )
