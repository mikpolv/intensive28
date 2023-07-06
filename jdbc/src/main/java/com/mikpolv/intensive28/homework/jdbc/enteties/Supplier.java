package com.mikpolv.intensive28.homework.jdbc.enteties;

import java.util.Objects;

public class Supplier {
  private int id;
  private String supplierName;
  private String supplierContact;

  public Supplier() {}

  public Supplier(int id, String supplierName, String supplierContact) {
    this.id = id;
    this.supplierName = supplierName;
    this.supplierContact = supplierContact;
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

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (o == null || getClass() != o.getClass()) return false;

    Supplier supplier = (Supplier) o;

    if (id != supplier.id) return false;
    if (!Objects.equals(supplierName, supplier.supplierName)) return false;
    return Objects.equals(supplierContact, supplier.supplierContact);
  }

  @Override
  public int hashCode() {
    int result = id;
    result = 31 * result + (supplierName != null ? supplierName.hashCode() : 0);
    result = 31 * result + (supplierContact != null ? supplierContact.hashCode() : 0);
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
