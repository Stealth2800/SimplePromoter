package com.stealthyone.bukkit.simplepromoter.config;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;

public enum ConfigHelper {

	DEBUG("Debug", false);
	
	private String key;
	private Object value;
	
	private ConfigHelper(String key, Object value) {
		this.key = key;
		this.value = value;
	}
	
	public final Object get() {
		return SimplePromoter.getInstance().getConfig().get(key);
	}
	
	public final Object getDefaultValue() {
		return this.value;
	}
	
	public final void set(Object newValue) {
		SimplePromoter.getInstance().getConfig().set(key, newValue);
	}
	
	public final void addDefault() {
		SimplePromoter.getInstance().getConfig().addDefault(key, value);
	}
}