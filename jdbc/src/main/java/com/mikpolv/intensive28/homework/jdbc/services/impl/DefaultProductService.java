package com.mikpolv.intensive28.homework.jdbc.services.impl;

import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.DaoFactory;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import com.mikpolv.intensive28.homework.jdbc.services.ProductService;

public class DefaultProductService implements ProductService {

  private ProductDao productDao = DaoFactory.getProductDao();

  private BrandDao brandDao = DaoFactory.getBrandDao();

  @Override
  public Product getProductById(int id) {
    return productDao.read(id);
  }

  @Override
  public boolean updateProduct(int id, String productName, int brandId) {
    if (checkBrandIfExist(brandId)) {
      return false;
    }
    Product productToUpdate = new Product();
    productToUpdate.setId(id);
    productToUpdate.setProductName(productName);
    productToUpdate.setBrand(brandDao.read(brandId));
    return productDao.update(productToUpdate);
  }

  @Override
  public int createProduct(String productName, int brandId) {

    if (checkBrandIfExist(brandId)) {
      return -1;
    }
    Product newProduct = new Product();
    newProduct.setProductName(productName);
    newProduct.setBrand(brandDao.read(brandId));
    return productDao.create(newProduct);
  }

  @Override
  public boolean deleteProductById(int id) {
    return productDao.delete(id);
  }

  private boolean checkBrandIfExist(int brandId) {
    return brandDao.read(brandId) == null;
  }
  // For testing
  public void setProductDao(ProductDao productDao) {
    this.productDao = productDao;
  }

  public void setBrandDao(BrandDao brandDao) {
    this.brandDao = brandDao;
  }
}
