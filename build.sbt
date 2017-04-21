import sbt.Keys._
import sbtrelease._
import ReleaseStateTransformations._
import im.dlg.DialogHouseRules._

name := "dialog-commons"

lazy val defaultSettings = defaultDialogSettings ++ mitLicense ++ Seq(
  scalaVersion := "2.11.11",
  resolvers += Resolver.sonatypeRepo("public"),
  publishMavenStyle := true
)

defaultSettings

lazy val dialogUtil = project in file("dialog-util") settings defaultSettings
lazy val dialogConcurrent = project in file("dialog-concurrent") settings defaultSettings dependsOn dialogUtil
lazy val dialogCatsSlick = project in file("dialog-cats-slick") settings defaultSettings
lazy val dialogStorage = project in file("dialog-storage") settings defaultSettings
lazy val dialogStorageSlick = project in file("dialog-storage-slick") dependsOn dialogStorage settings defaultSettings

releaseProcess := Seq[ReleaseStep](
  checkSnapshotDependencies,
  inquireVersions,
  runClean,
  runTest,
  setReleaseVersion,
  commitReleaseVersion,
  tagRelease,
  ReleaseStep(action = Command.process("publish", _)),
  setNextVersion,
  commitNextVersion,
  pushChanges
)
