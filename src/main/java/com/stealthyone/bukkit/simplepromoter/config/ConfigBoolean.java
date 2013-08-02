package com.stealthyone.bukkit.simplepromoter.config;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;

public enum ConfigBoolean {

	DEBUG("Debug"),
	CHECK_FOR_UPDATES("Check for updates"),
	ENABLE_PROMOTION_SOUND("Enable sound on promotion");
	
	private String path;
	
	private ConfigBoolean(String path) {
		this.path = path;
	}
	
	public final boolean get() {
		return SimplePromoter.getInstance().getConfig().getBoolean(path);
	}
	
}