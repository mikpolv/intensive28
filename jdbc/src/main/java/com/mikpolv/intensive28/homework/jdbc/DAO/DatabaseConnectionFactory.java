package com.mikpolv.intensive28.homework.jdbc.DAO;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class DatabaseConnectionFactory {

  private static String JDBC_POSTGRESQL_HOST;
  private static String DB_NAME;
  private static String USERNAME;
  private static String PASSWORD;
  private static String DB_DRIVER;

  private DatabaseConnectionFactory() {}

  static {
    try (InputStream input =
        DatabaseConnectionFactory.class.getClassLoader().getResourceAsStream("db.properties")) {
      Properties properties = new Properties();
      properties.load(input);

      JDBC_POSTGRESQL_HOST = properties.getProperty("JDBC_POSTGRESQL_HOST");
      DB_NAME = properties.getProperty("DB_NAME");
      USERNAME = properties.getProperty("USERNAME");
      PASSWORD = properties.getProperty("PASSWORD");
      DB_DRIVER = properties.getProperty("DB_DRIVER");
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  public static Connection getConnection() {
    try {
      Class.forName(DB_DRIVER);
    } catch (ClassNotFoundException e) {
      e.printStackTrace();
    }

    try {
      return DriverManager.getConnection(JDBC_POSTGRESQL_HOST + DB_NAME, USERNAME, PASSWORD);
    } catch (SQLException e) {
      throw new RuntimeException(e);
    }
  }
}
