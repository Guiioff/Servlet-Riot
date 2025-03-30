package br.com.devgui.config;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import br.com.devgui.exception.DatabaseException;
import io.github.cdimascio.dotenv.Dotenv;

public class DataBaseConnection implements AutoCloseable {

  private final Dotenv dotenv = Dotenv.load();

  private final String URL = dotenv.get("URL_DB");
  private final String USER = dotenv.get("USER_DB");
  private final String PASSWORD = dotenv.get("PASSWORD_DB");
  private final Connection connection;

  public DataBaseConnection() {
    this.connection = this.openConnection();
  }


  public Connection openConnection() {
    try {

      Class.forName("org.postgresql.Driver");
      return DriverManager.getConnection(URL, USER, PASSWORD);

    } catch (SQLException e) {
      throw new DatabaseException("Error connecting to database.", e);

    } catch (ClassNotFoundException e) {
      throw new DatabaseException("PostgreSQL Driver not found.", e);
    }
  }

  @Override
  public void close() {
    if (connection != null) {
      try {
        connection.close();
      } catch (SQLException e) {
        throw new DatabaseException("Error while closing connection.", e);
      }
    }
  }

  public Connection getConnection() {
    return this.connection;
  }

}
