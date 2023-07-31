package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.dto.ResistorRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;

import java.util.Optional;

public interface ResistorService<T extends Resistor, ID extends Number> {

  /** Returns resistor by id */
  Optional<ResistorRecord> getResistorById(ID id);

  /**
   * Create resistor.
   *
   * @param resistor - to create
   */
  void createResistor(T resistor);

  /**
   * Updates resistor by specifies id.
   *
   * @param id - specified id
   * @param resistor updated resistor
   */
  void updateResistor(ID id, T resistor);

  /**
   * Updates resistor by id with specified brand name
   *
   * @param id - resistor id to update
   * @param resistor updated resistor
   * @param brandName specified brand mane
   */
  void updateResistorWithBrandName(ID id, T resistor, String brandName);

  /** Deletes resistor by id. */
  void deleteResistor(ID id);
  /**
   * Creates resistor with specified brand id
   *
   * @param resistor resistor to create
   * @param brandId specified brand id
   */
  void createResistorWithBrandId(Resistor resistor, Integer brandId);
}
