package com.mikpolv.intensive28.homework.spring.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public class Product {

  @Id
  @GeneratedValue(generator = "product_id_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "product_id_sequence",
      sequenceName = "product_id_sequence",
      allocationSize = 1)
  @Column(name = "id")
  private Integer id;

  @NotNull
  @Column(name = "product_name")
  private String name;

  @Column(name = "part_number", unique = true)
  @NotNull
  private String partNumber;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "brand_id", referencedColumnName = "id")
  private Brand brand;

  @ManyToMany
  @JoinTable(
      name = "product_distributor",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "distributor_id"))
  private Set<Distributor> distributors = new HashSet<>();

  public Product() {}

  public Product(@NotNull String name, @NotNull String partNumber, Brand brand) {
    this.name = name;
    this.partNumber = partNumber;
    this.brand = brand;
  }

  public Product(int id, @NotNull String name, @NotNull String partNumber, Brand brand) {
    this.id = id;
    this.name = name;
    this.partNumber = partNumber;
    this.brand = brand;
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
    this.id = id;
  }

  public @NotNull String getName() {
    return name;
  }

  public void setName(@NotNull String productName) {
    this.name = productName;
  }

  public @NotNull String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(@NotNull String partNumber) {
    this.partNumber = partNumber;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public void setDistributors(Set<Distributor> distributors) {
    this.distributors = distributors;
  }

  public Set<Distributor> getDistributors() {
    return distributors;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product product)) return false;

    if (!getName().equals(product.getName())) return false;
    if (!getPartNumber().equals(product.getPartNumber())) return false;
    return getBrand() != null ? getBrand().equals(product.getBrand()) : product.getBrand() == null;
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + getPartNumber().hashCode();
    result = 31 * result + (getBrand() != null ? getBrand().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Product{"
        + "id="
        + id
        + ", productName='"
        + name
        + '\''
        + ", partNumber='"
        + partNumber
        + '\''
        + ", brand="
        + brand
        + ", distributors="
        + distributors
        + '}';
  }
}
