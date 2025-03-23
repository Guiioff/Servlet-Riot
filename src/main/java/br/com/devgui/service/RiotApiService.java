package br.com.devgui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.ArrayList;
import java.util.List;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import br.com.devgui.model.Champion;
import br.com.devgui.model.Player;

public class RiotApiService {

  private final String URL_ACCOUNT =
      "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/";
  private final String URL_CHAMPION_MASTERY =
      "https://br1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/";

  private final String API_KEY = "RGAPI-381b0028-06f7-4653-a427-28471abac35c";

  private final Gson gson = new Gson();
  private final HttpClient client = HttpClient.newHttpClient();

  private HttpRequest createRequest(String url) {
    return HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
  }

  private HttpResponse<String> sendRequest(HttpRequest request)
      throws IOException, InterruptedException {
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  public Player getPlayer(String gameName, String tagLine) {
    try {
      String urlWithParams = URL_ACCOUNT + gameName + "/" + tagLine + "?api_key=" + API_KEY;
      HttpRequest request = createRequest(urlWithParams);
      HttpResponse<String> response = sendRequest(request);

      if (response.statusCode() == 400) {
        throw new RuntimeException("Player not found.");
      }

      return gson.fromJson(response.body(), Player.class);

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }

  public List<Champion> getChampionMasteryId(String puuid) {
    try {
      String urlWithParams = URL_CHAMPION_MASTERY + puuid + "/top?count=3&api_key=" + API_KEY;
      HttpRequest request = createRequest(urlWithParams);
      HttpResponse<String> response = sendRequest(request);

      if (response.statusCode() == 400) {
        throw new RuntimeException("PUUID is wrong.");
      }

      String responseBody = response.body();
      JsonArray jsonArray = JsonParser.parseString(responseBody).getAsJsonArray();

      List<Champion> champions = new ArrayList<>();
      for (JsonElement element : jsonArray) {
        JsonObject jsonObject = element.getAsJsonObject();

        String championId = jsonObject.get("championId").getAsString();
        String masteryPoints = jsonObject.get("championPoints").getAsString();

        champions.add(new Champion(championId, masteryPoints));
      }
      return champions;

    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (InterruptedException e) {
      throw new RuntimeException(e);
    }
  }
}
