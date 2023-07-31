package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.dao.WireDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.persistence.model.Wire;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.math.BigDecimal;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class SpringWireServiceTest {
  @InjectMocks private SpringWireService testInstance;
  @Mock private WireDao<Wire, Integer> testInstanceDao;
  @Mock private BrandDao<Brand, Integer> brandDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getWire_shouldReturnOptionalRecordById() {
    Wire wire = getWire();
    when(testInstanceDao.getById(wire.getId())).thenReturn(Optional.of(wire));
    assertEquals(wire.getId(), testInstance.getWireById(wire.getId()).get().id());
    assertEquals(wire.getName(), testInstance.getWireById(wire.getId()).get().name());
    assertEquals(wire.getPartNumber(), testInstance.getWireById(wire.getId()).get().partNumber());
  }

  @Test
  void createWire_shouldCreateWire() {
    Wire wire = getWire();
    wire.setId(null);
    doNothing().when(testInstanceDao).save(wire);
    testInstance.createWire(wire);
    verify(testInstanceDao, times(1)).save(wire);
  }

  @Test
  void updateWire_shouldUpdateWire() {
    Wire wire = getWire();
    when(testInstanceDao.existsById(wire.getId())).thenReturn(true);
    doNothing().when(testInstanceDao).save(wire);
    testInstance.updateWire(wire.getId(), wire);
    verify(testInstanceDao, times(1)).save(wire);
  }

  @Test
  void updateWireWithBrandName_shouldUpdateWire() {
    Wire wire = getWire();
    String brandName = wire.getBrand().getName();
    wire.setBrand(null);
    when(testInstanceDao.existsById(wire.getId())).thenReturn(true);
    doNothing().when(testInstanceDao).save(wire);
    testInstance.updateWireWithBrandName(wire.getId(), wire, brandName);
    verify(testInstanceDao, times(1)).save(wire);
  }

  @Test
  void deleteWire_shouldDeleteWire() {
    Wire wire = getWire();
    when(testInstanceDao.getById(wire.getId())).thenReturn(Optional.of(wire));
    doNothing().when(testInstanceDao).delete(wire);
    testInstance.deleteWire(wire.getId());
    verify(testInstanceDao, times(1)).delete(wire);
  }

  @Test
  void createWireWithBrandId_shouldCreateWireWithSpecifiedBrandId() {
    Wire wire = getWire();
    wire.setId(null);
    Brand brand = wire.getBrand();
    wire.setBrand(null);
    when(brandDao.getById(brand.getId())).thenReturn(Optional.ofNullable(brand));
    doNothing().when(testInstanceDao).save(wire);
    testInstance.createWireWithBrandId(wire, brand.getId());
    verify(testInstanceDao, times(1)).save(wire);
  }

  private Wire getWire() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brand1 = new Brand(brandIdToCreate1, brandNameToCreate1);

    int wireIdToCreate1 = 1;
    String wireNameToCreate1 = "NewWire1";
    String wirePartNumberToCreate1 = "NewWirePN1";
    BigDecimal outsideDiameter = BigDecimal.valueOf(1);
    String awg = "18";
    return new Wire(
        wireIdToCreate1, wireNameToCreate1, wirePartNumberToCreate1, brand1, awg, outsideDiameter);
  }
}
