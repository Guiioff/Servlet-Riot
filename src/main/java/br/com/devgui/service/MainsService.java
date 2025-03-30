package br.com.devgui.service;

import java.util.List;
import java.util.Optional;
import br.com.devgui.model.Champion;
import br.com.devgui.model.Player;

public class MainsService {

  private final PlayerService playerService = new PlayerService();
  private final ChampionService championService = new ChampionService();
  private final RiotApiService riotApiService = new RiotApiService();
  private final DataDragonApiService dataDragonApiService = new DataDragonApiService();

  public Player getOrFetchPlayer(String gameName, String tagLine) {
    Optional<Player> playerOptional = playerService.getPlayer(gameName, tagLine);
    if (playerOptional.isPresent()) {
      return playerOptional.get();
    }

    Player player = riotApiService.getPlayer(gameName, tagLine);
    playerService.savePlayer(player);
    return player;
  }

  public List<Champion> getOrFetchChampions(Player player) {
    List<Champion> champions = championService.getChampionsByPlayerId(player.getPuuid());

    if (champions.isEmpty()) {
      champions = riotApiService.getChampionMasteryId(player.getPuuid());
      List<Champion> championsCompleted = dataDragonApiService.getChampionDetails(champions);

      for (Champion champion : championsCompleted) {
        championService.saveChampion(champion);
        championService.savePlayerChampions(player.getPuuid(), champion.getChampionId(),
            champion.getMasteryPoints());
      }

      return championsCompleted;
    }
    return champions;
  }

}
