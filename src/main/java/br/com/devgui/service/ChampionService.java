package br.com.devgui.service;

import java.util.List;
import br.com.devgui.dao.ChampionDAO;
import br.com.devgui.model.Champion;

public class ChampionService {

  private final ChampionDAO championDAO = new ChampionDAO();

  public List<Champion> getChampionsByPlayerId(String puuid) {
    return championDAO.findByPlayerId(puuid);
  }

  public void saveChampion(Champion champion) {
    championDAO.save(champion);
  }

  public void savePlayerChampions(String playerId, String championId, String masteryPoints) {
    championDAO.savePlayerChampions(playerId, championId, masteryPoints);
  }

}
