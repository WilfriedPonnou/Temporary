name := "Consumer"

version := "1.0"

scalaVersion := "2.13.8"
val sparkVersion = "3.2.1"

// https://mvnrepository.com/artifact/org.apache.spark/spark-streaming-kafka-0-10
libraryDependencies += "org.apache.spark" %% "spark-streaming-kafka-0-10" % "3.2.1"

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.1.2"

libraryDependencies += "org.apache.spark" %% "spark-sql" % "3.1.2"

