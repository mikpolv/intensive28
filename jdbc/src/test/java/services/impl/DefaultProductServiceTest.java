package services.impl;

import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcBrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc.JdbcProductDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import com.mikpolv.intensive28.homework.jdbc.services.impl.DefaultProductService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import static org.junit.jupiter.api.Assertions.*;

class DefaultProductServiceTest {

  private DefaultProductService testInstance;
  private final Brand BRAND1 = new Brand(1, "brand_1");
  private final Brand BRAND2 = new Brand(2, "brand_2");
  private final Product PRODUCT1 = new Product(1, "product_1", BRAND1);
  private final Product PRODUCT2 = new Product(2, "product_2", BRAND1);
  private final Product PRODUCT3 = new Product(3, "product_3", BRAND2);

  @BeforeEach
  public void setup() throws IOException, SQLException {

    String ddlWithDataQuery = Files.readString(Paths.get("src/main/resources/create_tables.sql"));

    java.util.function.Supplier<Connection> connectionSupplier =
        () -> {
          try {
            return DriverManager.getConnection("jdbc:h2:mem:test;DB_CLOSE_DELAY=-1");
          } catch (SQLException e) {
            throw new RuntimeException(e);
          }
        };

    testInstance = new DefaultProductService();
    testInstance.setProductDao(new JdbcProductDao(connectionSupplier));
    testInstance.setBrandDao(new JdbcBrandDao(connectionSupplier));

    try (Connection connection = connectionSupplier.get()) {
      Statement statement = connection.createStatement();
      statement.executeUpdate(ddlWithDataQuery);
    }
  }

  @Test
  void getProductById_shouldReturnProductById() {
    assertEquals(PRODUCT1, testInstance.getProductById(1));
    assertEquals(PRODUCT2, testInstance.getProductById(2));
    assertEquals(PRODUCT3, testInstance.getProductById(3));
  }

  @Test
  void createProduct_shouldReturnMinusOneIfBrandNotExist() {
    int notExistBrandId = 5;
    assertEquals(-1, testInstance.createProduct("product_5", notExistBrandId));
  }

  @Test
  void createProduct_shouldCreateProduct() {
    String productName = "product_5";
    int newId = testInstance.createProduct(productName, BRAND2.getId());
    Product product = new Product(newId, productName, BRAND2);
    assertEquals(product, testInstance.getProductById(newId));
  }

  @Test
  void updateProduct_shouldUpdate() {
    int productToUpdateId = 1;
    String newProductName = "product_new";
    testInstance.updateProduct(productToUpdateId, newProductName, BRAND2.getId());
    Product product = new Product(productToUpdateId, newProductName, BRAND2);
    assertEquals(product, testInstance.getProductById(productToUpdateId));
  }

  @Test
  void updateProduct_shouldReturnFalseIfNotExist() {
    int productToUpdateId = 5;
    String newProductName = "product_new";
    assertFalse(testInstance.updateProduct(productToUpdateId, newProductName, BRAND2.getId()));
  }

  @Test
  void deleteProductById_shouldDelete() {
    int productToDeleteId = 1;
    testInstance.deleteProductById(productToDeleteId);
    assertNull(testInstance.getProductById(productToDeleteId));
  }
}
