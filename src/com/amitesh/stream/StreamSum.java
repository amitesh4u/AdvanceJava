package com.amitesh.stream;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class StreamSum {

  public static void main(String[] args) {
    List<Integer> integers = Arrays.asList(1, 2, 3, 4, 5);

    /* Using Reduce */
    // Using Stream.reduce()
    Integer sum1 = integers.stream()
        .reduce(0, (a, b) -> a + b);

    // With Method reference
    Integer sum2 = integers.stream()
        .reduce(0, Integer::sum);

    // With Custom Method
    Integer sum3 = integers.stream()
        .reduce(0, StreamSum::add);

    // Using Stream.collect()
    Integer sum4 = integers.stream()
        .collect(Collectors.summingInt(Integer::intValue));

    // Using IntStream.sum()
    Integer sum5 = integers.stream()
        .mapToInt(Integer::intValue)
        .sum();

    // Using Stream#sum With Map
    Map<String, Integer> map = new HashMap<>();
    IntStream.rangeClosed(1, 5).forEach(i -> map.put(String.valueOf(i), i));

    Integer sum6 = map.values()
        .stream()
        .mapToInt(Integer::valueOf)
        .sum();

    // Using Stream#sum With Objects
    Item item1 = new Item(1, 1);
    Item item2 = new Item(2, 2);
    Item item3 = new Item(3, 3);
    Item item4 = new Item(4, 4);
    Item item5 = new Item(5, 5);

    List<Item> items = Arrays.asList(item1, item2, item3, item4, item5);

    Integer sum7 = items.stream()
        .map(Item::getPrice)
        .reduce(0, StreamSum::add);
    Integer sum8 = items.stream()
        .map(Item::getPrice)
        .reduce(0, Integer::sum);
    Integer sum9 = items.stream()
        .map(Item::getPrice)
        .reduce(0, (a, b) -> a + b);
    Integer sum10 = items.stream()
        .map(Item::getPrice)
        .collect(Collectors.summingInt(Integer::intValue));

    Integer sum11 = items.stream()
        .mapToInt(Item::getPrice)
        .sum();

    // Using Stream#sum With String
    String string = "Item1 1 Item2 2 Item3 3 Item4 4 Item5 5";

    Integer sum12 = Arrays.stream(string.split(" "))
        .filter((s) -> s.matches("\\d+"))
        .mapToInt(Integer::valueOf)
        .sum();

    System.out.println(
        sum1 + "|" + sum2 + "|" + sum3 + "|" + sum4 + "|" + sum5 + "|" + sum6 + "|" + sum7 + "|"
            + sum8 + "|" + sum9 + "|" + sum10 + "|" + sum11 + "|" + sum12);
  }


  private static int add(int a, int b) {
    return a + b;
  }
}

class Item {

  private int id;
  private Integer price;

  public Item(int id, Integer price) {
    this.id = id;
    this.price = price;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public Integer getPrice() {
    return price;
  }

  public void setPrice(Integer price) {
    this.price = price;
  }
}
