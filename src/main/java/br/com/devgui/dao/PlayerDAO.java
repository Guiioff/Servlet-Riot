package br.com.devgui.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Optional;
import br.com.devgui.config.DataBaseConnection;
import br.com.devgui.model.Player;

public class PlayerDAO {

  private final Connection connection;

  public PlayerDAO() {
    this.connection = new DataBaseConnection().openConnection();
  }

  public Optional<Player> findByGameNameAndTagLine(String gameName, String tagLine) {
    String sql = "SELECT * FROM player WHERE game_name = ? AND tag_line = ?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, gameName);
      stmt.setString(2, tagLine.toUpperCase());
      ResultSet rs = stmt.executeQuery();

      if (rs.next()) {
        Player player = new Player();
        player.setGameName(rs.getString("game_name"));
        player.setTagLine(rs.getString("tag_line"));
        player.setPuuid(rs.getString("puuid"));
        return Optional.of(player);
      }
    } catch (SQLException e) {
      e.printStackTrace();
    }
    return Optional.empty();
  }

  public void save(Player player) {
    String sql = "INSERT INTO player (game_name, tag_line, puuid) VALUES (?, ?, ?)";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, player.getGameName());
      stmt.setString(2, player.getTagLine());
      stmt.setString(3, player.getPuuid());
      stmt.executeUpdate();
    } catch (SQLException e) {
      e.printStackTrace();
    }
  }
}
