package com.amitesh.playbook;

import static java.util.stream.Collectors.toMap;

import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.function.BiFunction;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class UsefulMapMethods {

  public static void main(String[] args) {
    checkMapEquality();
    findDuplicateValues();
    mergeMap();
    replaceElement();
    sortMap();
  }

  private static void checkMapEquality() {
    Map<Integer, LanguageRecord> mapLangRecordOne = Map.of(1, new LanguageRecord("Java"));
    Map<Integer, LanguageRecord> mapLangRecordTwo = Map.of(1, new LanguageRecord("Java"));
    System.out.println("With records: " + mapLangRecordOne.equals(mapLangRecordTwo));

    Map<Integer, Language> mapLangOne = Map.of(1, new Language("Java"));
    Map<Integer, Language> mapLangTwo = Map.of(1, new Language("Java"));

    System.out.println("Class without equals: " + mapLangOne.equals(mapLangTwo));

    System.out.println("By values only: " + areMapsEqualByValue(mapLangOne, mapLangTwo));
  }

  private static boolean areMapsEqualByValue(Map<Integer, Language> first,
      Map<Integer, Language> second) {
    if (first.size() != second.size()) {
      return false;
    }

    return first.entrySet().stream()
        .allMatch(e -> e.getValue().getName().equals(second.get(e.getKey()).getName()));

  }

  private static void findDuplicateValues() {
    Map<Integer, String> map = Map.of(
        1, "Java",
        2, "C#",
        3, "C#",
        4, "JavaScript",
        5, "JavaScript"
    );

    System.out.println(mapHasDuplicates(map));

    System.out.println(collectDuplicateValues(map));
  }

  public static <K, V> boolean mapHasDuplicates(Map<K, V> map) {
    Collection<V> valuesList = map.values();
    Set<V> valuesSet = new HashSet<>(valuesList);
    return valuesList.size() != valuesSet.size();
  }


  private static <K, V> List<V> collectDuplicateValues(Map<K, V> map) {
    return map.values().stream()
        .filter(value -> Collections.frequency(map.values(), value) > 1)
        .distinct()
        .toList();
  }

  private static void mergeMap() {
    Map<Integer, String> mapOne = Map.of(1, "Java", 2, "C#", 3, "JavaScript");
    Map<Integer, String> mapTwo = Map.of(3, "Go", 5, "Python", 6, "Kotlin");

    Map<Integer, String> mergedMap = new HashMap<>();
    mergedMap.putAll(mapOne);
    mergedMap.putAll(mapTwo);
    System.out.println("putAll: " + mergedMap);

    var mergedMapTwo = Stream.of(mapOne, mapTwo)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue,
            (v1, v2) -> v1 + " or " + v2));

    System.out.println(mergedMapTwo);

    Map<String, Integer> mapThree = Map.of("Java", 1, "C#", 2, "JavaScript", 3);
    Map<String, Integer> mapFour = Map.of("JavaScript", 4, "Python", 5, "Kotlin", 6);

    var mergedMapThree = Stream.of(mapThree, mapFour)
        .flatMap(m -> m.entrySet().stream())
        .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, Integer::max));

    System.out.println(mergedMapThree);
  }

  private static void replaceElement() {
    Map<Integer, String> map = new HashMap<>(Map.of(
        1, "Java",
        2, "C#",
        3, "JavaScript",
        4, "Python",
        5, "Go",
        6, "Kotlin"
    ));

    BiFunction<Integer, String, String> replace = (k, v) -> {
      if ("Go".equals(v) || k == 6) {
        return "Java";
      }
      return v;
    };

    map.replaceAll(replace);
    System.out.println(map);
  }

  private static void sortMap() {
    Map<Integer, String> mapOne = Map.of(20, "Java",
        30, "C#",
        10, "JavaScript",
        40, "Python");
    System.out.println(mapOne);

    System.out.println("TreeMap: " + new TreeMap<>(mapOne));

    Map<Tech, String> mapTwo = Map.of(new Tech(), "Java", new Tech(), "C#", new Tech(), "Go");
//        System.out.println(new TreeMap<>(mapTwo));

    System.out.println(
        "Sort by value stream: " + sortByValueStream(mapTwo, Comparator.naturalOrder()));
    System.out.println(
        "Sort by value stream reverse: " + sortByValueStream(mapTwo, Comparator.reverseOrder()));
  }

  public static <K, V> Map<K, V> sortByValueStream(Map<K, V> map, Comparator<? super V> c) {

    return map.entrySet().stream()
        .sorted(Map.Entry.comparingByValue(c))
        .collect(toMap(Map.Entry::getKey, Map.Entry::getValue,
            (v1, v2) -> v1, LinkedHashMap::new));
  }
}

class Language {

  final String name;

  public Language(String name) {
    this.name = name;
  }

  public String getName() {
    return name;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }

    Language language = (Language) o;

    return name.equals(language.name);
  }

  @Override
  public int hashCode() {
    return name.hashCode();
  }
}

record LanguageRecord(String name) {

}

class Tech {

  String name;

  @Override
  public String toString() {
    return "Tech";
  }
}