package br.com.devgui.service;

import java.util.Optional;
import br.com.devgui.dao.PlayerDAO;
import br.com.devgui.model.Player;

public class PlayerService {

  private final PlayerDAO playerDAO = new PlayerDAO();

  public Optional<Player> getPlayer(String gameName, String tagLIne) {
    return playerDAO.findByGameNameAndTagLine(gameName, tagLIne);
  }

  public void savePlayer(Player player) {
    playerDAO.save(player);
  }


}
