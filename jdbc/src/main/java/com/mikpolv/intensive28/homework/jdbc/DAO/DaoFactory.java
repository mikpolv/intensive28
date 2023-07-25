package com.mikpolv.intensive28.homework.jdbc.DAO;

import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcBrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcProductDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcSupplierDao;

public class DaoFactory {
  public static ProductDao getProductDao() {
    return new JdbcProductDao(DatabaseConnectionFactory::getConnection);
  }

  public static BrandDao getBrandDao() {
    return new JdbcBrandDao(DatabaseConnectionFactory::getConnection);
  }

  public static SupplierDao getSupplierDao() {
    return new JdbcSupplierDao(DatabaseConnectionFactory::getConnection);
  }

  public static ProductSupplierDao getProductSupplierDao() {
    return new JdbcProductSupplierDao(DatabaseConnectionFactory::getConnection);
  }
}
