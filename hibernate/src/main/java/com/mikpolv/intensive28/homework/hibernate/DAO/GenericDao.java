package com.mikpolv.intensive28.homework.hibernate.DAO;

/**
 * Parametrised interface for CRUD operations
 *
 * @param <P> - Object;
 * @param <K> - Key;
 */
public interface GenericDao<P, K> {
  /**
   * Inserts new object to database; Returns ID of new object if created, return -1 if creation
   * failed;
   *
   * @param object - object to create
   * @return id of created object
   */
  int create(P object);

  /**
   * Get object by id. Throws NotExistException if not exist.
   *
   * @param id - object id
   * @return object for specified id
   */
  P read(K id);

  /**
   * Update object/
   *
   * @param object - to update
   * @return true if updated successfully
   */
  boolean update(P object);

  /**
   * Delete object by id.
   *
   * @param id - Object id
   * @return true if removed successfully
   */
  boolean delete(K id);
}
