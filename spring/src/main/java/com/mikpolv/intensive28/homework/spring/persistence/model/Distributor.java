package com.mikpolv.intensive28.homework.spring.persistence.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "distributor")
public class Distributor {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  @Column(name = "id")
  private Integer id;

  @Column(name = "distributor_name", unique = true)
  @NotNull
  private String name;

  @Column(name = "distributor_contact")
  private String contact;

  @ManyToMany(mappedBy = "distributors")
  private Set<Product> products = new HashSet<>();

  public Distributor() {}

  public Distributor(@NotNull String name, String contact) {
    this.name = name;
    this.contact = contact;
  }

  public Distributor(int id, @NotNull String name, String contact) {
    this.id = id;
    this.name = name;
    this.contact = contact;
  }

  @PreRemove
  private void removeDistributorAssociations() {
    for (Product p : this.products) {
      p.getDistributors().remove(this);
    }
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

  public void setName(@NotNull String distributorName) {
    this.name = distributorName;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String distributorContact) {
    this.contact = distributorContact;
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
    if (!(o instanceof Distributor that)) return false;

    if (!getName().equals(that.getName())) return false;
    return getContact() != null
        ? getContact().equals(that.getContact())
        : that.getContact() == null;
  }

  @Override
  public int hashCode() {
    int result = getName().hashCode();
    result = 31 * result + (getContact() != null ? getContact().hashCode() : 0);
    return result;
  }

  @Override
  public String toString() {
    return "Distributor{"
        + "id="
        + id
        + ", distributorName='"
        + name
        + '\''
        + ", distributorContact='"
        + contact
        + '\''
        + '}';
  }
}
