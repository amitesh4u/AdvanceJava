package com.amitesh.playbook;

import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ListToMap {

  public static void main(String[] args) {
    convertListToMap();
    convertListToMapCustomObject();
  }

  private static void convertListToMap() {
    String[] strings = {"A", "B", "CC", "B", "D", "A", "EEE"};
    System.out.println("String: " + Arrays.asList(strings));
    Map<String, Integer> stringLengthMap = Stream.of(strings).distinct().collect(
        Collectors.toMap(Function.identity(), String::length));
    System.out.println("Map by String and length: " + stringLengthMap);

    Map<Integer, List<String>> lengthStringMap = Stream.of(strings)
        .collect(groupingBy(String::length));
    System.out.println("Map by length and strings: " + lengthStringMap);

    Map<Integer, Long> lengthCountMap = Stream.of(strings)
        .collect(groupingBy(String::length, counting()));
    System.out.println("Map by length and strings count: " + lengthCountMap);

    Map<String, Long> keyCount = Stream.of(strings)
        .collect(groupingBy(Function.identity(), counting()));
    System.out.println("Map by String and count: " + keyCount);
  }


  private static void convertListToMapCustomObject() {
    //TODO
  }
}
