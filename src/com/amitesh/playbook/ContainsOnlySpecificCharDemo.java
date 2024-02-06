package com.amitesh.playbook;

import java.util.function.IntPredicate;

public class ContainsOnlySpecificCharDemo {

  private static final String ONLY_DIGITS = "123";
  private static final String NOT_ONLY_DIGITS = "123A";
  private static final String ONLY_LETTERS = "ABC";
  private static final String NOT_ONLY_LETTERS = "ABC1";


  public static void main(String[] args) {

    IntPredicate isDigit = Character::isDigit;
    IntPredicate isLetter = Character::isLetter;
    IntPredicate isLetterOrDigit = Character::isLetterOrDigit;

    System.out.println(ONLY_DIGITS + " isDigit: " + containsOnlyCharacter(ONLY_DIGITS, isDigit));
    System.out.println(ONLY_DIGITS + " isLetter: " + containsOnlyCharacter(ONLY_DIGITS, isLetter));
    System.out.println(
        NOT_ONLY_LETTERS + " isLetterOrDigit: " + containsOnlyCharacter(NOT_ONLY_LETTERS,
            isLetterOrDigit));

    System.out.println(
        NOT_ONLY_LETTERS + " isDigit: " + containsOnlyCharacter(NOT_ONLY_LETTERS, "[0-9]+"));
    System.out.println(ONLY_LETTERS + " isDigit: " + containsOnlyCharacter(ONLY_LETTERS, "[0-9]+"));
    System.out.println(ONLY_DIGITS + " isDigit: " + containsOnlyCharacter(ONLY_DIGITS, "[0-9]+"));
    System.out.println(
        NOT_ONLY_DIGITS + " isLetterOrDigit: " + containsOnlyCharacter(NOT_ONLY_DIGITS,
            "[0-9A-Za-z]+"));

  }


  public static boolean containsOnlyCharacter(String str, IntPredicate predicate) {
    return str.chars().allMatch(predicate);
  }


  public static boolean containsOnlyCharacter(String str, String regex) {
    return str.matches(regex);
  }


}
