package br.com.devgui.controller;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import com.google.gson.Gson;
import br.com.devgui.controller.response.ChampionResponseDTO;
import br.com.devgui.controller.response.MainsDetailsResponseDTO;
import br.com.devgui.model.Champion;
import br.com.devgui.model.Player;
import br.com.devgui.service.DataDragonApiService;
import br.com.devgui.service.RiotApiService;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@WebServlet("/get-mains/*")
public class GetMainsServlet extends HttpServlet {

  private static final long serialVersionUID = 1L;
  private final RiotApiService riotApiService = new RiotApiService();
  private final DataDragonApiService dataDragonApiService = new DataDragonApiService();
  private final Gson gson = new Gson();

  @Override
  protected void doGet(HttpServletRequest req, HttpServletResponse resp)
      throws ServletException, IOException {
    Optional<String[]> params = getParams(req.getPathInfo());

    if (params.isEmpty()) {
      throw new RuntimeException("ta vazio os params.");
    }

    String gameName = params.get()[0];
    String tagLine = params.get()[1];

    Player player = riotApiService.getPlayer(gameName, tagLine);
    List<Champion> champions = riotApiService.getChampionMasteryId(player.getPuuid());

    List<Champion> championsCompleted = dataDragonApiService.getChampionDetails(champions);

    List<ChampionResponseDTO> championsDTO = championsCompleted.stream()
        .map(c -> new ChampionResponseDTO(c.getChampionName(), c.getTitle(), c.getMasteryPoints()))
        .toList();

    MainsDetailsResponseDTO detailsResponseDTO =
        new MainsDetailsResponseDTO(player.getGameName(), player.getTagLine(), championsDTO);

    String responseJson = gson.toJson(detailsResponseDTO);
    resp.getWriter().append(responseJson);
  }

  private static Optional<String[]> getParams(String pathInfo) {
    if (pathInfo != null) {
      String[] params = pathInfo.substring(1).split("/");

      if (params.length == 2) {
        return Optional.of(params);
      }
      return null;
    }
    return null;
  }
}
