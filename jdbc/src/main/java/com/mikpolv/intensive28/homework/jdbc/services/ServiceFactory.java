package com.mikpolv.intensive28.homework.jdbc.services;

import com.mikpolv.intensive28.homework.jdbc.services.impl.DefaultBrandService;
import com.mikpolv.intensive28.homework.jdbc.services.impl.DefaultProductService;
import com.mikpolv.intensive28.homework.jdbc.services.impl.DefaultSupplierService;


public class ServiceFactory {
  public static ProductService getProductService() {
    return new DefaultProductService();
  }
  public static BrandService getBrandService() {
    return new DefaultBrandService();
  }
  public static SupplierService getSupplierService() {
    return new DefaultSupplierService();
  }
}
