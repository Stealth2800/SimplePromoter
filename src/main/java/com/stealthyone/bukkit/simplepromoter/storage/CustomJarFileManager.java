package com.stealthyone.bukkit.simplepromoter.storage;

import java.io.InputStream;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomJarFileManager {

	private JavaPlugin plugin;
	
	private String fileName;
	private FileConfiguration customConfig;
	
	public CustomJarFileManager(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		this.fileName = fileName;
	}
	
	public final void reloadFile() {
		InputStream defStream = plugin.getResource(fileName + ".yml");
		if (defStream != null) {
			customConfig = YamlConfiguration.loadConfiguration(defStream);
		}
	}
	
	public final FileConfiguration getConfig() {
		if (customConfig == null) {
			this.reloadFile();
		}
		return customConfig;
	}
	
}
