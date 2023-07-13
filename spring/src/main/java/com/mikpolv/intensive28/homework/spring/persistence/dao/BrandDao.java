package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;

import java.util.Optional;

public interface BrandDao<T extends Brand, ID extends Number> extends CrudDao<T, ID> {
  boolean existsByName(String name);

  Optional<T> getByName(String name);
}
