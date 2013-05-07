package com.stealthyone.bukkit.simplepromoter.messages;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

import com.stealthyone.bukkit.simplepromoter.storage.CustomJarFileManager;

public class HelpManager {

	private JavaPlugin plugin;
	
	private CustomJarFileManager helpFile;
	
	public HelpManager(JavaPlugin plugin, String fileName) {
		this.plugin = plugin;
		helpFile = new CustomJarFileManager(plugin, fileName);
		helpFile.reloadFile();
	}
	
	public final CustomJarFileManager getHelpFile() {
		return helpFile;
	}
	
	public final List<String> getMessages(String path) {
		List<String> returnList = getHelpFile().getConfig().getStringList("help." + path);
		return returnList;
	}
	
	public final void showHelp(CommandSender sender, String label, String command) {
		showHelp(sender, label, command, 1);
	}
	
	public final void showHelp(CommandSender sender, String label, String command, int page) {
		List<String> messages = getMessages(command);
		
		sender.sendMessage(ChatColor.DARK_GRAY + "----" + ChatColor.GOLD + "Help: " + ChatColor.GREEN + "/" + toString().toLowerCase().replace("_", " ") + ChatColor.GOLD + " page " + page + ChatColor.DARK_GRAY + "----");
		
		try {
			for (int i = 0; i < 8; i++) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(i + ((page - 1) * 8))));
			}
			
			if ((page - 1) * 8 < messages.size()) {
				sender.sendMessage(String.format(ChatColor.GREEN + "Type " + ChatColor.GOLD + "/%s help %d" + ChatColor.GREEN + " for the next page.", label, page + 1));
			}
		} catch (NullPointerException | IndexOutOfBoundsException e) {}
	}

}