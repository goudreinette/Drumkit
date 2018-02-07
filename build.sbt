name := "Drumkit"

version := "0.1"

scalaVersion := "2.12.4"

addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full)
addSbtPlugin("com.artima.supersafe" % "sbtplugin" % "1.1.3")

resolvers += "Artima Maven Repository" at "http://repo.artima.com/releases"


libraryDependencies += "org.scalafx" %% "scalafx" % "8.0.144-R12"
libraryDependencies += "org.scalafx" %% "scalafxml-core-sfx8" % "0.4"

libraryDependencies += "com.github.tototoshi" %% "scala-csv" % "1.3.5"
libraryDependencies += "commons-io" % "commons-io" % "2.5"

libraryDependencies += "org.scalactic" %% "scalactic" % "3.0.4"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.4" % "test"

