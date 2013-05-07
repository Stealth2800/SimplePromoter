package com.stealthyone.bukkit.simplepromoter.messages;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import com.stealthyone.bukkit.simplepromoter.storage.CustomFileManager;
import com.stealthyone.bukkit.simplepromoter.utils.FileUtils;

public final class MessageRetriever {

	private JavaPlugin plugin;
	private CustomFileManager messageFile;
	
	public MessageRetriever(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		messageFile = new CustomFileManager(plugin, fileName);
		if (!messageFile.getFile().exists()) {
			FileUtils.copyFileFromJar(plugin, fileName + ".yml");
			messageFile.reloadConfig();
		} else {
			messageFile.copyDefaults(YamlConfiguration.loadConfiguration(plugin.getResource(fileName + ".yml"))); 
		}
	}
	
	public final void reloadMessages() {
		messageFile.reloadConfig();
	}
	
	public final String[] getMessage(IMessagePath message) {
		String[] returnString;
		String path = message.getPrefix() + message.getMessagePath();
		
		if (message.isList()) {
			returnString = (String[]) messageFile.getConfig().getStringList(path).toArray();
		} else {
			returnString = new String[]{messageFile.getConfig().getString(path)};
		}
		
		return returnString;
	}
}