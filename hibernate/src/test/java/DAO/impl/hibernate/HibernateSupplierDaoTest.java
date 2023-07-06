package DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateSupplierDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Resistor;
import com.mikpolv.intensive28.homework.hibernate.enteties.Wire;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import enteties.*;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;


import static org.junit.jupiter.api.Assertions.*;

class HibernateSupplierDaoTest {

  private SupplierDao testInstance;

  @BeforeEach
  public void setup() throws SQLException, IOException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    java.util.function.Supplier<SessionFactory> sessionFactorySupplier =
        () -> {
          try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(Product.class);
            configuration.addAnnotatedClass(com.mikpolv.intensive28.homework.jdbc.enteties.Brand.class);
            configuration.addAnnotatedClass(com.mikpolv.intensive28.homework.jdbc.enteties.Supplier.class);
            configuration.addAnnotatedClass(Wire.class);
            configuration.addAnnotatedClass(Resistor.class);
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            configuration.setProperty(
                "hibernate.connection.url", "jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
            configuration.setProperty("hibernate.connection.username", "");
            configuration.setProperty("hibernate.connection.password", "");
            configuration.setProperty("hibernate.show_sql", "true");
            configuration.setProperty("hibernate.hbm2ddl", "create");
            ServiceRegistry serviceRegistry =
                new StandardServiceRegistryBuilder()
                    .applySettings(configuration.getProperties())
                    .build();
            return configuration.buildSessionFactory(serviceRegistry);
          } catch (Exception e) {
            throw new RuntimeException(e);
          }
        };

    testInstance = new HibernateSupplierDao(sessionFactorySupplier);

    try (Session session = sessionFactorySupplier.get().openSession()) {
      session.beginTransaction();
      Query<com.mikpolv.intensive28.homework.jdbc.enteties.Brand> q = session.createNativeQuery(ddlWithDataQuery, com.mikpolv.intensive28.homework.jdbc.enteties.Brand.class);
      q.executeUpdate();
    }
  }

  @Test
  void read_shouldReturnSupplierById() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s1 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier1", "supContact1");
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s2 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier2", "supContact2");
    testInstance.create(s1);
    testInstance.create(s2);
    assertEquals(s1, testInstance.read(s1.getId()));
    assertEquals(s2, testInstance.read(s2.getId()));
  }

  @Test
  void read_shouldThrowExceptionIfNotExist() {
    int notExistingId = 4;
    String exceptionMessage = "Such supplier id does not exist";
    Exception e =
        assertThrows(
            RuntimeException.class,
            () -> {
              testInstance.read(notExistingId);
            });
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void create_shouldCreateNewSupplier() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplier = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplier.setSupplierName("supplier");
    supplier.setSupplierContact("supplier_contact");
    int newId = testInstance.create(supplier);
    assertEquals(supplier, testInstance.read(newId));
  }

  @Test
  void update_shouldUpdateSupplier() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplier = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplier.setSupplierName("Supplier");
    supplier.setSupplierContact("supplier_contact");
    int newId = testInstance.create(supplier);
    supplier.setSupplierName("updatedSupplierName");
    assertTrue(testInstance.update(supplier));
    assertEquals(supplier.getSupplierName(), testInstance.read(newId).getSupplierName());
  }

  @Test
  void update_shouldNotUpdateSupplierIfNotExist() {
    int notExistingId = 4;
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplierToCreate = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplierToCreate.setSupplierName("Supplier");
    supplierToCreate.setSupplierContact("supplier_contact");
    int newId = testInstance.create(supplierToCreate);

    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplierToUpdate = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplierToUpdate.setId(notExistingId);
    supplierToUpdate.setSupplierName("Supplier_updated");
    supplierToUpdate.setSupplierContact("supplier_contact_updated");

    assertFalse(testInstance.update(supplierToUpdate));
    assertEquals(supplierToCreate, testInstance.read(newId));
  }

  @Test
  void delete_shouldDeleteById() {
    String exceptionMessage = "Such supplier id does not exist";
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplier = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplier.setSupplierName("Supplier");
    supplier.setSupplierContact("supplier_contact");
    int idToDelete = testInstance.create(supplier);
    assertTrue(testInstance.delete(idToDelete));
    Exception e =
        assertThrows(
            RuntimeException.class,
            () -> {
              testInstance.read(idToDelete);
            });
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 5;
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier supplier = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier();
    supplier.setSupplierName("Supplier");
    supplier.setSupplierContact("supplier_contact");
    int newId = testInstance.create(supplier);

    assertFalse(testInstance.delete(idToDelete));
    assertEquals(supplier, testInstance.read(newId));
  }
}
