package com.mikpolv.intensive28.homework.hibernate.services;

import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;

import java.util.List;

public interface BrandService {
  /**
   * @return list of all brands
   */
  List<Brand> brandList();

  /**
   * Return brand by id.
   * @param id - specified id
   * @return brand for specified id
   */
  Brand getBrandById(int id);
}
