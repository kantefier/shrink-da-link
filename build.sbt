name := "shrink-da-link"

version := "0.0.1"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

scalaVersion := "2.11.2"

libraryDependencies ++= Seq(
	jdbc,
	cache,
	ws,
	"org.squeryl" % "squeryl_2.11" 					% "0.9.5-7",
	"mysql" 			% "mysql-connector-java" 	% "5.1.31"
)