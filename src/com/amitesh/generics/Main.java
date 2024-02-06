package com.amitesh.generics;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class Main {

  public static void main(String[] args) {
    //testGenericsBasics();

    //testCommonMethod();

    //testListCasting();

    testUnknownAndFixedTypes();

  }

  private static void testUnknownAndFixedTypes() {
    List<Integer> integerList = IntStream.range(1, 100).boxed().toList();
    fixedTypeBound(integerList);
    unknownTypeBound(integerList);

    List<Double> doubleList = IntStream.range(1, 50).asDoubleStream().boxed().toList();
    fixedTypeBound(doubleList);
    unknownTypeBound(doubleList);

  }

  private static <T extends Number> void fixedTypeBound(List<T> list) {
    System.out.println("Type: " + list.get(0).getClass().getName());
    System.out.println(
        "fixedTypeBound Sum = " + list.stream().mapToDouble(Number::doubleValue).sum());
  }

  private static void unknownTypeBound(List<? extends Number> list) {
    double sum = 0;
    System.out.println("Type: " + list.get(0).getClass().getName());
    for (Number n : list) { // Always refer the value by BaseType here Number
      sum += n.doubleValue();
    }
    System.out.println("unknownTypeBound Sum = " + sum);
  }

  private static void testGenericsBasics() {
    printArray(new Integer[]{123, 456});
    printArray(new String[]{"123", "456"});
  }

  private static void testCommonMethod() {
    System.out.printf("Max of %d, %d and %d is %d\n\n", 1, 2, 3,
        maxValue(1, 2, 3));

    System.out.printf("Max of %.1f,%.1f and %.1f is %.1f\n\n",
        1.1, 2.2, 3.3, maxValue(1.1, 2.2, 3.3));

    System.out.printf("Max of %s, %s and %s is %s\n", "Audi",
        "Acura", "Aston Martin", maxValue("Audi", "Acura", "Aston Martin"));
  }

  private static void testListCasting() {
    List<Integer> integers = new ArrayList<>();

    List list = new ArrayList();
    list = integers;
    list.add("str"); // makes 'integers' a String Array

    //Integer integer = integers.get(0);
    System.out.println(integers.get(0));
  }

  private static <E> void printArray(E[] values) {
    for (E i : values) {
      System.out.println("i = " + i);
    }
  }

  /* public static <T extends Comparable<T> & Comparator<T>> T maxValue(T x, T y, T z)

  java: method maxValue in class Main cannot be applied to given types;
    required: T,T,T
    found:    int,int,int
    reason: inference variable T has incompatible bounds
      upper bounds: java.lang.Object,java.lang.Comparable<T>,java.util.Comparator<T>
      lower bounds: java.lang.Integer

   */
  public static <T extends Comparable<T>> T maxValue(T x, T y, T z) {
    T max = x; // assume x is initially the largest

    if (y.compareTo(max) > 0) {
      max = y; // y is the largest so far
    }

    if (z.compareTo(max) > 0) {
      max = z; // z is the largest now
    }
    return max; // returns the largest object
  }

  // In case a class is passed as bound, it should be passed first before interface otherwise compile time error will occur.
  //public static <T extends Comparable<T> & Number> void multipleBoundPriority(T t){}

  public static <T extends Number & Comparable<T>> void multipleBoundPriority(T t) {
  }
}