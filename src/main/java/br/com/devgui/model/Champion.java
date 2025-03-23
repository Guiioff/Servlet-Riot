package br.com.devgui.model;

public class Champion {
	
	private String championId;
	private String championName;
	private String title;
	private String masteryPoints;
	
	public Champion() {}

	public Champion(String championId, String masteryPoints) {
		super();
		this.championId = championId;
		this.masteryPoints = masteryPoints;
	}

	public String getChampionId() {
		return championId;
	}

	public void setChampionId(String championId) {
		this.championId = championId;
	}

	public String getChampionName() {
		return championName;
	}

	public void setChampionName(String championName) {
		this.championName = championName;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getMasteryPoints() {
		return masteryPoints;
	}

	public void setMasteryPoints(String masteryPoints) {
		this.masteryPoints = masteryPoints;
	}

	@Override
	public String toString() {
		return "Champion [championId=" + championId + ", championName=" + championName + ", title=" + title
				+ ", masteryPoints=" + masteryPoints + "]";
	}
	
}
