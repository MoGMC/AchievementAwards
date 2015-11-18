package com.fawkes.plugin.collectables.achievementawards;

public class Award {

	// to store stuff from config and loop thru it
	String awardId;
	int money;

	public Award(String awardId, int money) {
		this.awardId = awardId;
		this.money = money;

	}

	public String getId() {
		return awardId;

	}

	public int getMoney() {
		return money;

	}

}
