package com.mikpolv.intensive28.homework.hibernate.enteties;

import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "brand")
public class Brand {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "brand_name")
  private String brandName;

  @OneToMany(mappedBy = "brand")
  private List<Product> products = new ArrayList<>();

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

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand = (com.mikpolv.intensive28.homework.jdbc.enteties.Brand) o;

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
