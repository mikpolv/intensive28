package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.dao.ResistorDao;
import com.mikpolv.intensive28.homework.spring.persistence.model.Brand;
import com.mikpolv.intensive28.homework.spring.persistence.model.Resistor;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.mockito.Mockito.times;

class SpringResistorServiceTest {
  @InjectMocks private SpringResistorService testInstance;
  @Mock private ResistorDao<Resistor, Integer> testInstanceDao;
  @Mock private BrandDao<Brand, Integer> brandDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getResistor_shouldReturnOptionalRecordById() {
    Resistor resistor = getResistor();
    when(testInstanceDao.getById(resistor.getId())).thenReturn(Optional.of(resistor));
    assertEquals(resistor.getId(), testInstance.getResistorById(resistor.getId()).get().id());
    assertEquals(resistor.getName(), testInstance.getResistorById(resistor.getId()).get().name());
    assertEquals(
        resistor.getPartNumber(),
        testInstance.getResistorById(resistor.getId()).get().partNumber());
  }

  @Test
  void createResistor_shouldCreateResistor() {
    Resistor resistor = getResistor();
    resistor.setId(null);
    doNothing().when(testInstanceDao).save(resistor);
    testInstance.createResistor(resistor);
    verify(testInstanceDao, times(1)).save(resistor);
  }

  @Test
  void updateResistor_shouldUpdateResistor() {
    Resistor resistor = getResistor();
    when(testInstanceDao.existsById(resistor.getId())).thenReturn(true);
    doNothing().when(testInstanceDao).save(resistor);
    testInstance.updateResistor(resistor.getId(), resistor);
    verify(testInstanceDao, times(1)).save(resistor);
  }

  @Test
  void updateResistorWithBrandName_shouldUpdateResistor() {
    Resistor resistor = getResistor();
    String brandName = resistor.getBrand().getName();
    resistor.setBrand(null);
    when(testInstanceDao.existsById(resistor.getId())).thenReturn(true);
    doNothing().when(testInstanceDao).save(resistor);
    testInstance.updateResistorWithBrandName(resistor.getId(), resistor, brandName);
    verify(testInstanceDao, times(1)).save(resistor);
  }

  @Test
  void deleteResistor_shouldDeleteResistor() {
    Resistor resistor = getResistor();
    when(testInstanceDao.getById(resistor.getId())).thenReturn(Optional.of(resistor));
    doNothing().when(testInstanceDao).delete(resistor);
    testInstance.deleteResistor(resistor.getId());
    verify(testInstanceDao, times(1)).delete(resistor);
  }

  @Test
  void createResistorWithBrandId_shouldCreateResistorWithSpecifiedBrandId() {
    Resistor resistor = getResistor();
    resistor.setId(null);
    Brand brand = resistor.getBrand();
    resistor.setBrand(null);
    when(brandDao.getById(brand.getId())).thenReturn(Optional.of(brand));
    doNothing().when(testInstanceDao).save(resistor);
    testInstance.createResistorWithBrandId(resistor, brand.getId());
    verify(testInstanceDao, times(1)).save(resistor);
  }

  private Resistor getResistor() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brand1 = new Brand(brandIdToCreate1, brandNameToCreate1);

    int resistorIdToCreate1 = 1;
    String resistorNameToCreate1 = "NewResistor1";
    String resistorPartNumberToCreate1 = "NewResistorPN1";
    long resistance = 200;
    int voltage = 50;
    return new Resistor(
        resistorIdToCreate1,
        resistorNameToCreate1,
        resistorPartNumberToCreate1,
        brand1,
        resistance,
        voltage);
  }
}
