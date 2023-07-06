package com.mikpolv.intensive28.homework.hibernate.services.impl;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.BrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.hibernate.DAO.ProductDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Product;
import com.mikpolv.intensive28.homework.hibernate.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mikpolv.intensive28.homework.hibernate.services.ProductService;

public class DefaultProductService implements ProductService {
  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  private final ProductDao productDao = DaoFactory.getProductDao();

  private final BrandDao brandDao = DaoFactory.getBrandDao();

  @Override
  public Product getProductById(int id) throws SecurityException {
    return productDao.read(id);
  }

  @Override
  public boolean updateProduct(Product product) throws SecurityException {

    try {
      if (checkBrandIfExist(product.getBrand().getId())) {
        return false;
      }
      return productDao.update(product);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not update product", e);
    }
  }

  @Override
  public int createProduct(Product product) throws SecurityException {
    try {
      if (checkBrandIfExist(product.getBrand().getId())) {
        return -1;
      }
      return productDao.create(product);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could create product", e);
    }
  }

  @Override
  public boolean deleteProductById(int id) throws SecurityException {
    try {
      return productDao.delete(id);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not delete product by id", e);
    }
  }

  private boolean checkBrandIfExist(int brandId) {
    return brandDao.read(brandId) == null;
  }
}
