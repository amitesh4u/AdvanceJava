package com.amitesh.record;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 1) Records are immutable and final 2) Records can't extend but implement an Interface. Custom
 * records (implicitly) extend Record class (similar to enums extending Enum class). This is to make
 * sure that any immutable record field doesn't conflict with parent class field.
 */

public class RecordDemo {

  public static void main(String[] args) {
    TestCanonicalConstructorRecord testCanonicalConstructorRecord = new TestCanonicalConstructorRecord(
        1, "Tom",
        Arrays.asList("Apple", "Guava"));
    System.out.println(testCanonicalConstructorRecord.id());
    System.out.println(testCanonicalConstructorRecord.name()); // Should be in uppercase
    System.out.println(testCanonicalConstructorRecord.fruits());
    System.out.println(testCanonicalConstructorRecord);
    // Compilation error as fields are private with no setter by default
    //testCanonicalConstructorRecord.code = "Harry";

    System.out.println(TestCanonicalConstructorRecord.codeName);
    TestCanonicalConstructorRecord.codeName = "Harry";
    System.out.println(TestCanonicalConstructorRecord.codeName);

    TestCanonicalConstructorRecord testCanonicalConstructorRecordConstructor = new TestCanonicalConstructorRecord(
        5);
    System.out.println(testCanonicalConstructorRecordConstructor);

    System.out.println("Test Compact constructor with validation");
    try {
      new TestCompactConstructorRecord(5, "Sam", null);
    } catch (Exception e) {
      System.out.println(STR."Error: \{e.getMessage()}");
    }

    // Test Default constructor
    System.out.println(STR."Default Constructor: \{new TestCompactConstructorRecord()}");

    // Test Immutable constructor
    List<String> tokens = new ArrayList<>();
    tokens.add("active");

    Map<String, Integer> tokenMap = new HashMap<>();
    tokenMap.put("active", 1);

    ImmutableRecord immutableRecord = new ImmutableRecord(1, "value", tokens, tokenMap);
    System.out.println(
        immutableRecord);   //ImmutableRecord{id=1, name='value', tokens=[active], tokenMap={active=1}]

    immutableRecord.tokens().add("new token");
    immutableRecord.tokenMap().put("active", 2);
    immutableRecord.tokenMap().put("disabled", 3);

    System.out.println(
        immutableRecord);   //ImmutableRecord{id=1, name='value', tokens=[active], tokenMap={active=1}]

    immutableRecord.addToken("new token");
    immutableRecord.addTokenMap("active", 2);
    immutableRecord.addTokenMap("disabled", 3);
    System.out.println(
        immutableRecord);   //ImmutableRecord{id=1, name='value', tokens=[active], tokenMap={active=1}]
  }

}


record TestCanonicalConstructorRecord(int id, String name, List<String> fruits) {

  /* Instance fields are not allowed in Records. They must be listed as components during declaration
      here id, name, fruits
   */
  // public String codeName = "codeName";

  /* Records can have static fields and methods */
  public static String codeName = "codeName";

  /* Non-canonical record constructor must delegate to another canonical constructor (with all components).
    Must call internal constructor
   */
  //TestCanonicalConstructorRecord(int id){}

  TestCanonicalConstructorRecord(int id) {
    this(id, codeName, Collections.emptyList());
  }

  // Custom canonical constructor
  TestCanonicalConstructorRecord(int id, String name, List<String> fruits) {
    this.id = id;
    this.name = name;
    this.fruits = fruits;
  }

  /* Compact constructor. Can't be used with custom canonical constructor.
      Can be used for validations
   */
  //  TestCanonicalConstructorRecord {
  //    if(null == fruits) throw new IllegalArgumentException("Fruits must be present.");
  // }

  public String name() {
    return name.toUpperCase();
  }
}

record TestCompactConstructorRecord(int x, String str, List<String> fruits) implements
    Serializable {

  /* Compact constructor. Can't be used with custom canonical constructor.
      Can be used for validations
   */
  TestCompactConstructorRecord {
    if (null == fruits) {
      throw new IllegalArgumentException("Fruits must be present.");
    }
  }

  TestCompactConstructorRecord() {
    this(1, "Bob", Arrays.asList("Guava", "Pears"));
  }
}

// For Immutable Record with mutable fields do create a copy before returning/updating
record ImmutableRecord(long id, String name, List<String> tokens, Map<String, Integer> tokenMap) {

  public List<String> tokens() {
    return new ArrayList<>(tokens);
  }

  public List<String> addToken(final String token) {
    List<String> newTokens = new ArrayList<>(tokens);
    newTokens.add(token);
    return newTokens;
  }

  public Map<String, Integer> tokenMap() {
    return new HashMap<>(tokenMap);
  }

  public Map<String, Integer> addTokenMap(String key, Integer value) {
    Map<String, Integer> newTokenMap = new HashMap<>(tokenMap);
    newTokenMap.put(key, value);
    return newTokenMap;
  }
}