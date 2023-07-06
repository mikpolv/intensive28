package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class JdbcProductSupplierDao implements ProductSupplierDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBrandDao.class);
  private static final int STATEMENT_EXECUTED_SUCCESSFULLY = 1;
  private final java.util.function.Supplier<Connection> connectionFactory;

  public JdbcProductSupplierDao(java.util.function.Supplier<Connection> connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  @Override
  public boolean addSupplierForProductById(int productId, int supplierId) {
    String query = "INSERT INTO product_supplier (product_id, supplier_id) VALUES (?, ?)";
    return executeQueryProductSupplier(productId, supplierId, query);
  }

  @Override
  public boolean removeSupplierForProductById(int productId, int supplierId) {
    String query = "DELETE FROM product_supplier WHERE product_id = ? AND supplier_id = ?";

    return executeQueryProductSupplier(productId, supplierId, query);
  }

  private boolean executeQueryProductSupplier(int productId, int supplierId, String query) {
    try (Connection connection = connectionFactory.get();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, productId);
      statement.setInt(2, supplierId);
      if (statement.executeUpdate() < STATEMENT_EXECUTED_SUCCESSFULLY) {
        return false;
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to set execute Product_Supplier query", e);
    }
    return true;
  }

  @Override
  public boolean removeProduct(int productId) {
    String query = "DELETE FROM product_supplier WHERE product_id = ?";

    return removeById(productId, query);
  }

  @Override
  public boolean removeSupplier(int supplierId) {
    String query = "DELETE FROM product_supplier WHERE supplier_id = ?";

    return removeById(supplierId, query);
  }

  private boolean removeById(int Id, String query) {
    try (Connection connection = connectionFactory.get();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, Id);
      if (statement.executeUpdate() < STATEMENT_EXECUTED_SUCCESSFULLY) {
        return false;
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to remove Product_Supplier query", e);
    }
    return true;
  }

  @Override
  public List<Integer> getProductSupplierList(int productId) {

    List<Integer> suppliersList = new ArrayList<>();
    String query = "SELECT * FROM product_supplier WHERE product_id = ?";

    try (Connection connection = connectionFactory.get();
        PreparedStatement statement = connection.prepareStatement(query)) {
      statement.setInt(1, productId);

      try (ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
          int supplierId = resultSet.getInt("supplier_id");
          suppliersList.add(supplierId);
        }
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to get Product_Supplier", e);
    }
    return suppliersList;
  }
}
