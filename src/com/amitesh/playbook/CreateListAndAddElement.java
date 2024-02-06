package com.amitesh.playbook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CreateListAndAddElement {

  public static void main(String[] args) {
    final String[] strs = new String[]{"a", "b"};

    /* new Arraylist is mutable allowing to add, remove,set actions and nulls */
    System.out.println(
        "\n~~~~~~~~~~~~~~~~~ new Arraylist is mutable allowing add, remove, set actions and nulls");
    List<String> arr1 = new ArrayList<>(Arrays.asList(strs));
    System.out.println(arr1);
    arr1.add("c");
    System.out.println(arr1);
    arr1.remove("b");
    System.out.println(arr1);
    arr1.set(0, "d");
    System.out.println(arr1);
    arr1.add(null);
    System.out.println(arr1);


    /* Arrays.asList is partially mutable allowing set action and nulls but no change in size */
    System.out.println(
        "\n~~~~~~~~~~~~~~~~~ Arrays.asList is partially mutable allowing set action and nulls but no change in size");
    List<String> arr2 = Arrays.asList("a", "b", null);
    System.out.println(arr2);
    arr2.set(0, "c"); // change but you cannot resize
    System.out.println(arr2);
    try {
      arr2.add("d");
    } catch (Exception e) {
      //e.printStackTrace();
      System.out.println(STR."Error adding element: \{e.getClass().getName()}");
    }
    try {
      arr2.remove("b");
    } catch (Exception e) {
      System.out.println(STR."Error removing element: \{e.getClass().getName()}");
    }

    /* List.of is immutable and also doesn't allow Null element */
    System.out.println(
        "\n~~~~~~~~~~~~~~~~~ List.of is immutable and also doesn't allow Null element");
    List<String> arr3 = List.of("a", "b"); // prefer immutable lists
    System.out.println(arr3);

    try {
      arr3.set(0, "c"); // change but you cannot resize
    } catch (Exception e) {
      System.out.println(STR."Error setting element: \{e.getClass().getName()}");
    }

    try {
      arr3.add("d");
    } catch (Exception e) {
      System.out.println(STR."Error adding element: \{e.getClass().getName()}");
    }

    try {
      arr3.add(null);
    } catch (Exception e) {
      System.out.println(STR."Error adding NULL element: \{e.getClass().getName()}");
    }
    try {
      arr3.remove("b");
    } catch (Exception e) {
      System.out.println(STR."Error removing element: \{e.getClass().getName()}");
    }

    /* Stream toList is immutable and also doesn't allow Null element */
    List<String> arr4 = Arrays.stream(strs).toList();
    System.out.println(
        "\n~~~~~~~~~~~~~~~~~ Stream toList is immutable and also doesn't allow Null element");
    System.out.println(arr4);

    try {
      arr4.set(0, "c"); // change but you cannot resize
    } catch (Exception e) {
      System.out.println(STR."Error setting element: \{e.getClass().getName()}");
    }

    try {
      arr4.add("d");
    } catch (Exception e) {
      System.out.println(STR."Error adding element: \{e.getClass().getName()}");
    }

    try {
      arr4.add(null);
    } catch (Exception e) {
      System.out.println(STR."Error adding NULL element: \{e.getClass().getName()}");
    }
    try {
      arr4.remove("b");
    } catch (Exception e) {
      System.out.println(STR."Error removing element: \{e.getClass().getName()}");
    }

  }
}
