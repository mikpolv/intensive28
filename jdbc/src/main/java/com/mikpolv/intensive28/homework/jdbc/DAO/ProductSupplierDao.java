package com.mikpolv.intensive28.homework.jdbc.DAO;

import java.util.List;

/** Dao for join table many-to-many relationship */
public interface ProductSupplierDao {

  /**
   * Adds relationship between product and supplier.
   *
   * @param productId - product id
   * @param supplierId - supplier id
   * @return true if added successfully
   */
  boolean addSupplierForProductById(int productId, int supplierId);

  /**
   * Removes relationship between product and supplier.
   *
   * @param productId - product id
   * @param supplierId - supplier id
   * @return true if removed successfully
   */
  boolean removeSupplierForProductById(int productId, int supplierId);

  /**
   * Removes product by id. All relationship with this product will be removed.
   *
   * @param productId - product to remove
   * @return true if removed successfully
   */
  boolean removeProduct(int productId);

  /**
   * Removes supplier by id. All relationship with this supplier will be removed.
   *
   * @param supplierID - product to remove
   * @return true if removed successfully
   */
  boolean removeSupplier(int supplierID);

  /**
   * Return list of suppliers of product by specified product id.
   *
   * @param productId - product id
   * @return list of suppliers ids
   */
  List<Integer> getProductSupplierList(int productId);
}
