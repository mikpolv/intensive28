package DAO.impl.hibernate;

import com.mikpolv.intensive28.homework.hibernate.DAO.impl.hibernate.HibernateBrandDao;
import com.mikpolv.intensive28.homework.hibernate.enteties.Resistor;
import com.mikpolv.intensive28.homework.hibernate.enteties.Wire;
import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class HibernateBrandDaoTest {
  private BrandDao testInstance;

  @BeforeEach
  public void setup() throws IOException {

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

    testInstance = new HibernateBrandDao(sessionFactorySupplier);

    try (Session session = sessionFactorySupplier.get().openSession()) {
      session.beginTransaction();
      Query<com.mikpolv.intensive28.homework.jdbc.enteties.Brand> q = session.createNativeQuery(ddlWithDataQuery, com.mikpolv.intensive28.homework.jdbc.enteties.Brand.class);
      q.executeUpdate();
    }
  }

  @Test
  void read_shouldReturnBrandsById() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand.setBrandName("Brand_1");
    testInstance.create(brand);
    assertEquals(brand, testInstance.read(brand.getId()));
  }

  @Test
  void read_shouldThrowExceptionIfNotExist() {
    int notExistingId = 4;
    String exceptionMessage = "Such brand id does not exist";
    Exception e = assertThrows(RuntimeException.class, () -> testInstance.read(notExistingId));
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void create_shouldCreateNewBrand() {
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand.setBrandName("Brand_3");
    testInstance.create(brand);
    assertEquals(brand, testInstance.read(brand.getId()));
  }

  @Test
  void update_shouldUpdateBrand() {

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToCreate = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToCreate.setBrandName("Brand_created");
    int newId = testInstance.create(brandToCreate);

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToUpdate = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToUpdate.setId(newId);
    brandToUpdate.setBrandName("Brand_updated");
    assertNotEquals(brandToUpdate, testInstance.read(newId));
    assertTrue(testInstance.update(brandToUpdate));
    assertEquals(brandToUpdate, testInstance.read(newId));
  }

  @Test
  void update_shouldNotUpdateBrandIfNotExist() {
    int notExistingId = 4;
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToCreate = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToCreate.setBrandName("Brand_created");
    int newId = testInstance.create(brandToCreate);

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToUpdate = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToUpdate.setId(notExistingId);
    brandToUpdate.setBrandName("Brand_updated");

    assertFalse(testInstance.update(brandToUpdate));
    assertEquals(brandToCreate, testInstance.read(newId));
  }

  @Test
  void delete_shouldDeleteById() {
    String exceptionMessage = "Such brand id does not exist";
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brand = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brand.setBrandName("Brand_3");
    int idToDelete = testInstance.create(brand);
    assertTrue(testInstance.delete(idToDelete));
    Exception e = assertThrows(RuntimeException.class, () -> testInstance.read(idToDelete));
    assertEquals(exceptionMessage, e.getMessage());
  }

  @Test
  void delete_shouldNotDeleteIfNotExist() {
    int idToDelete = 5;
    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToCreate = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToCreate.setBrandName("Brand_created");
    int newId = testInstance.create(brandToCreate);

    assertFalse(testInstance.delete(idToDelete));
    assertEquals(brandToCreate, testInstance.read(newId));
  }

  @Test
  void getBrands_shouldReturnListOfBrands() {

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToCreate1 = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToCreate1.setBrandName("Brand_created1");
    testInstance.create(brandToCreate1);

    com.mikpolv.intensive28.homework.jdbc.enteties.Brand brandToCreate2 = new com.mikpolv.intensive28.homework.jdbc.enteties.Brand();
    brandToCreate2.setBrandName("Brand_created2");
    testInstance.create(brandToCreate2);
    List<com.mikpolv.intensive28.homework.jdbc.enteties.Brand> brandList = testInstance.getBrands();
    assertEquals(brandToCreate1, brandList.get(0));
    assertEquals(brandToCreate2, brandList.get(1));
  }
}
