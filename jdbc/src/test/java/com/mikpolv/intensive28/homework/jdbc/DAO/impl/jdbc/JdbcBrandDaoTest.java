package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
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
import java.util.function.Supplier;

import static org.junit.jupiter.api.Assertions.*;

class JdbcBrandDaoTest {

  private BrandDao testInstance;
  private final Brand BRAND1 = new Brand(1, "brand_1");
  private final Brand BRAND2 = new Brand(2, "brand_2");

  @BeforeEach
  public void setup() throws IOException, SQLException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    Supplier<Connection> connectionSupplier = () -> {
      try {
        return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
      } catch (SQLException e) {
        throw new RuntimeException(e);
      }
    };

    testInstance = new JdbcBrandDao(connectionSupplier);

    try (Connection connection = connectionSupplier.get()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(ddlWithDataQuery);
    }
  }

  @Test
  void read_shouldReturnBrandsById() {
    assertEquals(BRAND1, testInstance.read(1));
    assertEquals(BRAND2, testInstance.read(2));
  }

  @Test
  void read_shouldReturnNullIfNOtExist() {
    Brand testBrand1 = testInstance.read(4);
    assertNull(testBrand1);
  }

  @Test
  void create_shouldCreateNewBrand() {
    Brand brand = new Brand();
    brand.setBrandName("Brand_3");
    int newId = testInstance.create(brand);
    brand.setId(newId);
    assertEquals(brand, testInstance.read(newId));
  }

  @Test
  void update_shouldUpdateBrand() {
    Brand brand = new Brand();
    brand.setId(1);
    brand.setBrandName("Brand_4");
    assertTrue(testInstance.update(brand));
    assertEquals(brand, testInstance.read(1));
  }

  @Test
  void update_shouldNotUpdateBrandIfNotExist() {
    Brand brand = new Brand();
    brand.setId(5);
    brand.setBrandName("Brand_4");
    assertFalse(testInstance.update(brand));
    assertEquals(BRAND1, testInstance.read(1));
    assertEquals(BRAND2, testInstance.read(2));
  }

  @Test
  void delete_shouldDeleteById() {
    Brand brand = new Brand();
    brand.setBrandName("Brand_3");
    int idToDelete = testInstance.create(brand);
    assertTrue(testInstance.delete(idToDelete));
    assertNull(testInstance.read(idToDelete));
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 5;
    assertFalse(testInstance.delete(idToDelete));
    assertEquals(BRAND1, testInstance.read(1));
    assertEquals(BRAND2, testInstance.read(2));
  }

  @Test
  void getBrands_shouldReturnListOfBrands() {
    List<Brand> brandList = testInstance.getBrands();
    assertEquals(BRAND1, brandList.get(0));
    assertEquals(BRAND2, brandList.get(1));
  }
}
