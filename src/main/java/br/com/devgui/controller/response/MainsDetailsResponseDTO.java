package br.com.devgui.controller.response;

import java.util.List;

public record MainsDetailsResponseDTO(
		String gameName,
		String tagLine,
		List<ChampionResponseDTO> mainChampions) {

}
