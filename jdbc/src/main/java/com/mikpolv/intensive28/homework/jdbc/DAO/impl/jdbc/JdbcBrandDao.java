package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.BrandDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Brand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;


public class JdbcBrandDao extends JdbcGenericDaoImpl<Brand, Integer> implements BrandDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBrandDao.class);

  public JdbcBrandDao(Supplier<Connection> connectionFactory) {
    super(connectionFactory);
  }
  @Override
  public List<Brand> getBrands() {
    List<Brand> brandList = new ArrayList<>();
    String query = "SELECT * FROM brand";

    try (Connection connection = createConnection();
         PreparedStatement statement = connection.prepareStatement(query)) {
      try (ResultSet resultSet = statement.executeQuery()) {

        while (resultSet.next()) {
          int brandId = resultSet.getInt("id");
          brandList.add(this.read(brandId));
        }
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to get brands", e);
    }
    return brandList;
  }

  @Override
  protected String getCreateQuery() {
    return "INSERT INTO brand (brand_name) VALUES (?);";
  }

  @Override
  protected String getUpdateQuery() {
    return "UPDATE brand SET brand_name = ? WHERE id = ?;";
  }

  @Override
  protected String getSelectByIdQuery() {
    return "SELECT * FROM brand WHERE id = ?;";
  }

  @Override
  protected String getDeleteQuery() {
    return "DELETE FROM brand WHERE id = ?";
  }

  @Override
  protected String getCountRowsQuery() {
    return "SELECT count(*) FROM brand;";
  }

  @Override
  protected void setIdStatementParameter(PreparedStatement statement, Integer id) {
    int idParameterIndex = 1;
    try {
      statement.setInt(idParameterIndex, id);
    } catch (SQLException e) {
      LOGGER.error("Unable to set Brand Id to Statement", e);
    }
  }

  @Override
  protected void setObjectStatement(PreparedStatement statement, Brand brand) {
    int parameterIndexBrandName = 1;
    int parameterIndexID = 2;
    try {
      statement.setString(parameterIndexBrandName, brand.getBrandName());
      if (brand.getId() != 0) {
        statement.setInt(parameterIndexID, brand.getId());
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to set Brand Statement", e);
    }
  }

  @Override
  protected Brand readObject(ResultSet resultSet) {
    Brand brand = new Brand();
    try {

      brand.setId(resultSet.getInt("id"));
      brand.setBrandName(resultSet.getString("brand_name"));

    } catch (SQLException e) {
      LOGGER.error("Unable to read Brand", e);
    }
    return brand;
  }
}
