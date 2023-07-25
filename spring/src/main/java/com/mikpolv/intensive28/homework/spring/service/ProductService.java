package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.dto.ProductRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.Product;

import java.util.List;
import java.util.Optional;

public interface ProductService<T extends Product, ID extends Number> {
  /** Returns collection of all products. */
  List<ProductRecord> getProducts();

  /**
   * Returns optional product record by id.
   *
   * @param id - specified id
   * @return optional record of product
   */
  Optional<ProductRecord> getProductById(ID id);

  /**
   * Returns product type by specified id.
   *
   * @param id - specified id
   * @return - product type
   */
  String getProductType(ID id);

  /**
   * Sets distributor to product.
   *
   * @param productId - product id
   * @param distributorId - distributor id
   */
  void setDistributor(ID productId, ID distributorId);
}
