package com.mikpolv.intensive28.homework.jdbc.enteties;

import java.util.Objects;

public class Product {
  private int id;
  private String productName;
  private Brand brand;

  public Product() {}

  public Product(int id, String productName, Brand productBrand) {
    this.id = id;
    this.productName = productName;
    this.brand = productBrand;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getProductName() {
    return productName;
  }

  public void setProductName(String productName) {
    this.productName = productName;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Product product = (Product) o;

    if (id != product.id) return false;
    if (!Objects.equals(productName, product.productName)) return false;
    return Objects.equals(brand, product.brand);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (productName != null ? productName.hashCode() : 0);
    result = 31 * result + (brand != null ? brand.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", productName='"
        + productName
        + '\''
        + ", productBrand="
        + brand
        + '}';
  }
}
