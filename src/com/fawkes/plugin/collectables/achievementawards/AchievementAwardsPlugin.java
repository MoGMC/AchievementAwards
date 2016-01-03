package com.fawkes.plugin.collectables.achievementawards;

import java.sql.SQLException;
import java.util.HashMap;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.fawkes.plugin.collectables.AwardGiveEvent;
import com.fawkes.plugin.collectables.CollectablesPlugin;
import com.fawkes.plugin.collectables.QueryAward;

public class AchievementAwardsPlugin extends JavaPlugin implements Listener {

	CollectablesPlugin plugin;
	Random rand = new Random();

	HashMap<Integer, Award> awards;

	@Override
	public void onEnable() {

		saveDefaultConfig();

		initConfig();

		// loads the plugin for API usage
		plugin = this.getServer().getServicesManager().load(CollectablesPlugin.class);

		// registers listeners
		Bukkit.getPluginManager().registerEvents(this, this);

	}

	// TODO: make configurable

	@EventHandler
	public void onAwardGive(AwardGiveEvent e) {

		int count;
		UUID uuid = e.getUUID();

		try {

			// add one because this one hasn't been given yet

			count = plugin.getAwardCount(e.getUUID()) + 1;

			for (int number : awards.keySet()) {
				if (count < number) {
					return;

				}

				Award a = awards.get(number);

				if (!plugin.hasAward(uuid, a.getId())) {
					plugin.giveAward(uuid, new QueryAward(a.getId(), System.currentTimeMillis(), getRandLevel()),
							false, true);
					Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(),
							"economy give " + Bukkit.getOfflinePlayer(uuid).getName() + " " + a.getMoney());
				}

			}

		} catch (SQLException sql) {
			Bukkit.getLogger().severe("Could not get plugin count or of the sort.");
			sql.printStackTrace();
			return;

		}

	}

	private void initConfig() {

		awards = new HashMap<Integer, Award>();

		// reads config
		FileConfiguration config = getConfig();

		// loops thru all of the top level keys, gets values
		for (String s : config.getKeys(false)) {
			try {
				// converts key to int
				int number = Integer.valueOf(s);
				// converts money to int
				int money = Integer.valueOf(config.getString(s + ".money"));

				// gets award id of award
				String awardid = config.getString(s + ".awardid");

				// puts into map
				awards.put(number, new Award(awardid, money));

			} catch (NumberFormatException e) {
				// if the numbers screw up
				Bukkit.getLogger().severe("Error in AchievementAwards config: Cannot parse numbers.");
				continue;

			}

		}

	}

	public int getRandLevel() {
		return rand.nextInt(100) + 1;
	}

}
