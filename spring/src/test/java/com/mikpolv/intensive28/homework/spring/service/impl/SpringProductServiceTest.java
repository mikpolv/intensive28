package com.mikpolv.intensive28.homework.spring.service.impl;

import com.mikpolv.intensive28.homework.spring.persistence.dao.DistributorDao;
import com.mikpolv.intensive28.homework.spring.service.ProductType;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import com.mikpolv.intensive28.homework.spring.persistence.dao.ProductDao;
import com.mikpolv.intensive28.homework.spring.persistence.dto.ProductRecord;
import com.mikpolv.intensive28.homework.spring.persistence.model.*;
import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.mockito.Mockito.*;

class SpringProductServiceTest {
  @InjectMocks private SpringProductService testInstance;
  @Mock private ProductDao<Product, Integer> testInstanceDao;
  @Mock private DistributorDao<Distributor, Integer> distributorDao;

  @BeforeEach
  public void setup() {
    MockitoAnnotations.openMocks(this);
  }

  @Test
  void getProducts_shouldReturnListWithOptionalRecords() {
    List<Product> products = new ArrayList<>();
    // generate Brands
    String brandName1 = "NewBrand1";
    String brandName2 = "NewBrand2";
    String brandName3 = "NewBrand3";
    int brandId1 = 1;
    int brandId2 = 2;
    int brandId3 = 3;
    Brand brand1 = new Brand(brandId1, brandName1);
    Brand brand2 = new Brand(brandId2, brandName2);
    Brand brand3 = new Brand(brandId3, brandName3);

    String productNameToCreate1 = "NewProduct1";
    String productNameToCreate2 = "NewProduct2";
    String productNameToCreate3 = "NewProduct3";
    String productPartNumberToCreate1 = "NewProductPN1";
    String productPartNumberToCreate2 = "NewProductPN2";
    String productPartNumberToCreate3 = "NewProductPN3";
    int productIdToCreate1 = 1;
    int productIdToCreate2 = 2;
    int productIdToCreate3 = 3;
    Product productToCreate1 =
        new Product(productIdToCreate1, productNameToCreate1, productPartNumberToCreate1, brand1);
    Product productToCreate2 =
        new Product(productIdToCreate2, productNameToCreate2, productPartNumberToCreate2, brand2);
    Product productToCreate3 =
        new Product(productIdToCreate3, productNameToCreate3, productPartNumberToCreate3, brand3);
    products.add(productToCreate1);
    products.add(productToCreate2);
    products.add(productToCreate3);
    when(testInstanceDao.findAllProducts()).thenReturn(products);
    List<ProductRecord> br = testInstance.getProducts();
    for (int i = 0; i < products.size(); i++) {
      assertEquals(products.get(i).getId(), (br.get(i).id()));
      assertEquals(products.get(i).getName(), (br.get(i).name()));
      assertEquals(products.get(i).getPartNumber(), (br.get(i).partNumber()));
    }
  }

  @Test
  void getProductById_shouldGetById() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brand1 = new Brand(brandIdToCreate1, brandNameToCreate1);

    int productIdToCreate1 = 1;
    String productNameToCreate1 = "NewProduct1";
    String productPartNumberToCreate1 = "NewProductPN1";
    Product productToCreate1 =
        new Product(productIdToCreate1, productNameToCreate1, productPartNumberToCreate1, brand1);
    when(testInstanceDao.getById(productIdToCreate1)).thenReturn(Optional.of(productToCreate1));
    assertEquals(productIdToCreate1, testInstance.getProductById(productIdToCreate1).get().id());
    assertEquals(
        productNameToCreate1, testInstance.getProductById(productIdToCreate1).get().name());
    assertEquals(
        productPartNumberToCreate1,
        testInstance.getProductById(productIdToCreate1).get().partNumber());
  }

  @Test
  void getProductType_shouldReturnProductType() {
    int resistorId = 1;
    Resistor resistor1 = new Resistor();
    resistor1.setId(resistorId);
    when(testInstanceDao.getById(resistorId)).thenReturn(Optional.of(resistor1));
    assertEquals(ProductType.RESISTOR.toString(), testInstance.getProductType(resistorId));
  }

  @Test
  void setDistributor_shouldSetDistributorByIds() {
    String brandNameToCreate1 = "NewBrand1";
    int brandIdToCreate1 = 1;
    Brand brand1 = new Brand(brandIdToCreate1, brandNameToCreate1);

    int productId1 = 1;
    String productName1 = "NewProduct1";
    String productPartNumber1 = "NewProductPN1";
    Product product1 =
        new Product(productId1, productName1, productPartNumber1, brand1);

    int distributorId1 = 1;
    String distributorName1 = "NewDistributor1";
    String distributorContact1 = "NewDistributorContact1";
    Distributor distributor1 =
        new Distributor(
            distributorId1, distributorName1, distributorContact1);
    when(testInstanceDao.getById(productId1)).thenReturn(Optional.of(product1));
    when(distributorDao.getById(distributorId1)).thenReturn(Optional.of(distributor1));
    testInstance.setDistributor(1, 1);
    assertTrue(product1.getDistributors().contains(distributor1));
    assertTrue(distributor1.getProducts().contains(product1));
  }
}
