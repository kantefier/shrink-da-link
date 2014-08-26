import sbt._
import Keys._

object ApplicationBuild extends Build {
	val appName			= "shrink-da-link"
	val appVersion	= "0.0.1"

	val appDependencies = Seq(
		"org.squeryl" % "squeryl_2.11" 					% "0.9.5-7",
		"mysql" 			% "mysql-connector-java" 	% "5.1.31"
	)

	val main = Project(appName, file(".")).enablePlugins(play.PlayScala).settings(
		version 							:= 	appVersion,
		libraryDependencies		++=	appDependencies,
		scalaVersion					:=	"2.11.2"
	)
}