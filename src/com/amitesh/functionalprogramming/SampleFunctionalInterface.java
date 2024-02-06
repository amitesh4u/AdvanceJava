package com.amitesh.functionalprogramming;


@FunctionalInterface
public interface SampleFunctionalInterface {

  static void someStaticMethod() {
  }

    /* A functional interface can't have more than one abstract method. You can't use is with annotation.
        FunctionalInterface methods are invoked without name and on the basis of parameters, so it will be
        ambiguous for the compiler to decide which anonymous method we are trying to implement
     */
  // void anotherAbstractMethodNotAllowed(String s1, String s2);

  static void anotherStaticMethod() {
  }

  void someAbstractMethod(String s1, String s2);

  default void someDefaultMethod() {
  }

  default void anotherDefaultMethod() {
  }

}
