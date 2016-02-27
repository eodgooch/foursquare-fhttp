name := "foursquare-fhttp"

version := "0.2.0"

organization := "com.foursquare"

scalaVersion := "2.11.7"

crossScalaVersions := Seq("2.11.7", "2.10.4")

libraryDependencies <++= (scalaVersion) { scalaVersion =>
  val v = scalaVersion match {
    case twoTen if scalaVersion.startsWith("2.10") => "_2.10"
    case twoEleven if scalaVersion.startsWith("2.11") => "_2.11"
    case _ => "_" + scalaVersion
  }
  Seq(
    "com.twitter"                   %  ("finagle-http" + v) % "6.30.0",
    "commons-httpclient"            %  "commons-httpclient" % "3.1",
    "junit"                         %  "junit"              % "4.10"       % "test",
    "com.novocode"                  %  "junit-interface"    % "0.9"        % "test"
  )
}

libraryDependencies := {
  CrossVersion.partialVersion(scalaVersion.value) match {
    // if scala 2.11+ is used, add dependency on scala-xml module
    case Some((2, scalaMajor)) if scalaMajor >= 11 =>
      libraryDependencies.value ++ Seq(
        "org.scala-lang.modules" %% "scala-xml" % "1.0.3")
    case _ =>
      libraryDependencies.value
  }
}

scalacOptions ++= Seq("-deprecation", "-unchecked")

testFrameworks += new TestFramework("com.novocode.junit.JUnitFrameworkNoMarker")

credentials += Credentials("Artifactory Realm", "localhost", System.getenv("ARTIFACTORY_UPLOADER"), System.getenv("ARTIFACTORY_UPLOADER_PASSWORD"))

publishTo <<= (version) { v =>
  val nexus = "https://oss.sonatype.org/"
  if (v.endsWith("-SNAPSHOT"))
    Some("Artifactory Realm" at "http://artifactory1.ops.prod.rbmhops.net:8081/artifactory/sbt-local;build.timestamp=" + new java.util.Date().getTime)   
  else
    Some("Artifactory Realm" at "http://artifactory1.ops.prod.rbmhops.net:8081/artifactory/sbt-local")
}

publishMavenStyle := true

publishArtifact in Test := false

pomIncludeRepository := { x => false }

pomExtra := (
<url>https://github.com/foursquare/foursquare-fhttp</url>
<licenses>
  <license>
    <name>Apache 2</name>
    <url>http://www.apache.org/licenses/LICENSE-2.0.txt</url>
    <distribution>repo</distribution>
    <comments>A business-friendly OSS license</comments>
  </license>
</licenses>
<scm>
  <url>git@github.com/foursquare/foursquare-fhttp.git</url>
  <connection>scm:git:git@github.com/foursquare/foursquare-fhttp.git</connection>
</scm>
<developers>
   <developer>
   <id>john</id>
   <name>John Gallagher</name>
   <email>john@foursquare.com</email>
 </developer>
</developers>
)

