package com.amitesh.playbook;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class StringSanitization {

  final static String text = """
      Tokyo,    37000000
      New York, 20_000_000
      Paris,    11.000.000 // TODO verify regex to keep first decimal
      """;

  public static void main(String[] args) {

    List<String> populations = new ArrayList<>();
    String[] lines = text.split("\n");
    for (String line : lines) {
      String population = line.split(",")[1];
      String sanitized = population.replaceAll("[^\\d]", "");
      populations.add(sanitized);
    }

    System.out.println(populations);

    List<String> populations2 = text.lines()
        .map(line -> line.split(",")[1])
        .map(population -> population.replaceAll("[^\\d]", ""))
        .toList();

    System.out.println(populations2);

    Function<String, String> extractSecondToken = line -> line.split(",")[1];
    Function<String, String> sanitizeNumber = numberAsString -> numberAsString.replaceAll("[^\\d]",
        "");

    DecimalFormat df = new DecimalFormat("#");
    df.setMaximumFractionDigits(0);

    System.out.println(text.lines()
        .map(extractSecondToken.andThen(sanitizeNumber).andThen(Double::valueOf))
        .reduce(Double::sum)
        .map(df::format) // Remove scientific notation E
        .orElse(null));


  }
}
