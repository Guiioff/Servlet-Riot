package br.com.devgui.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import br.com.devgui.config.DataBaseConnection;
import br.com.devgui.model.Champion;

public class ChampionDAO {

  private final Connection connection;

  public ChampionDAO() {
    this.connection = new DataBaseConnection().openConnection();
  }

  public List<Champion> findByPlayerId(String puuid) {
    List<Champion> champions = new ArrayList<>();

    String sql = "SELECT c.champion_id, c.champion_name, c.title, pc.mastery_points"
        + " FROM player_champion pc" + " JOIN champion c ON pc.champion_id = c.champion_id"
        + " WHERE pc.player_id=?";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, puuid);
      ResultSet rs = stmt.executeQuery();

      while (rs.next()) {
        Champion champion = new Champion();
        champion.setChampionId(rs.getString("champion_id"));
        champion.setChampionName(rs.getString("champion_name"));
        champion.setTitle(rs.getString("title"));
        champion.setMasteryPoints(rs.getString("mastery_points"));
        champions.add(champion);
      }

    } catch (SQLException e) {
      e.printStackTrace();
    }

    return champions;
  }

  public void save(Champion champion) {
    String sql = "INSERT INTO champion (champion_id, champion_name, title)" + " VALUES (?, ?, ?)"
        + " ON CONFLICT (champion_id) DO NOTHING";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, champion.getChampionId());
      stmt.setString(2, champion.getChampionName());
      stmt.setString(3, champion.getTitle());
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

  public void savePlayerChampions(String playerId, String championId, String masteryPoints) {
    String sql = "INSERT INTO player_champion (player_id, champion_id, mastery_points)"
        + " VALUES (?, ?, ?)" + " ON CONFLICT (player_id, champion_id) DO NOTHING";

    try (PreparedStatement stmt = connection.prepareStatement(sql)) {
      stmt.setString(1, playerId);
      stmt.setString(2, championId);
      stmt.setString(3, masteryPoints);
      stmt.executeUpdate();

    } catch (SQLException e) {
      e.printStackTrace();
    }
  }

}
