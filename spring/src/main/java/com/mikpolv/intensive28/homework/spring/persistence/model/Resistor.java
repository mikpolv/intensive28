package com.mikpolv.intensive28.homework.spring.persistence.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "resistor")
public class Resistor extends Product {

  @Column(name = "resistance")
  private long resistance;

  @Column(name = "voltage")
  private int voltage;

  public Resistor() {}

  public Resistor(
      String productName, @NotNull String partNumber, Brand brand, long resistance, int voltage) {
    super(productName, partNumber, brand);
    this.resistance = resistance;
    this.voltage = voltage;
  }

  public Resistor(
      int id,
      String productName,
      @NotNull String partNumber,
      Brand brand,
      long resistance,
      int voltage) {
    super(id, productName, partNumber, brand);
    this.resistance = resistance;
    this.voltage = voltage;
  }

  public long getResistance() {
    return resistance;
  }

  public void setResistance(long resistance) {
    this.resistance = resistance;
  }

  public int getVoltage() {
    return voltage;
  }

  public void setVoltage(int voltage) {
    this.voltage = voltage;
  }

  @Override
  public boolean equals(Object o) {
    if (this == o) return true;
    if (!(o instanceof Resistor resistor)) return false;
    if (!super.equals(o)) return false;

    if (getResistance() != resistor.getResistance()) return false;
    return getVoltage() == resistor.getVoltage();
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + (int) (getResistance() ^ (getResistance() >>> 32));
    result = 31 * result + getVoltage();
    return result;
  }

  @Override
  public String toString() {
    return "Resistor{"
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
        + ", resistance="
        + resistance
        + ", voltage="
        + voltage
        + "} "
        + super.toString();
  }
}
