package com.mikpolv.intensive28.homework.spring.persistence.dao;

import com.mikpolv.intensive28.homework.spring.persistence.model.Product;

import java.util.Optional;

public interface ProductDao<T, ID> {

  Iterable<Product> findAllProducts();

  Optional<T> getById(ID id);
}
