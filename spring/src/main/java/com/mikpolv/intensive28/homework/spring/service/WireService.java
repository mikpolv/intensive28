package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.dto.WireRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;

import java.util.Optional;

public interface WireService<T extends Wire, ID extends Number> {
  /** Returns wire by id */
  Optional<WireRecord> getWireById(ID id);
  /**
   * Create wire.
   *
   * @param wire - to create
   */
  void createWire(T wire);
  /**
   * Updates wire by specifies id.
   *
   * @param id - specified id
   * @param wire updated wire
   */
  void updateWire(ID id, T wire);
  /**
   * Updates wire by id with specified brand name
   *
   * @param id - wire id to update
   * @param wire updated wire
   * @param brandName specified brand mane
   */
  void updateWireWithBrandName(ID id, T wire, String brandName);
  /** Deletes wire by id. */
  void deleteWire(ID id);
  /**
   * Creates wire with specified brand id
   *
   * @param wire wire to create
   * @param brandId specified brand id
   */
  void createWireWithBrandId(Wire wire, Integer brandId);
}
