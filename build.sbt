import io.gatling.sbt.GatlingPlugin

val scala_version   = "2.11.8"
val akkaVersion     = "2.5.12"
val gatlingVersion  = "2.2.5"

lazy val root = (project in file("."))
  .enablePlugins(GatlingPlugin)
  .settings(
    name := "gatling-test",
    version := "0.1-SNAPSHOT",
    organization := "com.github.allenkim80",
    scalaVersion := scala_version,
    resolvers += "com.github.allenkim80" at "https://oss.sonatype.org/content/repositories/snapshots/",
    libraryDependencies ++= {
      Seq(
        "io.gatling"          % "gatling-http"                % gatlingVersion,
        "io.gatling"          % "gatling-test-framework"      % gatlingVersion  % "test",
        "io.gatling.highcharts" % "gatling-charts-highcharts"  % gatlingVersion  % "test",
        "io.sentry"             % "sentry"                      % "1.6.3",
        "com.google.code.gson" % "gson"                       % "2.8.5",
        "com.github.allenkim80" % "gatling-sentry-extension_2.11" % "0.1.16-SNAPSHOT"
      )
    }
  )
