package com.amitesh.collections.comparison;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

public class Comparison {

  public static void main(String[] args) {
    List<Product> products = new ArrayList<>(Arrays.asList(
        new Product(1, "Hardwood Oak Suffolk Internal Door", "Doors", 109.99),
        new Product(2, "Oregon Cottage Interior Oak Door", "Doors", 109.99),
        new Product(3, "Oregon Cottage Horizontal Interior White Oak Door", "Doors", 189.99),
        new Product(4, "4 Panel Oak Deco Interior Door", "Doors", 209.09),
        new Product(5, "Worcester 2000 30kW Ng Combi Boiler Includes Free Comfort+ II controller",
            "Boilers", 989.99),
        new Product(6, "Glow-worm Betacom 4 30kW Combi Gas Boiler ERP", "Boilers", 787.99),
        new Product(7, "Worcester 2000 25kW Ng Combi Boiler with Free Comfort+ II controller",
            "Boilers", 859.99),
        new Product(8,
            "Wienerberger Terca Class B Engineering Brick Red 215mm x 102.5mm x 65mm (Pack of 504)",
            "Bricks", 402.99),
        new Product(9,
            "Wienerberger Terca Engineering Brick Blue Perforated Class B 65mm (Pack of 400)",
            "Bricks", 659.99),
        new Product(10, "Wienerberger Engineering Brick Red Smooth Class B 73mm - Pack of 368",
            "Bricks", 523.99)
    ));

    System.out.println(products);

    // Inline
    Comparator<Product> productComparator = Comparator.comparing(Product::getCategoryName)
        .thenComparing(Product::getPrice)
        .thenComparing(Product::getProductName);
    products.sort(productComparator);
    System.out.println(products.stream().map(Product::getId).collect(Collectors.toList()));

    // External Comparator manual logic
    products.sort(new ProductComparator());
    System.out.println(products.stream().map(Product::getId).collect(Collectors.toList()));

    // External Comparator
    products.sort(ProductComparator.compare());
    System.out.println(products.stream().map(Product::getId).collect(Collectors.toList()));

    // External Comparator in reverse and using stream to create new list
    List<Product> productList3 = products.stream().sorted(new ProductComparator().reversed())
        .toList();
    System.out.println(productList3.stream().map(Product::getId).collect(Collectors.toList()));

  }
}
