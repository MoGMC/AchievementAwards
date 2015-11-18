package com.fawkes.plugin.collectables.achievementawards;

import java.sql.SQLException;
import java.util.Random;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import com.fawkes.plugin.collectables.AwardGiveEvent;
import com.fawkes.plugin.collectables.CollectablesPlugin;
import com.fawkes.plugin.collectables.QueryAward;

public class AchievementAwardsPlugin extends JavaPlugin implements Listener {

	CollectablesPlugin plugin;
	Random rand = new Random();

	@Override
	public void onEnable() {

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

			if (count < 3) {
				return;

			}

			if (!plugin.hasAward(uuid, "3awardaward")) {
				plugin.giveAward(uuid, new QueryAward("3awardaward", System.currentTimeMillis(), getRandLevel()),
						false);
				Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(),
						"economy give " + Bukkit.getOfflinePlayer(uuid).getName() + " 300");

			}

			if (count < 5) {
				return;

			}

			if (!plugin.hasAward(uuid, "5awardaward")) {
				plugin.giveAward(uuid, new QueryAward("5awardaward", System.currentTimeMillis(), getRandLevel()),
						false);
				Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(),
						"economy give " + Bukkit.getOfflinePlayer(uuid).getName() + " 1000");

			}

			if (count < 10) {
				return;

			}

			if (!plugin.hasAward(uuid, "10awardaward")) {
				plugin.giveAward(uuid, new QueryAward("10awardaward", System.currentTimeMillis(), getRandLevel()),
						false);
				Bukkit.getServer().dispatchCommand(getServer().getConsoleSender(),
						"economy give " + Bukkit.getOfflinePlayer(uuid).getName() + " 3000");

			}

		} catch (SQLException sql) {
			Bukkit.getLogger().severe("Could not get plugin count or of the sort.");
			sql.printStackTrace();
			return;

		}

	}

	public int getRandLevel() {
		return rand.nextInt(100) + 1;
	}

}
