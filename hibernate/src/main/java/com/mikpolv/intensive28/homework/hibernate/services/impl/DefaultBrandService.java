package com.mikpolv.intensive28.homework.hibernate.services.impl;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.BrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.hibernate.enteties.Brand;
import com.mikpolv.intensive28.homework.hibernate.exceptions.ServiceException;
import com.mikpolv.intensive28.homework.hibernate.services.BrandService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.List;

public class DefaultBrandService implements BrandService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  private final BrandDao brandDao = DaoFactory.getBrandDao();

  @Override
  public List<Brand> brandList() throws ServiceException {
    try {
      return brandDao.getBrands();
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not get brand list", e);
    }
  }

  @Override
  public Brand getBrandById(int id) throws ServiceException {
    try {
      return brandDao.read(id);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not get brand by id", e);
    }
  }
}
