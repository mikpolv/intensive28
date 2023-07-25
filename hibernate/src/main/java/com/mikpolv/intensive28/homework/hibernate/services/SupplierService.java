package com.mikpolv.intensive28.homework.hibernate.services;

import com.mikpolv.intensive28.homework.hibernate.enteties.Supplier;

import java.util.List;

public interface SupplierService {
  /**
   * Returns suppliers for specified product by id
   *
   * @param productId - product id
   * @return List of suppliers
   */
  List<Supplier> getProductSuppliers(int productId);
}
