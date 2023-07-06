package com.mikpolv.intensive28.homework.hibernate.DAO;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateProductDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateSessionFactoryProvider;
import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;

public class DaoFactory {
  public static ProductDao getProductDao() {
    return new HibernateProductDao(HibernateSessionFactoryProvider::getSessionFactory);
  }

  public static BrandDao getBrandDao() {
    return new HibernateBrandDao(HibernateSessionFactoryProvider::getSessionFactory);
  }

  public static SupplierDao getSupplierDao() {
    return new HibernateSupplierDao(HibernateSessionFactoryProvider::getSessionFactory);
  }
}
