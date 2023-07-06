package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
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

class JdbcProductSupplierDaoTest {
  private ProductSupplierDao testInstance;

  private final Supplier SUPPLIER1 = new Supplier(1, "supplier_1", "supplier_contact_1");
  private final Supplier SUPPLIER2 = new Supplier(2, "supplier_2", "supplier_contact_2");

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

    testInstance = new JdbcProductSupplierDao(connectionSupplier);

    try (Connection connection = connectionSupplier.get()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(ddlWithDataQuery);
    }
  }

  @Test
  void getProductSupplierList_shouldReturnSuppliersByProductId() {
    int productIdToGet = 1;
    List<Integer> supllierList = testInstance.getProductSupplierList(productIdToGet);
    assertTrue(supllierList.contains(SUPPLIER1.getId()));
    assertTrue(supllierList.contains(SUPPLIER2.getId()));
  }

  @Test
  void addSupplierForProductById_shouldAdd() {
    int productToAdd = 3;
    int supplierToAdd = 1;
    testInstance.addSupplierForProductById(productToAdd, supplierToAdd);
    List<Integer> supllierList = testInstance.getProductSupplierList(productToAdd);
    assertTrue(supllierList.contains(SUPPLIER1.getId()));
    assertTrue(supllierList.contains(SUPPLIER2.getId()));
  }

  @Test
  void removeSupplierForProductById_shouldRemove() {
    int productToDeleteId = 1;
    int supplierToDelete = 2;
    testInstance.removeSupplierForProductById(productToDeleteId, supplierToDelete);
    List<Integer> supllierList = testInstance.getProductSupplierList(productToDeleteId);
    assertFalse(supllierList.contains(SUPPLIER2.getId()));
  }

  @Test
  void removeProduct_shouldRemoveAllRelationshipsWithThisProduct() {
    int productToDeleteId = 1;
    testInstance.removeProduct(productToDeleteId);
    List<Integer> supllierList = testInstance.getProductSupplierList(productToDeleteId);
    assertTrue(supllierList.isEmpty());
  }

  @Test
  void removeSupplier_shouldRemoveAllRelationshipsWithTisSupplier() {
    int productId = 1;
    int supplierForDeleteId = 1;
    testInstance.removeSupplier(supplierForDeleteId);
    List<Integer> supllierList = testInstance.getProductSupplierList(productId);
    assertFalse(supllierList.contains(SUPPLIER1.getId()));
  }
}
