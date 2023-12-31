package com.mikpolv.intensive28.homework.jdbc.DAO;

import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;

import java.util.List;

public interface BrandDao extends GenericDao<Brand, Integer> {

  /**
   * @return - Returns List of all Brands
   */
  List<Brand> getBrands();
}
