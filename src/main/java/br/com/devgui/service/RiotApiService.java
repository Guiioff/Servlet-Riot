package br.com.devgui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

import br.com.devgui.model.Player;

public class RiotApiService {
	
	private final String URL_ACCOUNT = "https://americas.api.riotgames.com/riot/account/v1/accounts/by-riot-id/";
	private final String URL_CHAMPION_MASTERY = "https://br1.api.riotgames.com/lol/champion-mastery/v4/champion-masteries/by-puuid/";
	
	private final String API_KEY = "RGAPI-381b0028-06f7-4653-a427-28471abac35c";
	
	private final Gson gson = new Gson();
	private final HttpClient client = HttpClient.newHttpClient();
	
	private HttpRequest createRequest(String url) {
		return HttpRequest.newBuilder()
				.uri(URI.create(url))
				.GET()
				.build();
	}
	
	private HttpResponse<String> sendRequest(HttpRequest request) throws IOException, InterruptedException {
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
		} catch(InterruptedException e) {
			throw new RuntimeException(e);
		}
	}
}
