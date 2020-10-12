package dev.poliez.tools;

import picocli.CommandLine;
import picocli.CommandLine.*;

import java.io.File;
import java.nio.file.Path;
import java.util.concurrent.Callable;

@Command(
    version = "0.1.0",
    description = "Converts a JSON File in a single-column CSV file"
)
public class JsonToCsv implements Callable<Integer> {

    @Parameters(
        index = "0",
        description = "JSON file to convert"
    )
    File inputFile;

    @Option(
        names = {"--output", "-o"},
        description = "CSV file to write to (replaces content if present)",
        required = false
    )
    Path outputFile;

    @Option(
        names = {"-n", "--column-name"},
        required = false
    )
    String columnName;

    public static void main(String[] args) {
        new CommandLine(new JsonToCsv()).execute(args);
    }

    @Override
    public Integer call() throws Exception {
        return new dev.poliez.JsonToCsv().convert(inputFile, outputFile, columnName);
    }
}
