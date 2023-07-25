package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
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

class JdbcSupplierDaoTest {

  private SupplierDao testInstance;
  ProductSupplierDao productSupplierDao;
  private final Supplier SUPPLIER1 = new Supplier(1, "supplier_1", "supplier_contact_1");
  private final Supplier SUPPLIER2 = new Supplier(2, "supplier_2", "supplier_contact_2");

  @BeforeEach
  public void setup() throws SQLException, IOException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    java.util.function.Supplier<Connection> connectionSupplier =
        () -> {
          try {
            return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        };

    testInstance = new JdbcSupplierDao(connectionSupplier);
    productSupplierDao = new JdbcProductSupplierDao(connectionSupplier);

    try (Connection connection = connectionSupplier.get()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(ddlWithDataQuery);
    }
  }

  @Test
  void read_shouldReturnSupplierById() {
    assertEquals(SUPPLIER1, testInstance.read(1));
    assertEquals(SUPPLIER2, testInstance.read(2));
  }

  @Test
  void read_shouldReturnNullIfNOtExist() {
    Supplier testSupplier1 = testInstance.read(4);
    assertNull(testSupplier1);
  }

  @Test
  void create_shouldCreateNewSupplier() {
    Supplier supplier = new Supplier();
    supplier.setSupplierName("supplier_4");
    supplier.setSupplierContact("supplier_contact_4");
    int newId = testInstance.create(supplier);
    supplier.setId(newId);
    assertEquals(supplier, testInstance.read(newId));
  }

  @Test
  void update_shouldUpdateSupplier() {
    Supplier supplier = new Supplier();
    supplier.setId(1);
    supplier.setSupplierName("Supplier_4");
    supplier.setSupplierContact("supplier_contact_4");
    assertTrue(testInstance.update(supplier));
    assertEquals(supplier, testInstance.read(1));
  }

  @Test
  void update_shouldNotUpdateSupplierIfNotExist() {
    Supplier supplier = new Supplier();
    supplier.setId(5);
    supplier.setSupplierName("Supplier_4");
    supplier.setSupplierContact("supplier_contact_4");
    assertFalse(testInstance.update(supplier));
    assertEquals(SUPPLIER1, testInstance.read(1));
    assertEquals(SUPPLIER2, testInstance.read(2));
  }

  @Test
  void delete_shouldDeleteById() {
    int idToDelete = 2;
    assertTrue(testInstance.delete(idToDelete));
    assertNull(testInstance.read(idToDelete));
  }

  @Test
  void delete_shouldDeleteAllJoinWithProducts() {
    int idToDelete = 1;
    testInstance.delete(idToDelete);
    List<Integer> supplierListForProduct1 = productSupplierDao.getProductSupplierList(1);
    assertEquals(SUPPLIER2.getId(), supplierListForProduct1.get(0));
    List<Integer> supplierListForProduct2 = productSupplierDao.getProductSupplierList(2);
    assertEquals(SUPPLIER2.getId(), supplierListForProduct2.get(0));
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 5;
    assertFalse(testInstance.delete(idToDelete));
    assertEquals(SUPPLIER1, testInstance.read(1));
    assertEquals(SUPPLIER2, testInstance.read(2));
  }
}
