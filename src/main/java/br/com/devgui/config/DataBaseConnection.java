package br.com.devgui.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DataBaseConnection implements AutoCloseable {

  private static final String URL = "jdbc:postgresql://localhost:5432/servlet-riot-1";
  private static final String USER = "postgres";
  private static final String PASSWORD = "prog123";
  private final Connection connection;

  public DataBaseConnection() {
    this.connection = this.openConnection();
  }


  public Connection openConnection() {
    try {

      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(URL, USER, PASSWORD);

    } catch (SQLException e) {
      System.err.println("Error connecting to the database: " + e.getMessage());
      return null;

    } catch (ClassNotFoundException e) {
      throw new RuntimeException(
          "PostgreSQL JDBC Driver not found. Make sure the driver JAR is in the classpath.");
    }
  }

  @Override
  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new RuntimeException("Error while closing connection.");
      }
    }
  }

  public Connection getConnection() {
    return this.connection;
  }

}
