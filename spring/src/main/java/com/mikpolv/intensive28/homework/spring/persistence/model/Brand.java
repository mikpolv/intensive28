package com.mikpolv.intensive28.homework.spring.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "brand")
public class Brand {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "brand_name", unique = true)
  @NotNull
  private String name;

  @OneToMany(mappedBy = "brand")
  private Set<Product> products = new HashSet<>();

  public Brand() {}

  public Brand(@NotNull String name) {
    this.name = name;
  }

  public Brand(int id, @NotNull String name) {
    this.id = id;
    this.name = name;
  }

  public Integer getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public @NotNull String getName() {
    return name;
  }

  public void setName(@NotNull String brandName) {
    this.name = brandName;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public Set<Product> getProducts() {
    return products;
  }

  public void setProducts(Set<Product> products) {
    this.products = products;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Brand brand)) return false;

    if (!getName().equals(brand.getName())) return false;
    return Objects.equals(products, brand.products);
  }

  @Override
  public int hashCode() {
    return getName().hashCode();
  }

  @Override
  public String toString() {
    return "Brand{" + "id=" + id + ", brandName='" + name + '\'' + '}';
  }
}
