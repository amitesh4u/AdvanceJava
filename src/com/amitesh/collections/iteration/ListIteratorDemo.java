package com.amitesh.collections.iteration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.ListIterator;


public class ListIteratorDemo {

  public static void main(String[] args) {
    List<Integer> integers = new ArrayList<>(Arrays.asList(1, 2, 3, 4, 5));

    System.out.println("Original List: " + integers);
    ListIterator<Integer> listIterator = integers.listIterator(2);

    System.out.println("Previous: " + listIterator.previous());

    //  This call can be made only if neither remove nor add have been called after the last call to next or previous.
    listIterator.set(20);
    System.out.println("List after set(20): " + integers);
    System.out.println("Next element after 'set(20)': " + listIterator.next());

    // The new element is inserted before the implicit cursor: a subsequent call to next would be unaffected,
    // and a subsequent call to previous would return the new element.
    listIterator.add(21);
    System.out.println("List after add(21): " + integers);
    System.out.println("Previous after add(21): " + listIterator.previous());

    listIterator.remove();
    System.out.println("List after removing current element: " + integers);
  }

}