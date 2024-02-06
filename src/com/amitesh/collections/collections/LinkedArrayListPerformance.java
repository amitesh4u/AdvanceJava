package com.amitesh.collections.collections;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class LinkedArrayListPerformance {

  public static final int DEFAULT_CAPACITY = 1000000;
  public static final String EXEC_MESSAGE = "| Elements Size:%d | Execution Time in microsecond: %d %n";

  public static void main(String[] args) {
    long startTime;
    List<Integer> intArrayList;
    List<Integer> intLinkedList;
    List<Integer> elementsSizes = Arrays.asList(100, 10000, 100000);

    // add elements into the beginning of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToBeginning(intArrayList, elementSize);
      System.out.printf("addElementsToBeginning | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToBeginning(intLinkedList, elementSize);
      System.out.printf("addElementsToBeginning | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // add elements into the middle of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToMiddle(intArrayList, elementSize);
      System.out.printf("addElementsToMiddle | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToMiddle(intLinkedList, elementSize);
      System.out.printf("addElementsToMiddle | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // add elements into the end of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToEnd(intArrayList, elementSize);
      System.out.printf("addElementsToEnd | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      addElementsToEnd(intLinkedList, elementSize);
      System.out.printf("addElementsToEnd | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // remove elements from the beginning of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromBeginning(intArrayList, elementSize);
      System.out.printf("removeElementsFromBeginning | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromBeginning(intLinkedList, elementSize);
      System.out.printf("removeElementsFromBeginning | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // remove elements from the middle of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromMiddle(intArrayList, elementSize);
      System.out.printf("removeElementsFromMiddle | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromMiddle(intLinkedList, elementSize);
      System.out.printf("removeElementsFromMiddle | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // remove elements from the end of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromEnd(intArrayList, elementSize);
      System.out.printf("removeElementsFromEnd | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      removeElementsFromEnd(intLinkedList, elementSize);
      System.out.printf("removeElementsFromEnd | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // get elements from the beginning of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromBeginning(intArrayList, elementSize);
      System.out.printf("getElementsFromBeginning | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromBeginning(intLinkedList, elementSize);
      System.out.printf("getElementsFromBeginning | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // get elements from the middle of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromMiddle(intArrayList, elementSize);
      System.out.printf("getElementsFromMiddle | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromMiddle(intLinkedList, elementSize);
      System.out.printf("getElementsFromMiddle | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

    // get elements from the end of the List
    for (Integer elementSize : elementsSizes) {
      intArrayList = initializeArrayList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromEnd(intArrayList, elementSize);
      System.out.printf("getElementsFromEnd | ArrayList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);

      intLinkedList = initializeLinkedList(DEFAULT_CAPACITY);
      startTime = System.nanoTime();
      getElementsFromEnd(intLinkedList, elementSize);
      System.out.printf("getElementsFromEnd | LinkedList " + EXEC_MESSAGE, elementSize,
          (System.nanoTime() - startTime) / 1000);
    }

  }

  private static ArrayList<Integer> initializeArrayList(int capacity) {
    return IntStream.range(0, capacity).boxed().collect(Collectors.toCollection(ArrayList::new));
  }

  private static LinkedList<Integer> initializeLinkedList(int capacity) {
    return IntStream.range(0, capacity).boxed().collect(Collectors.toCollection(LinkedList::new));
  }

  public static void addElementsToBeginning(List<Integer> list, int numberOfElementsToAdd) {
    for (int i = 0; i < numberOfElementsToAdd; i++) {
      list.addFirst(Integer.MAX_VALUE);
    }
  }

  public static void addElementsToMiddle(List<Integer> list, int numberOfElementsToAdd) {
    for (int i = 0; i < numberOfElementsToAdd; i++) {
      list.add(list.size() / 2, Integer.MAX_VALUE);
    }
  }

  public static void addElementsToEnd(List<Integer> list, int numberOfElementsToAdd) {
    for (int i = 0; i < numberOfElementsToAdd; i++) {
      list.add(Integer.MAX_VALUE);
    }
  }

  public static void removeElementsFromBeginning(List<Integer> list, int numberOfElementsToRemove) {
    for (int i = 0; i < numberOfElementsToRemove; i++) {
      list.removeFirst();
    }
  }

  public static void removeElementsFromMiddle(List<Integer> list, int numberOfElementsToRemove) {
    for (int i = 0; i < numberOfElementsToRemove; i++) {
      list.remove(list.size() / 2);
    }
  }

  public static void removeElementsFromEnd(List<Integer> list, int numberOfElementsToRemove) {
    for (int i = 0; i < numberOfElementsToRemove; i++) {
      list.removeLast();
    }
  }

  public static void getElementsFromBeginning(List<Integer> list, int numberOfElementsToGet) {
    for (int i = 0; i < numberOfElementsToGet; i++) {
      list.getFirst();
    }
  }

  public static void getElementsFromMiddle(List<Integer> list, int numberOfElementsToGet) {
    for (int i = 0; i < numberOfElementsToGet; i++) {
      list.get(list.size() / 2);
    }
  }

  public static void getElementsFromEnd(List<Integer> list, int numberOfElementsToGet) {
    for (int i = 0; i < numberOfElementsToGet; i++) {
      list.getLast();
    }
  }

}
