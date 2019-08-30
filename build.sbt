name := "ExampleWorkshop"

version := "0.1"


scalaVersion := "2.12.8"

val log4catsVersion = "0.4.0-M1"
val console4castsVersion = "0.8.0-M1"
val logbackClassic = "1.2.3"
val catseffectVersion = "1.2.0"
val scalatestVersion = "3.0.6"


libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-effect" % catseffectVersion,
  "org.scalatest" %% "scalatest" % scalatestVersion,
  "dev.profunktor" %% "console4cats" % console4castsVersion,

) ++ logSettings

lazy val logSettings: Seq[ModuleID] = Seq(
  "ch.qos.logback" % "logback-classic" % logbackClassic,
  "io.chrisdavenport" %% "log4cats-core" % log4catsVersion,
  "io.chrisdavenport" %% "log4cats-slf4j" % log4catsVersion
)


addCompilerPlugin("com.olegpy" %% "better-monadic-for" % "0.3.1")