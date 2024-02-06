package com.amitesh.playbook;

import static java.util.Comparator.comparing;
import static java.util.Comparator.nullsLast;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.function.UnaryOperator;

public class UsefulListMethods {

  public static void main(String[] args) {
    findDuplicatesAndSimilarElements();
    replaceElementInAList();
    sortList();
  }

  private static void findDuplicatesAndSimilarElements() {
    List<String> listOne = List.of("USA", "Brazil", "USA", "France", "Egypt", "India", "Japan");

    List<String> listTwo = List.of("USA", "Brazil", "Germany", "Australia");

    Set<String> similar = new HashSet<>(listOne);
    similar.retainAll(listTwo);
    System.out.println(similar);

    Set<String> different = new HashSet<>();
    different.addAll(listOne);
    different.addAll(listTwo);
    different.removeAll(similar);

    System.out.println(different);
  }

  private static void replaceElementInAList() {
    List<Double> doubles = new ArrayList<>(List.of(10.525, 20.567, 30.789));

    UnaryOperator<Double> operator = num -> BigDecimal.valueOf(num)
        .setScale(2, RoundingMode.HALF_EVEN).doubleValue();

    UnaryOperator<Double> operator2 = num -> num < 30 ? BigDecimal.valueOf(num)
        .setScale(2, RoundingMode.HALF_EVEN).doubleValue() : num;

    UnaryOperator<Double> operator3 = num -> {
      if (num < 30) {
        return BigDecimal.valueOf(num).setScale(2, RoundingMode.HALF_EVEN).doubleValue();
      }
      return num;
    };

    doubles.replaceAll(operator);
    System.out.println(doubles);

    doubles.replaceAll(operator2);
    System.out.println(doubles);

    doubles.replaceAll(operator3);
    System.out.println(doubles);

    List<String> strings = new ArrayList<>(List.of("mary", "jake", "thomas"));
    strings.replaceAll(str -> str.substring(0, 1).toUpperCase() + str.substring(1));
    System.out.println(strings);

    List<String> newList = strings.stream()
        .map(str -> str.substring(0, 1).toUpperCase() + str.substring(1))
        .toList();
    System.out.println(newList);
  }

  private static void sortList() {
    List<Integer> ints = new ArrayList<>(List.of(20, 50, 10));
    ints.sort(Integer::compareTo);
    System.out.println(ints);

    List<Double> doubles = new ArrayList<>(List.of(30.789, 20.551, 20.55));
    doubles.sort(Double::compareTo);
    System.out.println(doubles);

    List<String> strings = new ArrayList<>(List.of("andre", "maria", "Andre"));
    strings.sort(String::compareTo);
    System.out.println(strings);

    strings.sort(Comparator.naturalOrder());
    System.out.println("Natural order: " + strings);

    strings.sort(Comparator.reverseOrder());
    System.out.println("Reverse order: " + strings);

    List<Person> personList = new ArrayList<>(Arrays.asList(
        new Person("Jake", 28),
        new Person("Don", 30),
        new Person("Andy", 40),
        new Person("John", 36),
        new Person("John", 35)
    ));

    // this doesn't work
//        personList.sort(String::compareTo);
//        personList.sort(Person::name);

    personList.sort(comparing(Person::age));
    System.out.println("Compared by age: " + personList);

    personList.sort(comparing(Person::name));
    System.out.println("Compared by name: " + personList);

    personList.sort(comparing(Person::name).reversed());
    System.out.println("Reversed by name: " + personList);

    personList.sort(comparing(Person::age).thenComparing(Person::name));
    System.out.println("By age then by name: " + personList);

    List<Person> personListWithNulls = new ArrayList<>(Arrays.asList(
        null,
        new Person("Jake", 28),
        new Person("Don", 30),
        new Person("Andy", 40),
        new Person("John", 36),
        new Person("John", 35),
        null
    ));

    personListWithNulls.sort(Comparator.nullsFirst(comparing(Person::name)));
    System.out.println(personListWithNulls);

    personListWithNulls.stream()
        .filter(Objects::nonNull)
        .sorted(Comparator.comparing(Person::age))
        .forEach(System.out::print);

    Person[] personArr = {
        null,
        new Person("Jake", 28),
        new Person("Don", 30),
        new Person("Andy", 40),
        new Person("John", 35),
        null
    };
    // Throws exception for NULL element
    //Arrays.sort(personArr, comparing(Person::name).thenComparing(Person::age));

    Arrays.sort(personArr, nullsLast(comparing(Person::name).thenComparing(Person::age)));
    System.out.println(Arrays.asList(personArr));
  }

}

record Person(String name, int age) {

  @Override
  public String toString() {
    return name + "=" + age;
  }
}