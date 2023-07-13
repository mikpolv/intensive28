package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.exception.DAOException;
import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.dao.ResistorDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.*;
import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;
import com.mikpolv.intensive28.homework.spring.service.ResistorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpringResistorService implements ResistorService<Resistor, Integer> {

  private final ResistorDao<Resistor, Integer> resistorDao;
  private final BrandDao<Brand, Integer> brandDao;

  public record ResistorRecord(
      Integer id,
      String name,
      String partNumber,
      String brandName,
      Long resistance,
      Integer voltage) {}

  @Autowired
  public SpringResistorService(
      ResistorDao<Resistor, Integer> resistorDao, BrandDao<Brand, Integer> brandDao) {
    this.resistorDao = resistorDao;
    this.brandDao = brandDao;
  }

  @Override
  public Optional<Record> getResistorById(Integer id) {

    Optional<Resistor> optionalResistor = resistorDao.getById(id);
    if (optionalResistor.isPresent()) {
      Resistor resistor = optionalResistor.get();
      return Optional.of(
          new ResistorRecord(
              resistor.getId(),
              resistor.getName(),
              resistor.getPartNumber(),
              resistor.getBrand().getName(),
              resistor.getResistance(),
              resistor.getVoltage()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void createResistor(Resistor resistor) {
    resistorDao.save(resistor);
  }

  @Override
  @Transactional
  public void updateResistor(Integer id, Resistor resistor) {
    if (resistorDao.existsById(id)) {
      resistor.setId(id);
      resistorDao.save(resistor);
    }
  }

  @Override
  @Transactional
  public void updateResistorWithBrandName(Integer id, Resistor resistor, String brandName) {
    if (brandDao.getByName(brandName).isPresent()) {
      resistor.setBrand(brandDao.getByName(brandName).get());
    }
    updateResistor(id, resistor);
  }

  @Override
  @Transactional
  public void deleteResistor(Integer id) {
    Optional<Resistor> optionalResistor = resistorDao.getById(id);
    optionalResistor.ifPresent(value -> resistorDao.delete(optionalResistor.get()));
  }

  @Override
  @Transactional
  public void createResistorWithBrandId(Resistor resistor, Integer brandId) {
    Optional<Brand> resistorBrand = brandDao.getById(brandId);
    if (resistorBrand.isPresent()) {
      resistor.setBrand(resistorBrand.get());
      resistorBrand.get().getProducts().add(resistor);
      resistorDao.save(resistor);
    } else throw new DAOException("Brand does not exist");
  }
}
