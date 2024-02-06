package com.amitesh.functionalprogramming.entities;

public interface Product {

  int getId();

  String getProductName();

  String getCategoryName();

  double getPrice();

  void setPrice(double price);
}
