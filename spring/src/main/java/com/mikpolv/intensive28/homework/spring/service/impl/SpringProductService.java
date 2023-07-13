package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.DistributorDao;
import com.mikpolv.intensive28.homework.spring.persistence.dao.ProductDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Distributor;
import com.mikpolv.intensive28.homework.spring.persistence.model.Product;
import com.mikpolv.intensive28.homework.spring.service.ProductService;
import com.mikpolv.intensive28.homework.spring.service.ProductType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpringProductService implements ProductService<Product, Integer> {

  private final ProductDao<Product, Integer> productDao;
  private final DistributorDao<Distributor, Integer> distributorDao;

  @Autowired
  public SpringProductService(
      ProductDao<Product, Integer> productDao,
      DistributorDao<Distributor, Integer> distributorDao) {
    this.productDao = productDao;
    this.distributorDao = distributorDao;
  }

  private record ProductRecord(Integer id, String name, String partNumber) {}

  @Override
  public Iterable<Record> getProducts() {
    List<Record> listOfRecords = new ArrayList<>();

    for (Product p : productDao.findAllProducts()) {
      listOfRecords.add(new ProductRecord(p.getId(), p.getName(), p.getPartNumber()));
    }
    return listOfRecords;
  }

  @Override
  public Optional<Record> getProductById(Integer id) {
    Optional<Product> optionalProduct = productDao.getById(id);
    if (optionalProduct.isPresent()) {
      Product product = optionalProduct.get();
      return Optional.of(
          new ProductRecord(product.getId(), product.getName(), product.getPartNumber()));
    }
    return Optional.empty();
  }

  @Override
  public String getProductType(Integer id) {
    Optional<Product> optionalProduct = productDao.getById(id);
    return optionalProduct
        .map(product -> product.getClass().getSimpleName())
        .orElseGet(ProductType.DEFAULT::toString);
  }

  @Override
  @Transactional
  public void setDistributor(Integer productId, Integer distributorId) {
    Optional<Product> optionalProduct = productDao.getById(productId);
    Optional<Distributor> optionalDistributor = distributorDao.getById(distributorId);

    if (optionalProduct.isPresent() && optionalDistributor.isPresent()) {
      optionalProduct.get().getDistributors().add(optionalDistributor.get());
      optionalDistributor.get().getProducts().add(optionalProduct.get());
    }
  }
}
