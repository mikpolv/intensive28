package com.mikpolv.intensive28.homework.spring.service;

public enum ProductType {
  WIRE("Wire"),
  RESISTOR("Resistor"),
  DEFAULT("Default");

  private final String typeName;

  ProductType(String typeName) {
    this.typeName = typeName;
  }

  public boolean isEquals(String passedType) {
    return this.typeName.equals(passedType);
  }

  @Override
  public String toString() {
    return typeName;
  }
}
