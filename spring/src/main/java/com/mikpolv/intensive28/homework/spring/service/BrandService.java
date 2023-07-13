package com.mikpolv.intensive28.homework.spring.service;

import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;

import java.util.Optional;

public interface BrandService<T extends Brand, ID extends Number> {

  Iterable<Record> getBrands();

  Optional<Record> getBrandById(ID id);

  void createBrand(T brand);

  void updateBrand(ID id, T brand);

  void deleteBrand(ID id);
}
