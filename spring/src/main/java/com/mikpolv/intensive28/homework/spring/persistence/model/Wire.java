package com.mikpolv.intensive28.homework.spring.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "wire")
public class Wire extends Product {

  @Column(name = "awg")
  @NotNull
  private String awg;

  @Column(name = "outside_diameter")
  @NotNull
  private BigDecimal outsideDiameter;

  public Wire() {}

  public Wire(
      String productName,
      @NotNull String partNumber,
      Brand brand,
      @NotNull String awg,
      @NotNull BigDecimal outsideDiameter) {
    super(productName, partNumber, brand);
    this.awg = awg;
    this.outsideDiameter = outsideDiameter;
  }

  public Wire(
      int id,
      String productName,
      @NotNull String partNumber,
      Brand brand,
      @NotNull String awg,
      @NotNull BigDecimal outsideDiameter) {
    super(id, productName, partNumber, brand);
    this.awg = awg;
    this.outsideDiameter = outsideDiameter;
  }

  public @NotNull String getAwg() {
    return awg;
  }

  public void setAwg(@NotNull String awg) {
    this.awg = awg;
  }

  public @NotNull BigDecimal getOutsideDiameter() {
    return outsideDiameter;
  }

  public void setOutsideDiameter(@NotNull BigDecimal outsideDiameter) {
    this.outsideDiameter = outsideDiameter;
  }

  @Override
  public String toString() {
    return "Wire{"
        + "id="
        + super.getId()
        + ", productName='"
        + super.getName()
        + '\''
        + ", partNumber='"
        + super.getPartNumber()
        + '\''
        + ", brand="
        + super.getBrand().getName()
        + ", distributors="
        + super.getDistributors().toString()
        + ", awg='"
        + awg
        + '\''
        + ", outsideDiameter="
        + outsideDiameter
        + "} "
        + super.toString();
  }
}
