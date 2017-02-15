name := "pyspark-elastic"

version := io.Source.fromFile("version.txt").mkString.trim

organization := "TargetHolding"

scalaVersion := "2.11.8"

credentials += Credentials(Path.userHome / ".ivy2" / ".sbtcredentials")

licenses += "Apache-2.0" -> url("http://opensource.org/licenses/Apache-2.0") 

libraryDependencies ++= Seq(
   	"org.elasticsearch" %% "elasticsearch-spark-20" % "5.2.0"
exclude("org.glassfish.hk2.external", "javax.inject")
exclude("org.glassfish.hk2.external", "aopalliance-repackaged")
)

spName := "TargetHolding/pyspark-elastic"

sparkVersion := "2.1.0"

sparkComponents += "streaming"

javacOptions ++= Seq("-source", "1.8", "-target", "1.8")

assemblyOption in assembly := (assemblyOption in assembly).value.copy(
	includeScala = false
)

mergeStrategy in assembly := {
  case PathList("org", "apache", "spark", "unused", "UnusedStubClass.class") => MergeStrategy.first
  case x => (mergeStrategy in assembly).value(x)
}

val ignore = Set(
  "commons-beanutils-1.7.0.jar",
  "commons-beanutils-core-1.8.0.jar",
  "commons-logging-1.1.1.jar",
  "hadoop-yarn-api-2.2.0.jar",
  "guava-14.0.1.jar",
  "kryo-2.21.jar"
)

assemblyExcludedJars in assembly := { 
  val cp = (fullClasspath in assembly).value
  cp filter { x => ignore.contains(x.data.getName) }
}

EclipseKeys.withSource := true
val path = "/home/davidka/prems/dockerfiles/spark/pyspark-util/target/scala-2.11/pyspark-utils_2.11-0.0.1.jar"
unmanagedClasspath in Compile += file(path)
unmanagedClasspath in Runtime += file(path)

