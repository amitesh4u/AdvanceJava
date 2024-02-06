package com.amitesh.patternmatching;

public class PatternMatching {

  public static void main(String[] args) {
    System.out.println("isGreaterThanZero 5: " + isGreaterThanZero(5));
    System.out.println("isGreaterThanZero -1.5: " + isGreaterThanZero(-1.5));
    System.out.println("isGreaterThanZero null: " + isGreaterThanZero(null));
    System.out.println("isGreaterThanZero A: " + isGreaterThanZero("A"));
  }

  private static boolean isGreaterThanZero(Object obj) {
    // Here number is a non-final variable created using Pattern matching
    return obj instanceof Integer number && number > 0;
  }
}
