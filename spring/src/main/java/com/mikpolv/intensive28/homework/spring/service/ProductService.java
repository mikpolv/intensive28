package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.model.Product;

import java.util.Optional;

public interface ProductService<T extends Product, ID extends Number> {
  Iterable<Record> getProducts();

  Optional<Record> getProductById(ID id);

  String getProductType(ID id);

  void setDistributor(ID productId, ID distributorId);
}
