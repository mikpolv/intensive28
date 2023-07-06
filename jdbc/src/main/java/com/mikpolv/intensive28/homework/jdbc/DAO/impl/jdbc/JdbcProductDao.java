package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Product;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.function.Supplier;

public class JdbcProductDao extends JdbcGenericDaoImpl<Product, Integer> implements ProductDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBrandDao.class);

  private final ProductSupplierDao productSupplierDao;
  private final BrandDao brandDao;

  public JdbcProductDao(Supplier<Connection> connectionFactory) {
    super(connectionFactory);
    productSupplierDao = new JdbcProductSupplierDao(connectionFactory);
    brandDao = new JdbcBrandDao(connectionFactory);
  }

  @Override
  protected String getCreateQuery() {
    return "INSERT INTO product (product_name, brand_id) VALUES (?,?);";
  }

  @Override
  protected String getUpdateQuery() {
    return "UPDATE product SET product_name = ?, brand_id = ? WHERE id = ?;";
  }

  @Override
  protected String getSelectByIdQuery() {
    return "SELECT * FROM product WHERE id = ?;";
  }

  @Override
  protected String getDeleteQuery() {
    return "DELETE FROM product WHERE id = ?";
  }

  @Override
  protected String getCountRowsQuery() {
    return "SELECT count(*) FROM product;";
  }

  @Override
  protected void setIdStatementParameter(PreparedStatement statement, Integer id) {
    int idParameterIndex = 1;
    try {
      statement.setInt(idParameterIndex, id);
    } catch (SQLException e) {
      LOGGER.error("Unable to set Product Statement", e);
    }
  }

  @Override
  protected void setObjectStatement(PreparedStatement statement, Product product) {
    int parameterIndexProductName = 1;
    int parameterIndexBrandId = 2;
    int parameterIndexID = 3;
    try {
      statement.setString(parameterIndexProductName, product.getProductName());
      statement.setInt(parameterIndexBrandId, product.getBrand().getId());
      if (product.getId() != 0) {
        statement.setInt(parameterIndexID, product.getId());
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to set Product Statement", e);
    }
  }

  @Override
  protected Product readObject(ResultSet resultSet) {
    Product product = new Product();
    int brandId;
    try {

      product.setId(resultSet.getInt("id"));
      product.setProductName(resultSet.getString("product_name"));
      brandId = resultSet.getInt("brand_id");
      product.setBrand(brandDao.read(brandId));

    } catch (SQLException e) {
      LOGGER.error("Unable to read Product Id", e);
    }
    return product;
  }

  @Override
  public boolean delete(Integer productId) {
    Product product = read(productId);
    if (product != null) {
      productSupplierDao.removeProduct(product.getId());
    }
    return super.delete(productId);
  }
}
