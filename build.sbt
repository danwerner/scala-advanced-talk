name := """saft-talk"""

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.8"

scalacOptions ++= Seq("-feature", "-target:jvm-1.8")

libraryDependencies ++= Seq(
  guice,
  "org.scalatestplus.play" %% "scalatestplus-play" % "3.1.2" % Test,
  "org.scalaz" %% "scalaz-core" % "7.2.17"
)

// Adds additional packages into Twirl
//TwirlKeys.templateImports += "de.sz.pay.controllers._"

// Adds additional packages into conf/routes
// play.sbt.routes.RoutesKeys.routesImport += "de.sz.pay.binders._"
