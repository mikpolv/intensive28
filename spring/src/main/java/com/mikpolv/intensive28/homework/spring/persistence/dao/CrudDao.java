package com.mikpolv.intensive28.homework.spring.persistence.dao;

import java.util.Optional;
/**
 * Parametrised interface for CRUD operations
 *
 * @param <T> - Object;
 * @param <ID> - Key;
 */
public interface CrudDao<T, ID> {
  /**
   * Inserts or update object to database.
   *
   * @param entity - object to save;
   */
  void save(T entity);

  /**
   * Returns entity by id.
   *
   * @param id - specified id;
   * @return Optional entity;
   */
  Optional<T> getById(ID id);

  /**
   * Check if entity exist by id.
   *
   * @param id - specified id;
   * @return true if exists;
   */
  boolean existsById(ID id);

  /**
   * Returns collection of all entities.
   *
   * @return collection of entities;
   */
  Iterable<T> findAll();

  /**
   * Removes entity.
   *
   * @param entity - to remove;
   */
  void delete(T entity);
}
