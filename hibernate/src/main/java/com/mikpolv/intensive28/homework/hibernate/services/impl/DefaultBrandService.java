package com.mikpolv.intensive28.homework.hibernate.services.impl;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.hibernate.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mikpolv.intensive28.homework.jdbc.services.BrandService;

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
  public Brand getBrandById(int id) throws ServiceException{
    try {
      return brandDao.read(id);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not get brand by id", e);
    }
  }
}
