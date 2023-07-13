package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.exception.AlreadyExistException;
import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.service.BrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpringBrandService implements BrandService<Brand, Integer> {
  private final BrandDao<Brand, Integer> brandDao;

  private record BrandRecord(Integer id, String name) {}

  @Autowired
  public SpringBrandService(BrandDao<Brand, Integer> brandDao) {
    this.brandDao = brandDao;
  }

  @Override
  public Iterable<Record> getBrands() {
    List<Record> listOfRecords = new ArrayList<>();

    for (Brand b : brandDao.findAll()) {
      listOfRecords.add(new BrandRecord(b.getId(), b.getName()));
    }
    return listOfRecords;
  }

  @Override
  public Optional<Record> getBrandById(Integer id) {
    Optional<Brand> optionalBrand = brandDao.getById(id);
    if (optionalBrand.isPresent()) {
      Brand brand = optionalBrand.get();
      return Optional.of(new BrandRecord(brand.getId(), brand.getName()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void createBrand(Brand brand) {
    if (!brandDao.existsByName(brand.getName())) {
      brandDao.save(brand);
    } else {
      throw new AlreadyExistException("Brand with name " + brand.getName() + " already exist");
    }
  }

  @Override
  @Transactional
  public void updateBrand(Integer id, Brand brand) {
    if (brandDao.existsById(id)) {
      brand.setId(id);
      brandDao.save(brand);
    }
  }

  @Override
  @Transactional
  public void deleteBrand(Integer id) {
    Optional<Brand> optionalBrand = brandDao.getById(id);
    optionalBrand.ifPresent(value -> brandDao.delete(optionalBrand.get()));
  }
}
