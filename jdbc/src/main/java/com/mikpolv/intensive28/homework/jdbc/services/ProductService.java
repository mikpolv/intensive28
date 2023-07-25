package com.mikpolv.intensive28.homework.jdbc.services;

import com.mikpolv.intensive28.homework.jdbc.enteties.Product;

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
   * @param id - product id
   * @param productName - product name
   * @param brandId - brand id
   * @return true if update successful
   */
  boolean updateProduct(int id, String productName, int brandId);

  /**
   * Creates Product.
   *
   * @param productName - product name
   * @param brandId - brand id
   * @return new product id, or -1 if creation failed or brand doesn't exist
   */
  int createProduct(String productName, int brandId);

  /**
   * Delete product by id.
   *
   * @param id product id to delete
   * @return true if removed successfully
   */
  boolean deleteProductById(int id);
}
