package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.exception.DAOException;
import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.dao.WireDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;
import com.mikpolv.intensive28.homework.spring.service.WireService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.Optional;

@Service
@Transactional(readOnly = true)
public class SpringWireService implements WireService<Wire, Integer> {

  private final WireDao<Wire, Integer> wireDao;
  private final BrandDao<Brand, Integer> brandDao;

  public record WireRecord(
      Integer id,
      String name,
      String partNumber,
      String brandName,
      String awg,
      BigDecimal outsideDiameter) {}

  @Autowired
  public SpringWireService(WireDao<Wire, Integer> wireDao, BrandDao<Brand, Integer> brandDao) {
    this.wireDao = wireDao;
    this.brandDao = brandDao;
  }

  @Override
  public Optional<Record> getWireById(Integer id) {

    Optional<Wire> optionalWire = wireDao.getById(id);
    if (optionalWire.isPresent()) {
      Wire wire = optionalWire.get();
      return Optional.of(
          new WireRecord(
              wire.getId(),
              wire.getName(),
              wire.getPartNumber(),
              wire.getBrand().getName(),
              wire.getAwg(),
              wire.getOutsideDiameter()));
    }
    return Optional.empty();
  }

  @Override
  @Transactional
  public void createWire(Wire wire) {
    wireDao.save(wire);
  }

  @Override
  @Transactional
  public void updateWire(Integer id, Wire wire) {
    if (wireDao.existsById(id)) {
      wire.setId(id);
      wireDao.save(wire);
    }
  }

  @Override
  @Transactional
  public void updateWireWithBrandName(Integer id, Wire wire, String brandName) {
    if (brandDao.getByName(brandName).isPresent()) {
      wire.setBrand(brandDao.getByName(brandName).get());
    }
    updateWire(id, wire);
  }

  @Override
  @Transactional
  public void deleteWire(Integer id) {
    Optional<Wire> optionalWire = wireDao.getById(id);
    optionalWire.ifPresent(value -> wireDao.delete(optionalWire.get()));
  }

  @Override
  @Transactional
  public void createWireWithBrandId(Wire wire, Integer brandId) {
    Optional<Brand> wireBrand = brandDao.getById(brandId);
    if (wireBrand.isPresent()) {
      wire.setBrand(wireBrand.get());
      wireBrand.get().getProducts().add(wire);
      wireDao.save(wire);
    } else throw new DAOException("Brand does not exist");
  }
}
