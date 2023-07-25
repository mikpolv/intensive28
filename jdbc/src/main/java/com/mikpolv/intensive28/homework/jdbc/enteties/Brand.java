package com.mikpolv.intensive28.homework.jdbc.enteties;

import java.util.Objects;

public class Brand {
  private int id;
  private String brandName;

  public Brand() {}

  public Brand(int id, String brandName) {
    this.id = id;
    this.brandName = brandName;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getBrandName() {
    return brandName;
  }

  public void setBrandName(String brandName) {
    this.brandName = brandName;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Brand brand = (Brand) o;

    if (id != brand.id) return false;
    return Objects.equals(brandName, brand.brandName);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (brandName != null ? brandName.hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Brand{" + "id=" + id + ", brandName='" + brandName + '\'' + '}';
  }
}
