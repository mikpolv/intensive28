package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;


import java.util.Optional;

public interface ResistorService<T extends Resistor, ID extends Number> {

  Optional<Record> getResistorById(ID id);

  void createResistor(T resistor);

  void updateResistor(ID id, T resistor);

  void updateResistorWithBrandName(ID id, T resistor, String brandName);

  void deleteResistor(ID id);

  void createResistorWithBrandId(Resistor resistor, Integer brandId);
}
