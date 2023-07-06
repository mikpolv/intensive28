package com.mikpolv.intensive28.homework.jdbc.services.impl;

import com.mikpolv.intensive28.homework.jdbc.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;
import com.mikpolv.intensive28.homework.jdbc.services.SupplierService;

import java.util.ArrayList;
import java.util.List;

public class DefaultSupplierService implements SupplierService {
  ProductSupplierDao productSupplierDao = DaoFactory.getProductSupplierDao();
  SupplierDao supplierDao = DaoFactory.getSupplierDao();

  @Override
  public List<Supplier> getProductSuppliers(int productId) {
    List<Supplier> supplierList = new ArrayList<>();
    for (int supplierId : productSupplierDao.getProductSupplierList(productId)) {
      supplierList.add(supplierDao.read(supplierId));
    }
    return supplierList;
  }
}
