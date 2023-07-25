package com.mikpolv.intensive28.homework.hibernate.enteties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;

@Entity
@Table(name = "resistor")
public class Resistor extends Product {
  @NotNull
  @Column(name = "part_number", unique = true)
  private String partNumber;

  @Column(name = "resistance")
  private long resistance;

  @Column(name = "voltage")
  private int voltage;

  public Resistor() {}

  public Resistor(
          String productName, Brand brand, @NotNull String partNumber, long resistance, int voltage) {
    super(productName, brand);
    this.partNumber = partNumber;
    this.resistance = resistance;
    this.voltage = voltage;
  }

  public Resistor(
      int id,
      String productName,
      Brand brand,
      @NotNull String partNumber,
      long resistance,
      int voltage) {
    super(id, productName, brand);
    this.partNumber = partNumber;
    this.resistance = resistance;
    this.voltage = voltage;
  }

  public @NotNull String getPartNumber() {
    return partNumber;
  }

  public void setPartNumber(@NotNull String partNumber) {
    this.partNumber = partNumber;
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
    if (getVoltage() != resistor.getVoltage()) return false;
    return getPartNumber().equals(resistor.getPartNumber());
  }

  @Override
  public int hashCode() {
    int result = super.hashCode();
    result = 31 * result + getPartNumber().hashCode();
    result = 31 * result + (int) (getResistance() ^ (getResistance() >>> 32));
    result = 31 * result + getVoltage();
    return result;
  }

  @Override
  public String toString() {
    return "Resistor{"
        + "partNumber='"
        + partNumber
        + '\''
        + ", resistance="
        + resistance
        + ", voltage="
        + voltage
        + "} "
        + super.toString();
  }
}
