package com.mikpolv.intensive28.homework.jdbc.services.impl;

import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.jdbc.services.BrandService;

import java.util.List;

public class DefaultBrandService implements BrandService {
  private final BrandDao brandDao = DaoFactory.getBrandDao();

  @Override
  public List<Brand> brandList() {
    return brandDao.getBrands();
  }
}
