val jsonToCsv = (project in file("."))
  .settings(
    name := "json-to-csv",
    version := "0.1",
    scalaVersion := "2.13.3",
    libraryDependencies ++= Seq(
      "com.lihaoyi" %% "upickle" % "0.9.5",
      "info.picocli" % "picocli" % "4.5.1"
    )
  )

lazy val generatePicoCliFile =
  taskKey[Unit]("Generates picocli-related native-image files")

lazy val native = (project in file("native"))
  .dependsOn(jsonToCsv)
  .enablePlugins(NativeImagePlugin)
  .settings(
    name := "json-to-csv-native",
    version := "0.1",
    generatePicoCliFile := {
      import picocli.codegen.aot.graalvm._

      ReflectionConfigGenerator.main(
        "--output",
        "target/scala-2.13/classes/META-INF/native-image/resource-config.json",
        "JsonToCsv"
      )
    },
    nativeImage := nativeImage.dependsOn(generatePicoCliFile).value,
    nativeImageOptions := Seq(
      "-H:+ReportExceptionStackTraces",
      "--no-fallback",
      "--static"
    )
  )
