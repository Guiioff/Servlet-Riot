package br.com.devgui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;
import java.util.Map;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import br.com.devgui.exception.DatadragonException;
import br.com.devgui.model.Champion;

public class DataDragonApiService {

  private final String URL_DATA_CHAMPION =
      "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/pt_BR/champion.json";

  private final HttpClient client = HttpClient.newHttpClient();

  private HttpRequest createRequest(String url) {
    return HttpRequest.newBuilder().uri(URI.create(url)).GET().build();
  }

  private HttpResponse<String> sendRequest(HttpRequest request)
      throws IOException, InterruptedException {
    return client.send(request, HttpResponse.BodyHandlers.ofString());
  }

  public List<Champion> getChampionDetails(List<Champion> champions) {
    try {
      HttpRequest request = createRequest(URL_DATA_CHAMPION);
      HttpResponse<String> response = sendRequest(request);

      if (response.statusCode() != 200) {
        throw new DatadragonException(
            "Error connecting to DataDragon API. Status code: " + response.statusCode());
      }

      List<String> championsIds = champions.stream().map(c -> c.getChampionId()).toList();

      String responseBody = response.body();
      JsonObject jsonObject = JsonParser.parseString(responseBody).getAsJsonObject();

      JsonObject data = jsonObject.getAsJsonObject("data");

      for (Map.Entry<String, JsonElement> entry : data.entrySet()) {
        JsonObject championJson = entry.getValue().getAsJsonObject();
        String key = championJson.get("key").getAsString();

        if (championsIds.contains(key)) {
          String name = championJson.get("name").getAsString();
          String title = championJson.get("title").getAsString();

          champions.stream().filter(c -> c.getChampionId().equals(key)).forEach(c -> {
            c.setChampionName(name);
            c.setTitle(title);
          });
        }
      }
      return champions;

    } catch (IOException e) {
      throw new DatadragonException("Failed to communicate with the DataDragon API.", e);

    } catch (InterruptedException e) {
      Thread.currentThread().interrupt();
      throw new DatadragonException("The request to DataDragon API was interrupted.", e);
    }
  }



}
