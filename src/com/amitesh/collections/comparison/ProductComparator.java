package com.amitesh.collections.comparison;

import java.util.Comparator;

public class ProductComparator implements Comparator<Product> {

  public static Comparator<Product> compare() {
    return Comparator.comparing(Product::getCategoryName)
        .thenComparing(Product::getPrice)
        .thenComparing(Product::getProductName);
  }

  @Override
  public int compare(Product p1, Product p2) {
    int result = p1.getCategoryName().compareTo(p2.getCategoryName());
    if (result == 0) {
      double priceDelta = p1.getPrice() - p2.getPrice();
      result = priceDelta < 0 ? -1 : (priceDelta == 0) ? 0 : 1;
    }
    if (result == 0) {
      result = p1.getProductName().compareTo(p2.getProductName());
    }
    return result;
  }
}
