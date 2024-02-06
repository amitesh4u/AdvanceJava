package com.amitesh.collections.iteration;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class Iteration {

  public static void main(String[] args) {
    List<Integer> integers = IntStream.range(0, 10).boxed()
        .collect(Collectors.toCollection(ArrayList::new));
    Iterator<Integer> intIterator = integers.iterator();

    intIterator.next();
    intIterator.remove();
    System.out.println(intIterator);
    System.out.println(integers);
    intIterator.remove();
  }
}
