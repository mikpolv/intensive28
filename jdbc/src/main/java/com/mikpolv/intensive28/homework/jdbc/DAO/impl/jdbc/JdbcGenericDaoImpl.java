package com.mikpolv.intensive28.homework.jdbc.DAO.impl.jdbc;

import com.mikpolv.intensive28.homework.jdbc.DAO.GenericDao;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;
import java.util.function.Supplier;

/** Abstract to create queries, and implement CRUD Operation */
abstract class JdbcGenericDaoImpl<P, K> implements GenericDao<P, K> {

  private static final Logger LOGGER = LoggerFactory.getLogger(JdbcBrandDao.class);
  private static final int STATEMENT_EXECUTED_SUCCESSFULLY = 1;
  private static final int OBJECT_ADD_ABORTED = -1;
  protected final Supplier<Connection> connectionFactory;

  protected JdbcGenericDaoImpl(Supplier<Connection> connectionFactory) {
    this.connectionFactory = connectionFactory;
  }

  protected abstract String getCreateQuery();

  protected abstract String getUpdateQuery();

  protected abstract String getSelectByIdQuery();

  protected abstract String getDeleteQuery();

  protected abstract void setIdStatementParameter(PreparedStatement preparedStatement, K id);

  protected abstract void setObjectStatement(PreparedStatement preparedStatement, P object);

  protected abstract P readObject(ResultSet resultSet);

  protected abstract String getCountRowsQuery();

  protected final Connection createConnection() {
    return connectionFactory.get();
  }

  @Override
  public int create(P object) {

    int newId;

    if (object == null) {
      return OBJECT_ADD_ABORTED;
    }

    String createQuery = getCreateQuery();

    try (Connection connection = createConnection();
        PreparedStatement statement =
            connection.prepareStatement(createQuery, Statement.RETURN_GENERATED_KEYS)) {

      setObjectStatement(statement, object);
      if (statement.executeUpdate() < STATEMENT_EXECUTED_SUCCESSFULLY) {
        return OBJECT_ADD_ABORTED;
      }
      try (ResultSet generatedKeys = statement.getGeneratedKeys()) {
        if (generatedKeys.next()) {
          newId = generatedKeys.getInt(1);
        } else {
          throw new SQLException("Creating user failed, no ID obtained.");
        }
        return newId;
      }

    } catch (SQLException e) {
      LOGGER.error("Unable to create object", e);
    }

    return OBJECT_ADD_ABORTED;
  }

  @Override
  public P read(K id) {

    P object = null;

    String selectByIdQuery = getSelectByIdQuery();

    try (Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement(selectByIdQuery)) {

      setIdStatementParameter(statement, id);

      ResultSet resultSet = statement.executeQuery();
      if (resultSet.next()) {
        object = readObject(resultSet);
      }
      resultSet.close();

    } catch (SQLException e) {
      LOGGER.error("Unable to read object", e);
    }
    return object;
  }

  @Override
  public boolean update(P object) {

    String updateQuery = getUpdateQuery();

    try (Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement(updateQuery)) {

      setObjectStatement(statement, object);

      if (statement.executeUpdate() < STATEMENT_EXECUTED_SUCCESSFULLY) {
        return false;
      }
    } catch (SQLException e) {
      LOGGER.error("Unable to update object", e);
    }

    return true;
  }

  @Override
  public boolean delete(K id) {
    String deleteQuery = getDeleteQuery();

    try (Connection connection = createConnection();
        PreparedStatement statement = connection.prepareStatement(deleteQuery)) {

      setIdStatementParameter(statement, id);
      if (statement.executeUpdate() < STATEMENT_EXECUTED_SUCCESSFULLY) {
        return false;
      }

    } catch (SQLException e) {
      LOGGER.error("Unable to delete object", e);
    }
    return true;
  }
}
