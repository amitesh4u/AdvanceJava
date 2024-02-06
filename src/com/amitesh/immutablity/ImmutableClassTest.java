package com.amitesh.immutablity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class ImmutableClassTest {

  public static void main(String[] args) {
    List<String> tokens = new ArrayList<>();
    tokens.add("active");

    Map<String, Integer> tokenMap = new HashMap<>();
    tokenMap.put("active", 1);

    ImmutableClass immutableClass = new ImmutableClass(1, "value", tokens, tokenMap);
    System.out.println(
        immutableClass);   //ImmutableClass{id=1, name='value', tokens=[active], tokenMap={active=1}]

    immutableClass.getTokens().add("new token");
    immutableClass.getTokenMap().put("active", 2);
    immutableClass.getTokenMap().put("disabled", 3);

    System.out.println(
        immutableClass);   //ImmutableClass{id=1, name='value', tokens=[active], tokenMap={active=1}]

    immutableClass.addToken("new token");
    immutableClass.addTokenMap("active", 2);
    immutableClass.addTokenMap("disabled", 3);
    System.out.println(
        immutableClass);   //ImmutableClass{id=1, name='value', tokens=[active], tokenMap={active=1}]
  }
}

final class ImmutableClass {

  private final long id;
  private final String name;
  private final List<String> tokens;
  private final Map<String, Integer> tokenMap;

  public ImmutableClass(long id, String name, List<String> tokens, Map<String, Integer> tokenMap) {
    this.id = id;
    this.name = name;
    this.tokens = tokens;
    this.tokenMap = tokenMap;
  }

  public long getId() {
    return id;
  }

  public String getName() {
    return name;
  }

  public List<String> getTokens() {
    return new ArrayList<>(tokens);
  }

  public List<String> addToken(final String token) {
    List<String> newTokens = new ArrayList<>(tokens);
    newTokens.add(token);
    return newTokens;
  }

  public Map<String, Integer> getTokenMap() {
    return new HashMap<>(tokenMap);
  }

  public Map<String, Integer> addTokenMap(String key, Integer value) {
    Map<String, Integer> newTokenMap = new HashMap<>(tokenMap);
    newTokenMap.put(key, value);
    return newTokenMap;
  }

  @Override
  public String toString() {
    return "ImmutableClass{" +
        "id=" + id +
        ", name='" + name + '\'' +
        ", tokens=" + tokens +
        ", tokenMap=" + tokenMap +
        '}';
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) {
      return true;
    }
    if (o == null || getClass() != o.getClass()) {
      return false;
    }
    ImmutableClass that = (ImmutableClass) o;
    return id == that.id && Objects.equals(name, that.name) && Objects.equals(
        tokens, that.tokens) && Objects.equals(tokenMap, that.tokenMap);
  }

  @Override
  public int hashCode() {
    return Objects.hash(id, name, tokens, tokenMap);
  }
}
