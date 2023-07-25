package com.mikpolv.intensive28.homework.hibernate.DAO;

import com.mikpolv.intensive28.homework.hibernate.enteties.Supplier;

import java.util.List;

public interface SupplierDao extends GenericDao<Supplier, Integer> {

  /**
   * Return list of suppliers of product by specified product id.
   *
   * @param productId - product id
   * @return list of suppliers ids
   */
  List<Supplier> getProductSuppliers(Integer productId);
}
