package com.amitesh.advancestring;

import java.text.ChoiceFormat;
import java.text.MessageFormat;
import java.text.ParsePosition;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class AdvanceString {

  public static void main(String[] args) {
    testIsBlank();
    testEmptyVsBlank();
    testRepeat();
    testIndent();
    testLines();
    testFormatted();
    testMessageFormat();
    testMessageAndChoiceFormatWithPlurals();
    testMessageAndInlineChoiceFormatWithPlurals();
    testStringTemplate();
    testTextBlock();
    testStrip();
    testStripVsTrim();
    testTranslateEscapes();
    testTransform();
  }

  private static void testIsBlank() {
    System.out.println("\n~~~~~~~ Test isBlank");
    System.out.println(STR."\"    text    \" = \{"    text    ".isBlank()}");
    System.out.println(STR."\"    text    \" = \{"    text    ".isBlank()}");
    System.out.println(STR."\"    \"= \{"    ".isBlank()}");
    System.out.println(STR."\"\"= \{"".isBlank()}");
  }

  /**
   * Empty checks for pure empty string whereas isBlank checks for special characters too
   */
  private static void testEmptyVsBlank() {
    System.out.println("\n~~~~~~~ Test Empty vs Blank");
    System.out.println("isEmpty()");
    System.out.println("".isEmpty());           // True

    System.out.println("\r".isEmpty());         // False
    System.out.println("\u2002".isEmpty());     // False
    System.out.println(" ".isEmpty());          // False

    System.out.println("isBlank()");
    System.out.println("".isBlank());           // True
    System.out.println("\r".isBlank());         // True
    System.out.println("\u2002".isBlank());     // True
    System.out.println(" ".isBlank());          // True

    System.out.println("Careful!");
    String evilString = "\u2002";
    System.out.println(
        evilString.trim().isEmpty());        // False! But you might have expected true

    System.out.println(evilString.strip().isBlank());       // redundant
    System.out.println(evilString.isBlank());               // true
  }

  private static void testRepeat() {
    System.out.println("\n~~~~~~~~~ Test Repeat");
    System.out.println("One Two repeat 3 times = one two ".repeat(3));
  }

  private static void testIndent() {
    System.out.println(
        "\n~~~~~~~~~ Test Indent (add/remove front spacing and add linefeed at the end");
    System.out.println("\"Text\" (4, Add 4 front space) = \"" + "Text".indent(4) + "\"");
    System.out.println(
        "\"    Text\"  (-4, Remove 4 front space = \"" + "    Text".indent(-4) + "\"");
    System.out.println("Test Indent ends");
  }

  private static void testLines() {
    System.out.println("\n~~~~~~~~~ Test Lines");
    System.out.println(
        "Break in multiple lines : " + "hi! \n How r u? \n I'm fine. \n Thank you".lines()
            .toList());
  }

  public static void testFormatted() {
    System.out.println("\n~~~~~~~ Test Formatted");
    String str = "1. %s 2. %s 3. %s ";
    System.out.println(str.formatted("one", "two", "three"));
  }

  public static void testMessageFormat() {
    System.out.println("\n~~~~~~~ Test Message Format");
    var format = "Hello {0}, how are you {0}?. Its {1}°C today!";
    System.out.println(MessageFormat.format(format, "Ron", "5"));

    MessageFormat mf = new MessageFormat(format);
    System.out.println(mf.format(new String[]{"Tom", "10"}));

    int planet = 7;
    String event = "a disturbance in the Force";
    System.out.println(MessageFormat.format(
        "At {1,time} on {1,date}, there was {2} on planet {0,number,integer}.",
        planet, new Date(), event));

    MessageFormat mfNumber = new MessageFormat(
        "Testing PI with different decimal point i.e. {0,number,#.##}, {0,number,#.#}");
    Object[] objs = {3.1415};
    String result = mfNumber.format(objs);
    System.out.println(result);

    // If a single argument is parsed more than once in the string, then the later parse wins.
    // TODO Need revision on Parse
    System.out.println(Arrays.asList(mfNumber.parse(result, new ParsePosition(0))));
  }

  public static void testMessageAndChoiceFormatWithPlurals() {
    System.out.println("\n~~~~~~~ Test Message and Choice Format with Plurals");
    MessageFormat form = new MessageFormat("The disk \"{1}\" contains {0}.");
    double[] fileLimits = {0, 1, 2};
    String[] filePart = {"no files", "one file", "{0,number} files"};
    ChoiceFormat fileForm = new ChoiceFormat(fileLimits, filePart);
    form.setFormatByArgumentIndex(0, fileForm);

    List<Integer> fileCounts = Arrays.asList(0, 1, 123);
    String diskName = "MyDisk";
    fileCounts.forEach(fileCount -> {
      Object[] testArgs = {fileCount, diskName};
      System.out.println(form.format(testArgs));
    });
  }

  public static void testMessageAndInlineChoiceFormatWithPlurals() {
    System.out.println("\n~~~~~~~ Test Message and Inline Choice Format with Plurals");

    MessageFormat form = new MessageFormat(
        "There {0,choice,0#are no files|1#is one file|1<are {0,number,integer} files} in the disk {1}.");
    List<Integer> fileCounts = Arrays.asList(0, 1, 123);
    String diskName = "MyDisk";
    fileCounts.forEach(fileCount -> {
      Object[] testArgs = {fileCount, diskName};
      System.out.println(form.format(testArgs));
    });
  }

  private static void testStringTemplate() {
    System.out.println("\n~~~~~~~ Test String Template");
    var name = "Ben";
    var tempC = 28;
    System.out.println(STR."Hello \{name}, how are you?\nIt's \{tempC}°C today!");

    System.out.println(
        STR."Today's weather is \{getFeelsLike()}, with a temperature of \{getTemperature()} degrees \{getUnit()}");

  }

  private static String getUnit() {
    return "Fahrenheit";
  }

  private static Float getTemperature() {
    return 52.6f;
  }

  private static String getFeelsLike() {
    return "Cold";
  }

  /**
   * Quotes are allowed in Text blocks without escaping
   */
  private static void testTextBlock() {
    System.out.println("\n~~~~~~~ Test TextBlock");
    String name = "Harry";
    String textBlock = """     
        This is an example
            of a multi-line "quoted"
        string. - By %s %d yrs     """.formatted(name, 40);
    System.out.println("Text Block = " + textBlock);

    // Text blocks by default removes spaces from beginning and end. Use /s to preserve space
    String withSpaceTextBlock = """           
             \sThis is an example
            of a multi-line
        string. - By %s %d yrs     \s""".formatted(name, 40);
    System.out.println("\nWith Space Text block = " + withSpaceTextBlock);
  }


  private static void testStrip() {
    System.out.println("\n~~~~~~~~~ Test Strip");
    System.out.println("\"    Stripped    \" = \"" + "    Stripped    ".strip() + "\"");
    System.out.println(
        "\"    StrippedLeading    \" = \"" + "    Stripped Leading    ".stripLeading() + "\"");
    System.out.println(
        "\"    StrippedTrailing    \" = \"" + "    Stripped Trailing    ".stripTrailing() + "\"");
    System.out.println(
        "\"    StrippedIndent    \" = \"" + "    Stripped Indent    ".stripIndent() + "\"");

    String multiLineString = """
        This is an example
            of a multi-line
        string with
            inconsistent
        indentation.""";

    System.out.println("\nStrip Indent from each line = ");
    System.out.println(multiLineString.stripIndent());
  }

  /**
   * Trim remove only Unicode characters <= 20 whereas Strip is expanded to cover other unicode
   * characters
   */
  public static void testStripVsTrim() {
    System.out.println("\n~~~~~~~~~ Test Strip vs Trim");
    System.out.println("Trim ex: " + "hello   ".trim() + "     there".trim());

    char space = '\u2002';
    System.out.println("\\u2002 is whitespace: " + Character.isWhitespace(space));

    System.out.println("With Trim:" + "hello\u2002".trim() + "\u2002there".trim());
    System.out.println("With Strip:" + "hello\u2002".strip() + "\u2002there".strip());
  }

  /**
   * translateEscapes method returns a new string which translates the escape characters into a
   * string literal. Escape characters are tab (\t), new line(\n) etc.
   */
  public static void testTranslateEscapes() {
    System.out.println("\n~~~~~~~ Test TranslateEscapes");
    String str = "This is tab \t, Next New Line \n,next backspace \b,next Single Quote ' next Double Quote \" ";
    System.out.println(str.translateEscapes());
  }

  public static void testTransform() {
    System.out.println("\n~~~~~~~ Test Transform");
    // Old Style
    String lotteryWin = " 100 usd ";
    String result = lotteryWin
        .replaceAll("[a-z]", "")
        .strip();

    String formattedResult = formatNumber(result);
    System.out.println(formattedResult.toUpperCase());

    // Chaining with Transform
    String finalResult = lotteryWin
        .replaceAll("[a-z]", "")
        .strip()
        .transform(AdvanceString::formatNumber)
        .toUpperCase();

    System.out.println(finalResult);

    // All Transform calls
    String finalResult2 = lotteryWin
        .transform(s -> s.replaceAll("[a-z]", ""))
        .transform(String::strip)
        .transform(AdvanceString::formatNumber)
        .transform(String::toUpperCase);

    System.out.println(finalResult2);

  }

  private static String formatNumber(String num) {
    if (Integer.parseInt(num) < 100) {
      return "Nice! You've won: " + num;
    } else {
      return "Great news! You've won: " + num;
    }
  }
}
