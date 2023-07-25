package com.mikpolv.intensive28.homework.jdbc.services;

import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;

import java.util.List;

public interface BrandService {
  /**
   * @return list of all brands
   */
  List<Brand> brandList();
}
