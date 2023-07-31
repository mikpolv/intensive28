package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.BrandDao;
import com.mikpolv.intensive28.homework.spring.persistence.dto.BrandRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

class SpringBrandServiceTest {
  @InjectMocks private SpringBrandService testInstance;
  @Mock private BrandDao<Brand, Integer> testInstanceDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getBrands() {
    List<Brand> brands = new ArrayList<>();
    String brandNameToCreate1 = "NewBrand1";
    String brandNameToCreate2 = "NewBrand2";
    String brandNameToCreate3 = "NewBrand3";
    int brandIdToCreate1 = 1;
    int brandIdToCreate2 = 2;
    int brandIdToCreate3 = 3;
    Brand brandToCreate1 = new Brand(brandIdToCreate1, brandNameToCreate1);
    Brand brandToCreate2 = new Brand(brandIdToCreate2, brandNameToCreate2);
    Brand brandToCreate3 = new Brand(brandIdToCreate3, brandNameToCreate3);
    brands.add(brandToCreate1);
    brands.add(brandToCreate2);
    brands.add(brandToCreate3);
    when(testInstanceDao.findAll()).thenReturn(brands);
    List<BrandRecord> br = testInstance.getBrands();
    for (int i = 0; i < brands.size(); i++) {
      assertEquals(brands.get(i).getId(), (br.get(i).id()));
    }
  }

  @Test
  void getBrandById_shouldGetById() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brandToCreate1 = new Brand(brandIdToCreate1, brandNameToCreate1);
    when(testInstanceDao.getById(brandIdToCreate1)).thenReturn(Optional.of(brandToCreate1));
    assertEquals(brandIdToCreate1, testInstance.getBrandById(brandIdToCreate1).get().id());
  }

  @Test
  void createBrand_ShouldCreateBrand() {
    String brandNameToCreate1 = "NewBrand1";
    Brand brandToCreate1 = new Brand();
    brandToCreate1.setName(brandNameToCreate1);
    when(testInstanceDao.existsByName(brandNameToCreate1)).thenReturn(false);
    doNothing().when(testInstanceDao).save(brandToCreate1);
    testInstance.createBrand(brandToCreate1);
    verify(testInstanceDao, times(1)).save(brandToCreate1);
  }

  @Test
  void updateBrand_shouldUpdateBrand() {
    String brandNameToUpdate1 = "UpdatedBrand1";
    int brandIdToUpdate1 = 1;
    Brand brandToUpdate1 = new Brand(brandIdToUpdate1, brandNameToUpdate1);
    doNothing().when(testInstanceDao).save(brandToUpdate1);
    when(testInstanceDao.existsById(brandIdToUpdate1)).thenReturn(true);
    testInstance.updateBrand(brandIdToUpdate1, brandToUpdate1);
    verify(testInstanceDao, times(1)).save(brandToUpdate1);
  }

  @Test
  void deleteBrand_ShouldDeleteBrandById() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brandToCreate1 = new Brand(brandIdToCreate1, brandNameToCreate1);
    when(testInstanceDao.getById(brandIdToCreate1)).thenReturn(Optional.of(brandToCreate1));
    willDoNothing().given(testInstanceDao).delete(brandToCreate1);
    testInstance.deleteBrand(brandIdToCreate1);
    verify(testInstanceDao, times(1)).delete(brandToCreate1);
  }
}
