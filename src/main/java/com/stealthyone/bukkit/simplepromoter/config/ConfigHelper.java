package com.stealthyone.bukkit.simplepromoter.config;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;

public enum ConfigHelper {

	DEBUG("Debug"),
	CHECK_FOR_UPDATES("Check for updates"),
	ENABLE_PROMOTION_SOUND("Enable sound on promotion");
	
	private String key;
	
	private ConfigHelper(String key) {
		this.key = key;
	}
	
	public final Object get() {
		return SimplePromoter.getInstance().getConfig().get(key);
	}
	
	public final void set(Object newValue) {
		SimplePromoter.getInstance().getConfig().set(key, newValue);
	}
}