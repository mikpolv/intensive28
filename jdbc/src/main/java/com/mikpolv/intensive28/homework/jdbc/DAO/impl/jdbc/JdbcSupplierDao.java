package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.ProductSupplierDao;
import com.mikpolv.intensive28.homework.jdbc.DAO.SupplierDao;
import com.mikpolv.intensive28.homework.jdbc.enteties.Supplier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class JdbcSupplierDao extends JdbcGenericDaoImpl<Supplier, Integer> implements SupplierDao {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBrandDao.class);

  private final ProductSupplierDao productSupplierDao;

  public JdbcSupplierDao(java.util.function.Supplier<Connection> connectionFactory) {
    super(connectionFactory);
    productSupplierDao = new JdbcProductSupplierDao(connectionFactory);
  }

  @Override
  protected String getCreateQuery() {
    return "INSERT INTO supplier (supplier_name, supplier_contact) VALUES (?,?);";
  }

  @Override
  protected String getUpdateQuery() {
    return "UPDATE supplier SET supplier_name = ?, supplier_contact = ? WHERE id = ?;";
  }

  @Override
  protected String getSelectByIdQuery() {
    return "SELECT * FROM supplier WHERE id = ?;";
  }

  @Override
  protected String getDeleteQuery() {
    return "DELETE FROM supplier WHERE id = ?";
  }

  @Override
  protected String getCountRowsQuery() {
    return "SELECT count(*) FROM supplier;";
  }

  @Override
  protected void setIdStatementParameter(PreparedStatement statement, Integer id) {
    int idParameterIndex = 1;
    try {
      statement.setInt(idParameterIndex, id);
    } catch (SQLException e) {
      LOGGER.error("Unable to set Id to Supplier Statement", e);
    }
  }

  @Override
  protected void setObjectStatement(PreparedStatement statement, Supplier supplier) {
    int parameterIndexSupplierName = 1;
    int parameterIndexSupplierContact = 2;
    int parameterIndexID = 3;
    try {
      statement.setString(parameterIndexSupplierName, supplier.getSupplierName());
      statement.setString(parameterIndexSupplierContact, supplier.getSupplierContact());
      if (supplier.getId() != 0) {
        statement.setInt(parameterIndexID, supplier.getId());
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to set Supplier Statement", e);
    }
  }

  @Override
  protected Supplier readObject(ResultSet resultSet) {
    Supplier supplier = new Supplier();
    try {
      supplier.setId(resultSet.getInt("id"));
      supplier.setSupplierName(resultSet.getString("supplier_name"));
      supplier.setSupplierContact(resultSet.getString("supplier_contact"));

    } catch (SQLException e) {
      LOGGER.error("Unable to read Supplier Statement", e);
    }
    return supplier;
  }

  @Override
  public boolean delete(Integer supplierId) {
    Supplier supplier = read(supplierId);
    if (supplier != null) {
      productSupplierDao.removeSupplier(supplierId);
    }
    return super.delete(supplierId);
  }
}
