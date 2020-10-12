import java.io.File
import java.nio.file.{Files, Path, Paths}
import java.util.concurrent.Callable

import picocli.{CommandLine => cmd}
import ujson.Arr
import upickle.default._

import scala.jdk.CollectionConverters._
import scala.util.{Failure, Success, Try}

@cmd.Command(
  version = Array("0.1.0"),
  description = Array("Converts a JSON File in a single-column CSV file")
)
object JsonToCsv extends Callable[Int] {

  @cmd.Parameters(
    index = "0",
    description = Array("JSON file to convert")
  )
  var inputFile: File = _

  @cmd.Option(
    names = Array("--output", "-o"),
    description = Array("CSV file to write to (replaces content if present)"),
    required = false
  )
  var outputFile: Path = _

  @cmd.Option(
    names = Array("-n", "--column-name"),
    required = false
  )
  var columnName: String = _

  override def call(): Int = {
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

  def main(args: Array[String]): Unit = {
    System.exit(new cmd(JsonToCsv).execute(args: _*))
  }
}
