name := course.value + "-" + assignment.value

scalaVersion := "2.11.7"

scalacOptions ++= Seq("-deprecation")

courseId := "GVy8tIIKEeWXmQ4F86nmrw"

// grading libraries
libraryDependencies += "junit" % "junit" % "4.10" % "test"
libraryDependencies ++= assignmentsMap.value.values.flatMap(_.dependencies).toSeq

// include the common dir
commonSourcePackages += "common"

assignmentsMap := {
  val depsCollections = Seq(
    "com.storm-enroute" %% "scalameter-core" % "0.6",
    "com.github.scala-blitz" %% "scala-blitz" % "1.1",
    "org.scala-lang.modules" %% "scala-swing" % "1.0.1",
    "com.storm-enroute" %% "scalameter" % "0.6" % "test"
  )
  val depsSpark = Seq(
    "org.apache.spark" %% "spark-core" % "1.2.1"
  )
  val styleSheetPath = (baseDirectory.value / ".." / ".." / "project" / "scalastyle_config.xml").getPath
  Map(
    "example" -> Assignment(
      packageName = "example",
      key = "_Cuio9oTEeWUtQpvX4iAkw",
      itemId = "gM5Y4",
      partId = "WGx0f",
      maxScore = 10d,
      dependencies = Seq()),
    "scalashop" -> Assignment(
      packageName = "scalashop",
      key = "OpSNmtC1EeWvXAr2bF16EQ",
      itemId = "NeGTv",
      partId = "Q2e1P",
      maxScore = 10d,
      dependencies = depsCollections),
    "reductions" -> Assignment(
      packageName = "reductions",
      key = "lUUWddoGEeWPHw6r45-nxw",
      itemId = "4rXwX",
      partId = "gmSnR",
      maxScore = 10d,
      dependencies = depsCollections),
    "kmeans" -> Assignment(
      packageName = "kmeans",
      key = "UJmFEtoIEeWJwRKcpT8ChQ",
      itemId = "akLxD",
      partId = "mz8iL",
      maxScore = 10d,
      dependencies = depsCollections),
    "barneshut" -> Assignment(
      packageName = "barneshut",
      key = "itfW99oJEeWXuxJgUJEB-Q",
      itemId = "xGkV0",
      partId = "ep95q",
      maxScore = 10d,
      dependencies = depsCollections)
  )
}
