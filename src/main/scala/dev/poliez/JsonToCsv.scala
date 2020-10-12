package dev.poliez

import java.io.File
import java.nio.file.{Files, Path, Paths}

import picocli.{CommandLine => cmd}
import ujson.Arr
import upickle.default._

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

class JsonToCsv {

  def convert(inputFile: File, outputFile: Path, columnName: String): Int = {
    val lines =
      Option(columnName).toList ++
        read[Arr](inputFile).value.map(_.toString)

    val outputPath =
      Option(outputFile)
        .getOrElse(
          Paths.get(inputFile.getPath.replaceFirst("(\\.\\w+)?$", ".csv"))
        )

    Try {
      Files.write(outputPath, lines.asJava)
    } match {
      case Failure(exception) => 1
      case Success(value)     => 0
    }
  }
}
