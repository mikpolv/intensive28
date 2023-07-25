package com.mikpolv.intensive28.homework.jdbc.services;

import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;

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
