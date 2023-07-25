package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.ProductDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class JdbcProductDaoTest {

  private ProductDao testInstance;
  ProductSupplierDao productSupplierDao;
  private final Brand BRAND1 = new Brand(1, "brand_1");
  private final Brand BRAND2 = new Brand(2, "brand_2");
  private final Product PRODUCT1 = new Product(1, "product_1", BRAND1);
  private final Product PRODUCT2 = new Product(2, "product_2", BRAND1);
  private final Product PRODUCT3 = new Product(3, "product_3", BRAND2);

  @BeforeEach
  public void setup() throws IOException, SQLException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    java.util.function.Supplier<Connection> connectionSupplier = () -> {
      try {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    };


    try (Connection connection = connectionSupplier.get()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(ddlWithDataQuery);
    }
    testInstance = new JdbcProductDao(connectionSupplier);
    productSupplierDao = new JdbcProductSupplierDao(connectionSupplier);
  }

  @Test
  void read_shouldReturnProductById() {
    assertEquals(PRODUCT1, testInstance.read(1));
    assertEquals(PRODUCT2, testInstance.read(2));
    assertEquals(PRODUCT3, testInstance.read(3));
  }

  @Test
  void read_shouldReturnNullIfNOtExist() {
    Product testProduct1 = testInstance.read(4);
    assertNull(testProduct1);
  }

  @Test
  void create_shouldCreateNewProduct() {
    Product product = new Product();
    product.setProductName("Product_4");
    product.setBrand(BRAND2);
    int newId = testInstance.create(product);
    product.setId(newId);
    assertEquals(product, testInstance.read(newId));
  }

  @Test
  void update_shouldUpdateProduct() {
    Product product = new Product();
    product.setId(1);
    product.setProductName("Product_4");
    product.setBrand(BRAND2);
    assertTrue(testInstance.update(product));
    assertEquals(product, testInstance.read(1));
  }

  @Test
  void update_shouldNotUpdateProductIfNotExist() {
    Product product = new Product();
    product.setId(5);
    product.setProductName("Product_4");
    product.setBrand(BRAND2);
    assertFalse(testInstance.update(product));
    assertEquals(PRODUCT1, testInstance.read(1));
    assertEquals(PRODUCT2, testInstance.read(2));
    assertEquals(PRODUCT3, testInstance.read(3));
  }

  @Test
  void delete_shouldDeleteById() {
    int idToDelete = 2;
    assertTrue(testInstance.delete(idToDelete));
    assertNull(testInstance.read(idToDelete));
  }

  @Test
  void delete_shouldDeleteAllJoinWithSuppliers() {
    int idToDelete = 1;
    testInstance.delete(idToDelete);
    List<Integer> supplierList = productSupplierDao.getProductSupplierList(1);
    assertTrue(supplierList.isEmpty());
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 5;
    assertFalse(testInstance.delete(idToDelete));
    assertEquals(PRODUCT1, testInstance.read(1));
    assertEquals(PRODUCT2, testInstance.read(2));
    assertEquals(PRODUCT3, testInstance.read(3));
  }
}
