package br.com.devgui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

import com.google.gson.Gson;

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
}
