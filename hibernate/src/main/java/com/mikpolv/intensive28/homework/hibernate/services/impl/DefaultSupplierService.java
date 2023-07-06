package com.mikpolv.intensive28.homework.hibernate.services.impl;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;
import com.mikpolv.intensive28.homework.hibernate.exceptions.ServiceException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.mikpolv.intensive28.homework.jdbc.services.SupplierService;

import java.util.List;

public class DefaultSupplierService implements SupplierService {

  private static final Logger LOGGER = LoggerFactory.getLogger(HibernateBrandDao.class);
  SupplierDao supplierDao = DaoFactory.getSupplierDao();

  @Override
  public List<Supplier> getProductSuppliers(int productId) throws SecurityException {
    try {
      return supplierDao.getProductSuppliers(productId);
    } catch (Exception e) {
      LOGGER.error(e.getMessage());
      throw new ServiceException("Could not get update product suppliers", e);
    }
  }
}
