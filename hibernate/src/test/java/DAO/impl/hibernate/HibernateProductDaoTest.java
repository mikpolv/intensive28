package DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateProductDao;
import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateSupplierDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Resistor;
import com.mikpolv.intensive28.homework.hibernate.enteties.Wire;
import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import enteties.*;
import org.hibernate.LazyInitializationException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateProductDaoTest {
  private ProductDao testInstance;
  private SupplierDao supplierDao;
  private BrandDao brandDao;

  private final List<com.mikpolv.intensive28.homework.jdbc.enteties.Product> productList = new ArrayList<>();

  @BeforeEach
  public void setup() throws IOException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    java.util.function.Supplier<SessionFactory> sessionFactorySupplier =
        () -> {
          try {
            Configuration configuration = new Configuration();
            configuration.addAnnotatedClass(com.mikpolv.intensive28.homework.jdbc.enteties.Product.class);
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

    testInstance = new HibernateProductDao(sessionFactorySupplier);
    supplierDao = new HibernateSupplierDao(sessionFactorySupplier);
    brandDao = new HibernateBrandDao(sessionFactorySupplier);

    try (Session session = sessionFactorySupplier.get().openSession()) {
      session.beginTransaction();
      Query<com.mikpolv.intensive28.homework.jdbc.enteties.Brand> q = session.createNativeQuery(ddlWithDataQuery, com.mikpolv.intensive28.homework.jdbc.enteties.Brand.class);
      q.executeUpdate();
    }

    createProducts();
  }

  @Test
  void read_shouldCreateAndReturnProductById() {
    for (com.mikpolv.intensive28.homework.jdbc.enteties.Product p : productList) {
      assertEquals(p, testInstance.read(p.getId()));
    }
  }

  @Test
  void read_shouldThrowExceptionIfNotExist() {
    int notExistingId = 10;
    String exceptionMessage = "Such product id does not exist";
    Exception e = assertThrows(RuntimeException.class, () -> testInstance.read(notExistingId));
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void update_shouldUpdateProduct() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Product product = productList.get(0);
    product.setProductName("updatedName");
    assertTrue(testInstance.update(product));
    assertEquals(product.getProductName(), testInstance.read(product.getId()).getProductName());
  }

  @Test
  void update_shouldNotUpdateProductIfNotExist() {
    int notExistingId = 10;
    com.mikpolv.intensive28.homework.jdbc.enteties.Product product = productList.get(0);
    product.setId(notExistingId);
    assertFalse(testInstance.update(product));
  }

  @Test
  void delete_shouldDeleteById() {
    int idToDelete = 2;
    assertTrue(testInstance.delete(idToDelete));
    String exceptionMessage = "Such product id does not exist";
    Exception e = assertThrows(RuntimeException.class, () -> testInstance.read(idToDelete));
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void delete_shouldDeleteAllJoinWithSuppliers() {
    int idToDelete = 1;
    testInstance.delete(idToDelete);
    List<com.mikpolv.intensive28.homework.jdbc.enteties.Supplier> supplierList = supplierDao.getProductSuppliers(idToDelete);
    assertTrue(supplierList.isEmpty());
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 10;
    assertFalse(testInstance.delete(idToDelete));
    for (com.mikpolv.intensive28.homework.jdbc.enteties.Product p : productList) {
      assertEquals(p, testInstance.read(p.getId()));
    }
  }

  @Test
  void getProductsLazyException_shouldThrowLazyInitException() {
    HibernateProductDao hpd = (HibernateProductDao) testInstance;
    assertThrows(
        LazyInitializationException.class,
        () -> {
          for (com.mikpolv.intensive28.homework.jdbc.enteties.Product p : hpd.getProductsLazyException()) {
            System.out.println(p);
          }
        });
  }

  @Test
  void getProducts_shouldNotThrowLazyInitException() {
    HibernateProductDao hpd = (HibernateProductDao) testInstance;
    assertDoesNotThrow(
        () -> {
          for (Product p : hpd.getProducts()) {
            System.out.println(p);
          }
        });
  }

  private void createProducts() {
    // Create brands
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand1 = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand1.setBrandName("Brand_1");
    brandDao.create(brand1);

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand2 = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand2.setBrandName("Brand_2");
    brandDao.create(brand2);

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand3 = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand3.setBrandName("Brand_3");
    brandDao.create(brand3);

    // create suppliers
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s1 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier1", "supContact1");
    supplierDao.create(s1);
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s2 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier2", "supContact2");
    supplierDao.create(s2);
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s3 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier3", "supContact3");
    supplierDao.create(s3);
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s4 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier4", "supContact4");
    supplierDao.create(s4);
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s5 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier5", "supContact5");
    supplierDao.create(s5);
    com.mikpolv.intensive28.homework.jdbc.enteties.Supplier s6 = new com.mikpolv.intensive28.homework.jdbc.enteties.Supplier("supplier6", "supContact6");
    supplierDao.create(s6);

    // create products
    Wire wire1 = new Wire("wire1", brand1, "wirePN1", "24", BigDecimal.valueOf(10));
    wire1.setSuppliers(new HashSet<>((Arrays.asList(s1, s2))));
    Wire wire2 = new Wire("wire2", brand2, "wirePN2", "20", BigDecimal.valueOf(12));
    wire2.setSuppliers(new HashSet<>((Arrays.asList(s3, s4))));
    Wire wire3 = new Wire("wire3", brand3, "wirePN3", "20", BigDecimal.valueOf(12));
    wire3.setSuppliers(new HashSet<>((Arrays.asList(s5, s6))));
    Resistor resistor1 = new Resistor("resistor1", brand1, "resistorPN1", 200, 50);
    resistor1.setSuppliers(new HashSet<>((Arrays.asList(s1, s2))));
    Resistor resistor2 = new Resistor("resistor2", brand2, "resistorPN2", 300, 130);
    resistor2.setSuppliers(new HashSet<>((Arrays.asList(s3, s4))));
    Resistor resistor3 = new Resistor("resistor3", brand3, "resistorPN3", 2020, 10);
    resistor3.setSuppliers(new HashSet<>((Arrays.asList(s5, s6))));
    testInstance.create(wire1);
    testInstance.create(wire2);
    testInstance.create(wire3);
    testInstance.create(resistor1);
    testInstance.create(resistor2);
    testInstance.create(resistor3);

    productList.add(wire1);
    productList.add(wire2);
    productList.add(wire3);
    productList.add(resistor1);
    productList.add(resistor2);
    productList.add(resistor3);
  }
}
