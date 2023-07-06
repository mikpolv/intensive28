package com.mikpolv.intensive28.homework.hibernate.enteties;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

@Entity
@Table(name = "product")
@Inheritance(strategy = InheritanceType.JOINED)
public abstract class Product {

  @Id
  @GeneratedValue(generator = "product_id_sequence", strategy = GenerationType.SEQUENCE)
  @SequenceGenerator(
      name = "product_id_sequence",
      sequenceName = "product_id_sequence",
      allocationSize = 1)
  @Column(name = "id")
  private int id;

  @NotNull
  @Column(name = "product_name")
  private String productName;

  @ManyToOne(cascade = CascadeType.MERGE)
  @JoinColumn(name = "brand_id")
  private Brand brand;

  @ManyToMany(
      cascade = {
        CascadeType.MERGE,
      })
  @JoinTable(
      name = "product_supplier",
      joinColumns = @JoinColumn(name = "product_id"),
      inverseJoinColumns = @JoinColumn(name = "supplier_id"))
  private Set<Supplier> suppliers = new HashSet<>();

  public Product() {}

  public Product(@NotNull String productName, Brand brand) {
    this.productName = productName;
    this.brand = brand;
  }

  public Product(int id, @NotNull String productName, Brand brand) {
    this.id = id;
    this.productName = productName;
    this.brand = brand;
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public @NotNull String getProductName() {
    return productName;
  }

  public void setProductName(@NotNull String productName) {
    this.productName = productName;
  }

  public Brand getBrand() {
    return brand;
  }

  public void setBrand(Brand brand) {
    this.brand = brand;
  }

  public Set<Supplier> getSuppliers() {
    return suppliers;
  }

  public void setSuppliers(Set<Supplier> suppliers) {
    this.suppliers = suppliers;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Product product)) return false;

    if (!getProductName().equals(product.getProductName())) return false;
    if (getBrand() != null ? !getBrand().equals(product.getBrand()) : product.getBrand() != null)
      return false;
    return Objects.equals(suppliers, product.suppliers);
  }

  @Override
  public int hashCode() {
    int result = getProductName().hashCode();
    result = 31 * result + (getBrand() != null ? getBrand().hashCode() : 0);
    result = 31 * result + (suppliers != null ? suppliers.hashCode() : 0);
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
        + ", brand="
        + brand
        + ", suppliers="
        + suppliers
        + '}';
  }
}
