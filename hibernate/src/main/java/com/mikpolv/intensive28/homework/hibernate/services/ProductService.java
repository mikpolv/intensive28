package com.mikpolv.intensive28.homework.hibernate.services;

import com.mikpolv.intensive28.homework.hibernate.enteties.Product;

public interface ProductService {
  /**
   * Returns product by id.
   *
   * @param id - product id
   * @return - product
   */
  Product getProductById(int id);

  /**
   * Updates existing Product
   *
   * @param product - product to update
   * @return true if update successful
   */
  boolean updateProduct(Product product);

  /**
   * Creates Product.
   *
   * @param product - product
   * @return new product id, or -1 if creation failed or brand doesn't exist
   */
  int createProduct(Product product);

  /**
   * Delete product by id.
   *
   * @param id product id to delete
   * @return true if removed successfully
   */
  boolean deleteProductById(int id);
}
