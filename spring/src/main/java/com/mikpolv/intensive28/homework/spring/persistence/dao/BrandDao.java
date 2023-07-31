package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;

import java.util.Optional;

public interface BrandDao<T extends Brand, ID extends Number> extends CrudDao<T, ID> {

  /**
   * Check if the brand with such name is exist.
   *
   * @param name - brand name
   * @return true if exist
   */
  boolean existsByName(String name);

  /**
   * Return Optional Brand by name.
   * @param name - brand name
   * @return Optional brand
   */
  Optional<T> getByName(String name);
}
