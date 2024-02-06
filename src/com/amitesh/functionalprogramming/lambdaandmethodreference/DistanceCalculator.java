package com.amitesh.functionalprogramming.lambdaandmethodreference;

@FunctionalInterface
public interface DistanceCalculator {

  static void someStaticMethod() {
  }

  // in FunctionalInterface it is forbidden to have two abstract methods
  //	double calculateDeliveryTime();

  double calculateDistance(City city1, City City2);

  default void someDefaultMethod() {
  }

}
