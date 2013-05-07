package com.stealthyone.bukkit.simplepromoter.utils;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;
import com.stealthyone.bukkit.simplepromoter.SimplePromoter.PluginLogger;
import com.stealthyone.bukkit.simplepromoter.config.ConfigHelper;

public final class UpdateCheckRunnable implements Runnable {
		
	SimplePromoter plugin;
	
	public UpdateCheckRunnable(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public void run() {
		if ((boolean) ConfigHelper.CHECK_FOR_UPDATES.get()) {
			if (StringUtils.containsMultiple(plugin.getVersion(), "SNAPSHOT", "BETA", "ALPHA", "b")) {
				PluginLogger.info("Currently running a snapshot, beta, or alpha build. Update check cancelled.");
				return;
			}
		
			PluginLogger.info("Checking for updates...");
			
			UpdateChecker updateChecker = new UpdateChecker(plugin, "http://dev.bukkit.org/server-mods/simplepromoter/files.rss");
			
			if (updateChecker.updateNeeded()) {
				PluginLogger.info("Found a different version on BukkitDev! (New: " + updateChecker.getVersion() + " | Current: " + plugin.getVersion() + ")");
				PluginLogger.info("You can download it from: " + updateChecker.getLink());
			}
		} else {
			//Update checker disable, alert console
			PluginLogger.info("Update checker is disabled, enable in config for auto update checking");
			PluginLogger.info("You can also check for updates by typing /simplepromoter version");
		}
	}
	
}