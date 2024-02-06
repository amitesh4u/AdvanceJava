package com.amitesh.functionalprogramming.lambdaandmethodreference;

public class LambdaAndMethodReferenceDemo {

  public static void main(String[] args) {

    // ========== Lambda function demo ==========
    OrderManagement orderManagement =
        new OrderManagement(new DefaultDistanceCalculator());

    // Anonymous Function style
    orderManagement.setDistanceCalculator(new DistanceCalculator() {
      @Override
      public double calculateDistance(City city1, City city2) {
        // take into account interstate distance calculation
        return city1.getLogitude() - city2.getLogitude(); // some dummy calculations
      }
    });

    // Lambda Styles
    DistanceCalculator dCalculator = (city1, city2) -> city1.getLogitude() - city2.getLogitude();
    DistanceCalculator dCalculator2 = (city1, city2) -> {
      System.out.println("Text inside lambda function");
      return city1.getLogitude() - city2.getLogitude();
    };

    dCalculator2.calculateDistance(new City(), new City());

    // Lambda is just a definition and doesn't require exact parameters or return calculation logic
    orderManagement.setDistanceCalculator((c1, c2) -> 5.63);

    // Optimized
    orderManagement.setDistanceCalculator(
        (city1, city2) -> city1.getLogitude() - city2.getLogitude());

    // ========== Method reference demo ==========

    // Static method
    orderManagement
        .setDistanceCalculator(GoogleDistanceCalculator::getDistanceBetweenCitiesStatic);

    // Instance Method
    GoogleDistanceCalculator gdc = new GoogleDistanceCalculator();
    orderManagement
        .setDistanceCalculator(gdc::getDistanceBetweenCities);

  }

}


class OrderManagement {

  private DistanceCalculator distanceCalculator;

  public OrderManagement(DistanceCalculator distanceCalculator) {
    this.distanceCalculator = distanceCalculator;
  }

  public void setDistanceCalculator(DistanceCalculator distanceCalculator) {
    this.distanceCalculator = distanceCalculator;
  }
}


class DefaultDistanceCalculator implements DistanceCalculator {

  @Override
  public double calculateDistance(City city1, City city2) {
    return 0;
  }

}

class GoogleDistanceCalculator {

  public static double getDistanceBetweenCitiesStatic(City city1, City city2) {
    return 1;
  }

  public double getDistanceBetweenCities(City city1, City city2) {
    return 1;
  }
}
