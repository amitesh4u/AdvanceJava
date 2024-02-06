package com.amitesh.playbook;

import java.time.DayOfWeek;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

public class AdvanceDateTime {

  public static void main(String[] args) {
    testCurrentTime();
    testAddSubtractPeriods();
    testStartEndOfPeriod();
    testCompareDates();
    testConvertOldDate();
    testDateDifference();
    testFormatDates();
    testHandlingRecurringEvents();
    testTimeZoneDifference();
    testUntil();
    testConvertTimeZones();
    testGetAllZoneIds(true);
    testGetAllZoneIds(false);
  }

  private static void testCurrentTime() {
    System.out.println("\n~~~~~~~~~~~~~~~ testCurrentTime");

    System.out.println("LocalDate.now() : " + LocalDate.now());
    System.out.println("LocalTime.now() : " + LocalTime.now());
    System.out.println("LocalDateTime.now() : " + LocalDateTime.now());
    System.out.println("Instant.now() : " + Instant.now());
    System.out.println("Instant.now().toEpochMilli() : " + Instant.now().toEpochMilli());
    System.out.println("System.currentTimeMillis() : " + System.currentTimeMillis());

    System.out.println("ZonedDateTime.now() : " + ZonedDateTime.now());
    System.out.println("LocalDateTime.now(ZoneId.of(\"America/New_York\")) : " + LocalDateTime.now(
        ZoneId.of("America/New_York")));
  }

  private static void testAddSubtractPeriods() {
    System.out.println("\n~~~~~~~~~~~~~~~ testAddSubtractPeriods");

    var date = LocalDate.now();

    System.out.println(date);
    System.out.println(date.plusDays(1));
    System.out.println(date.plusWeeks(1));
    System.out.println(date.plusMonths(3));
    System.out.println(date.plusYears(5));
    System.out.println(date.plus(1, ChronoUnit.DECADES));
    Period period = Period.of(0, 1, 5);
    System.out.println(date.plus(period));

    try {
      System.out.println(
          date.plus(1, ChronoUnit.MINUTES)); // Fail as LocalDate doesn't have Time component
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }

    System.out.printf("Your scheduled appointment will take place in %s days\n",
        ChronoUnit.DAYS.between(date, date.plusMonths(1).plusDays(6)));

    System.out.printf("Your scheduled appointment will take place in %s days\n",
        date.until(date.plusMonths(1).plusDays(6), ChronoUnit.DAYS));

  }

  private static void testStartEndOfPeriod() {
    System.out.println("\n~~~~~~~~~~~~~~~ testStartEndOfPeriod");

    // Print: Hurry! Sale ends at the end of the month!, x Days remaining
    LocalDate today = LocalDate.now();
    LocalDate endOfMonth = today.with(TemporalAdjusters.lastDayOfMonth());

    System.out.println(today);
    System.out.println(endOfMonth);

    String salesEndMessageWithDate = String.format(
        "Hurry! The sale ends at the end of the month, %s day(s) remaining!",
        endOfMonth.getDayOfMonth() - today.getDayOfMonth());

    System.out.println(salesEndMessageWithDate);

    LocalTime now = LocalTime.now();
    LocalDateTime nowToday = LocalDateTime.of(today, now);
    LocalDateTime midnightToday = LocalDateTime.of(today, LocalTime.MAX);
    System.out.println(nowToday);
    System.out.println(midnightToday);

    String salesEndMessageWithTime = String.format(
        "Hurry, sale ends at midnight, time left: %s hours, %s minutes, %s seconds",
        midnightToday.getHour() - nowToday.getHour(),
        midnightToday.getMinute() - nowToday.getMinute(),
        midnightToday.getSecond() - nowToday.getSecond());
    System.out.println(salesEndMessageWithTime);
  }

  private static void testCompareDates() {
    System.out.println("\n~~~~~~~~~~~~~~~ testCompareDates");

    LocalDate date = LocalDate.of(2021, 12, 30);
    LocalDate now = LocalDate.now();

    // isBefore() / isAfter()
    System.out.println(date.isAfter(now) ? "Not Yet!" : "Already past!");
  }

  private static void testConvertOldDate() {
    System.out.println("\n~~~~~~~~~~~~~~~ testConvertOldDate");

    Date in = new Date(); // from an external API

    LocalDateTime ldt = LocalDateTime.ofInstant(in.toInstant(),
        ZoneId.systemDefault()); // Date to LocalDateTime
    LocalDateTime ldt2 = LocalDateTime.ofInstant(in.toInstant(), ZoneId.of("America/New_York"));

    Date out = Date.from(ldt.atZone(ZoneId.systemDefault()).toInstant()); // LocalDateTime to Date

    System.out.println(in);
    System.out.println(ldt);
    System.out.println(ldt2);
    System.out.println(out);
  }

  private static void testDateDifference() {
    System.out.println("\n~~~~~~~~~~~~~~~ testDateDifference");

    LocalDate anniversary = LocalDate.of(2020, Month.NOVEMBER, 5);
    LocalDate today = LocalDate.now();

    long yearsBetween = ChronoUnit.YEARS.between(anniversary, today);
    System.out.println("Years between dates: " + yearsBetween);

    long daysBetween = ChronoUnit.DAYS.between(anniversary, today);
    System.out.println("Days between dates:" + daysBetween);

    LocalDate start = LocalDate.of(2010, 11, 2);

    Period age = Period.between(start, today);

    // example: 13 months
    System.out.println(age.getYears() + "y "    // == 1 (12 months)
        + age.getMonths() + "m "            // == 1
        + age.getDays() + "d");
  }

  private static void testFormatDates() {
    System.out.println("\n~~~~~~~~~~~~~~~ testFormatDates");

    var date = LocalDateTime.now();
    System.out.println("Default Date : " + date);

    var isoDate = DateTimeFormatter.ISO_DATE.format(date);
    System.out.println("ISO_DATE : " + isoDate);

    var basicIsoDate = DateTimeFormatter.BASIC_ISO_DATE.format(date);
    System.out.println("BASIC_ISO_DATE : " + basicIsoDate);

    var someDate = DateTimeFormatter.ofLocalizedDate(FormatStyle.FULL).format(date);
    System.out.println("FULL : " + someDate);

    var someDate2 = DateTimeFormatter.ofLocalizedDateTime(FormatStyle.SHORT).format(date);
    System.out.println("SHORT : " + someDate2);

    var someDate3 = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.of("en", "US"))
        .format(date);
    System.out.println("SHORT USA : " + someDate3);

    var someDate4 = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.of("fr", "FR"))
        .format(date);
    System.out.println("SHORT France : " + someDate4);

    var someDate5 = DateTimeFormatter
        .ofLocalizedDate(FormatStyle.SHORT).withLocale(Locale.of("de", "DE"))
        .format(date);
    System.out.println("SHORT German : " + someDate5);

    String europeanPattern = "dd-MM-yyyy";
    String usPattern = "MM.dd.yyyy";
    var fixedDate = LocalDate.of(2022, 10, 31);

    System.out.println(
        "Europe pattern : " + DateTimeFormatter.ofPattern(europeanPattern).format(fixedDate));
    System.out.println("USA Pattern : " + DateTimeFormatter.ofPattern(usPattern).format(fixedDate));

    var formatBuilder = new DateTimeFormatterBuilder();

    formatBuilder.appendOptional(DateTimeFormatter.ofPattern("yyyy-MM-dd"))
        .appendOptional(DateTimeFormatter.ofPattern("MM-dd-yyyy"))
        .appendOptional(DateTimeFormatter.ofPattern("dd-MM-yyyy"));

    var formatter = formatBuilder.toFormatter();

    System.out.println(LocalDate.parse("2022-07-22", formatter)); // OK. Matches first pattern
    System.out.println(LocalDate.parse("07-22-2022", formatter)); // OK. Matches second pattern
    try {
      System.out.println(LocalDate.parse("22-07-2022",
          formatter)); // FAIL. Matches but fail for second pattern. Do not try third
    } catch (Exception e) {
      System.out.println(e.getMessage());
    }
  }

  private static void testHandlingRecurringEvents() {
    System.out.println("\n~~~~~~~~~~~~~~~ testHandlingRecurringEvents");

    // Task - meeting every Friday. Print all meetings for the month
    var today = LocalDate.now();
    var start = today.with(TemporalAdjusters.firstDayOfMonth());
    var stop = today.with(TemporalAdjusters.lastDayOfYear());

    var friday = start.with(TemporalAdjusters.nextOrSame(DayOfWeek.FRIDAY)); //First Friday

    System.out.println("Start date " + start);
    System.out.println("End date " + stop);

    List<LocalDate> fridays = new ArrayList<>();

    while (friday.isBefore(stop) || friday.isEqual(stop)) {
      fridays.add(friday);
      friday = friday.plusWeeks(1); // move to the next Friday
    }

    System.out.printf("From %s to %s, Fridays will be on %s%n", start, stop, fridays);
  }

  private static void testTimeZoneDifference() {
    System.out.println("\n~~~~~~~~~~~~~~~ testTimeZoneDifference");

    var departure = LocalDateTime.of(2023, Month.DECEMBER, 22, 7, 0);
    var arrival = departure.plusHours(8);

    var departureLondonPerspective = departure.atZone(ZoneId.of("Europe/London"));
    var arrivalLondonPerspective = arrival.atZone(ZoneId.of("Europe/London"));

    System.out.println(departureLondonPerspective);
    System.out.println(arrivalLondonPerspective);

    var departureNyPerspective = departureLondonPerspective
        .withZoneSameInstant(ZoneId.of("America/New_York"));

    var arrivalNyPerspective = arrivalLondonPerspective
        .withZoneSameInstant(ZoneId.of("America/New_York"));

    System.out.println(departureNyPerspective);
    System.out.println(arrivalNyPerspective);

    System.out.println("======== Flight ========");

    System.out.println(new Flight("Europe/London", "America/New_York", departure, 8));
    System.out.println(new Flight("Europe/Berlin", "Asia/Dubai", departure, 4));
  }

  private static void testUntil() {
    System.out.println("\n~~~~~~~~~~~~~~~ testUntil");

    var today = LocalDate.now();
    var lastDay = today.with(TemporalAdjusters.lastDayOfYear());

    // 1st attempt
    var period = Period.between(today, lastDay);
    System.out.printf("Only %s months left till EOY", period.getMonths());
    System.out.printf("\nOnly %s days left till EOY", period.getDays());
    // produces an exception
//        System.out.printf("\nOnly %s weeks left till EOY", period.get(ChronoUnit.WEEKS));

    System.out.println();

    // 2nd attempt
    System.out.printf("\nOnly %s whole months left till EOY",
        today.until(lastDay, ChronoUnit.MONTHS));
    System.out.printf("\nOnly %s whole weeks left till EOY",
        today.until(lastDay, ChronoUnit.WEEKS));
    System.out.printf("\nOnly %s whole days left till EOY", today.until(lastDay, ChronoUnit.DAYS));
    System.out.println();

  }

  private static void testConvertTimeZones() {
    System.out.println("\n~~~~~~~~~~~~~~~ testConvertTimeZones");

    LocalTime time = LocalTime.now();
    List<String> zones = List.of("America/New_York", "Europe/London", "Asia/Kolkata");
    ZonedDateTime dateTime = time.atDate(LocalDate.now()).atZone(ZoneId.systemDefault());

    System.out.println(
        dateTime.getZone() + " " + dateTime.format(DateTimeFormatter.ISO_LOCAL_TIME));

    System.out.println("===================");

    for (var zone : zones) {
      ZonedDateTime otherTime = dateTime.withZoneSameInstant(ZoneId.of(zone));
      System.out.println(zone + " " + otherTime.format(DateTimeFormatter.ISO_LOCAL_TIME));
    }
  }


  private static void testGetAllZoneIds(boolean sortByRegion) {
    ZoneId.getAvailableZoneIds()
        .forEach(System.out::println);

    Map<String, String> sortedMap = new LinkedHashMap<>();
    Map<String, String> allZoneIdsAndItsOffSet = getAllZoneIdsAndItsOffSet();

    if (sortByRegion) {
      allZoneIdsAndItsOffSet.entrySet().stream()
          .sorted(Map.Entry.comparingByKey())
          .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
    } else {
      allZoneIdsAndItsOffSet.entrySet().stream()
          .sorted(Map.Entry.<String, String>comparingByValue().reversed())
          .forEachOrdered(entry -> sortedMap.put(entry.getKey(), entry.getValue()));
    }

    sortedMap.forEach((k, v) -> System.out.printf("%35s (UTC%s) %n", k, v));
  }

  private static Map<String, String> getAllZoneIdsAndItsOffSet() {

    Map<String, String> result = new HashMap<>();

    LocalDateTime localDateTime = LocalDateTime.now();

    for (String zoneId : ZoneId.getAvailableZoneIds()) {

      ZoneId id = ZoneId.of(zoneId);

      //replace Z to +00:00
      String offset = localDateTime.atZone(id)
          .getOffset().getId().replaceAll("Z", "+00:00");

      result.put(id.toString(), offset);

    }
    return result;
  }


}


record Flight(String from, String to, LocalDateTime departure, int duration) {

  @Override
  public String toString() {
    ZoneId fromZone = ZoneId.of(from);
    ZoneId toZone = ZoneId.of(to);

    var departTime = departure.atZone(fromZone);
    var arrivalTime = departure.plusHours(duration)
        .atZone(fromZone).withZoneSameInstant(toZone);

    return String.format("Flight departs at %s and arrives at %s%n", departTime, arrivalTime);
  }
}