package com.stealthyone.bukkit.simplepromoter.storage;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

public class CustomFileManager {

	private JavaPlugin plugin;
	
	private String fileName;
	private String path;
	private File customFile;
	private FileConfiguration customConfig;

	public CustomFileManager(JavaPlugin plugin, String fileName) {
		this(plugin, fileName, "");
	}

	public CustomFileManager(JavaPlugin plugin, String fileName, String path) {
		this.plugin = plugin;
		this.fileName = fileName;
		this.path = path;
		this.reloadConfig();
	}
	
	public final void copyDefaults(FileConfiguration otherConfig) {
		customConfig.addDefaults(otherConfig);
		customConfig.options().copyDefaults(true);
		saveFile();
	}
	
	/**
	 * Returns the file
	 * @return Custom file
	 */
	public final File getFile() {
		return this.customFile;
	}
	
	/**
	 * Reload file from disk
	 */
	public void reloadConfig() {
		if (customFile == null) {
			if (path.length() > 1) {
				customFile = new File(plugin.getDataFolder() + File.separator + path, fileName + ".yml");
			} else {
				customFile = new File(plugin.getDataFolder(), fileName + ".yml");
			}
			saveFile();
		}
		customConfig = YamlConfiguration.loadConfiguration(customFile);
	}

	/**
	 * Return the config from file
	 * @return
	 */
	public FileConfiguration getConfig() {
		if (customConfig == null) {
			this.reloadConfig();
		}
		return customConfig;
	}

	/**
	 * Save changes to file on disk
	 * @throws IOException 
	 */
	public void saveFile() {
        if (customConfig == null || customFile == null) {
        	this.reloadConfig();
        } else {
        	try {
				this.getConfig().save(customFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
        }
	}

}