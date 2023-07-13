package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;

import java.util.Optional;

public interface WireService<T extends Wire, ID extends Number> {

  Optional<Record> getWireById(ID id);

  void createWire(T wire);

  void updateWire(ID id, T wire);

  void updateWireWithBrandName(ID id, T wire, String brandName);

  void deleteWire(ID id);

  void createWireWithBrandId(Wire wire, Integer brandId);
}
