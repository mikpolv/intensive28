package com.mikpolv.intensive28.homework.hibernate.enteties;

import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

@Entity
@Table(name = "wire")
public class Wire extends Product {
  @Column(name = "part_number", unique = true)
  @NotNull
  private String partNumber;

  @Column(name = "awg")
  @NotNull
  private String awg;

  @Column(name = "outside_diameter")
  @NotNull
  private BigDecimal outsideDiameter;

  public Wire() {}

  public Wire(
      String productName,
      com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand,
      @NotNull String partNumber,
      @NotNull String awg,
      @NotNull BigDecimal outsideDiameter) {
    super(productName, brand);
    this.partNumber = partNumber;
    this.awg = awg;
    this.outsideDiameter = outsideDiameter;
  }

  public Wire(
      int id,
      String productName,
      Brand brand,
      @NotNull String partNumber,
      @NotNull String awg,
      @NotNull BigDecimal outsideDiameter) {
    super(id, productName, brand);
    this.partNumber = partNumber;
    this.awg = awg;
    this.outsideDiameter = outsideDiameter;
  }

  public @NotNull String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(@NotNull String partNumber) {
    this.partNumber = partNumber;
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
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Wire wire)) return false;
    if (!super.equals(o)) return false;

    if (!getPartNumber().equals(wire.getPartNumber())) return false;
    if (!getAwg().equals(wire.getAwg())) return false;
    return getOutsideDiameter().equals(wire.getOutsideDiameter());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getPartNumber().hashCode();
    result = 31 * result + getAwg().hashCode();
    result = 31 * result + getOutsideDiameter().hashCode();
    return result;
  }

  @Override
  public String toString() {
    return "Wire{"
        + "partNumber='"
        + partNumber
        + '\''
        + ", awg='"
        + awg
        + '\''
        + ", outsideDiameter="
        + outsideDiameter
        + "} "
        + super.toString();
  }
}
