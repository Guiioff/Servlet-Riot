package br.com.devgui.model;

public class Player {
	
	private String gameName;
	private String tagLine;
	private String puuid;
	
	public String getGameName() {
		return gameName;
	}
	
	public void setGameName(String gameName) {
		this.gameName = gameName;
	}
	
	public String getTagLine() {
		return tagLine;
	}
	
	public void setTagLine(String tagLine) {
		this.tagLine = tagLine;
	}
	
	public String getPuuid() {
		return puuid;
	}
	
	public void setPuuid(String puuid) {
		this.puuid = puuid;
	}

	@Override
	public String toString() {
		return "Player [gameName=" + gameName + ", tagLine=" + tagLine + ", puuid=" + puuid + "]";
	}

}
