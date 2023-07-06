package com.mikpolv.intensive28.homework.hibernate.enteties;

import jakarta.persistence.*;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "supplier")
public class Supplier {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private int id;

  @Column(name = "supplier_name", unique = true)
  private String supplierName;

  @Column(name = "supplier_contact")
  private String supplierContact;

  @ManyToMany(mappedBy = "suppliers")
  private Set<Product> products = new HashSet<>();

  public Supplier() {}

  public Supplier(String supplierName, String supplierContact) {
    this.supplierName = supplierName;
    this.supplierContact = supplierContact;
  }

  public Supplier(int id, String supplierName, String supplierContact) {
    this.id = id;
    this.supplierName = supplierName;
    this.supplierContact = supplierContact;
  }

  @PreRemove
  private void removeSupplierAssociations() {
    for (Product p : this.products) {
      p.getSuppliers().remove(this);
    }
  }

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public String getSupplierName() {
    return supplierName;
  }

  public void setSupplierName(String supplierName) {
    this.supplierName = supplierName;
  }

  public String getSupplierContact() {
    return supplierContact;
  }

  public void setSupplierContact(String supplierContact) {
    this.supplierContact = supplierContact;
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
    if (!(o instanceof Supplier supplier)) return false;

    if (getSupplierName() != null
        ? !getSupplierName().equals(supplier.getSupplierName())
        : supplier.getSupplierName() != null) return false;
    return getSupplierContact() != null
        ? getSupplierContact().equals(supplier.getSupplierContact())
        : supplier.getSupplierContact() == null;
  }

  @Override
  public int hashCode() {
    int result = getSupplierName() != null ? getSupplierName().hashCode() : 0;
    result = 31 * result + (getSupplierContact() != null ? getSupplierContact().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Supplier{"
        + "id="
        + id
        + ", supplierName='"
        + supplierName
        + '\''
        + ", supplierContact='"
        + supplierContact
        + '\''
        + '}';
  }
}
