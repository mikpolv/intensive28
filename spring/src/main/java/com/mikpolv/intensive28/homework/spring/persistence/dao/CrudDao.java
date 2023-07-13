package com.mikpolv.intensive28.homework.spring.persistence.dao;

import java.util.Optional;

public interface CrudDao<T, ID> {
  void save(T entity);

  Optional<T> getById(ID id);

  boolean existsById(ID id);

  Iterable<T> findAll();

  void delete(T entity);
}
