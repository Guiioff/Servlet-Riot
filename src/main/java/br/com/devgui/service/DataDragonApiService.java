package br.com.devgui.service;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class DataDragonApiService {

	private final String URL_DATA_CHAMPION = "https://ddragon.leagueoflegends.com/cdn/15.2.1/data/pt_BR/champion.json";
	
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
