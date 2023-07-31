package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Product;

import java.util.Optional;

public interface ProductDao<T, ID> {
  /**
   * Returns collection of products.
   *
   * @return collection of products
   */
  Iterable<Product> findAllProducts();

  /**
   * Returns product by id.
   *
   * @param id - specified id
   * @return product by specified id
   */
  Optional<T> getById(ID id);
}
