package com.stealthyone.mcb.simplepromoter.messages;

import com.stealthyone.mcb.simplepromoter.SimplePromoter;
import com.stealthyone.mcb.stbukkitlib.lib.messages.HelpManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.List;

public enum HelpMessage {

	SIMPLEPROMOTER_HELP("simplepromoter.help.self");
	
	private String path;
	
	private HelpMessage() {
		this.path = this.toString().toLowerCase().replace("_", ".");
	}
	
	private HelpMessage(String path) {
		this.path = path;
	}
	
	public final void sendTo(CommandSender sender, String label) {
		sendTo(sender, label, 1);
	}
	
	public final void sendTo(CommandSender sender, String label, int page) {
		HelpManager helpManager = SimplePromoter.getInstance().getHelpHandler();
		List<String> messages = helpManager.getMessages(this.path);
		
		sender.sendMessage(ChatColor.DARK_GRAY + "----" + ChatColor.GOLD + "Help: " + ChatColor.GREEN + "/" + toString().toLowerCase().replace("_", " ") + ChatColor.GOLD + " page " + page + ChatColor.DARK_GRAY + "----");
		
		try {
			for (int i = 0; i < 8; i++) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', messages.get(i + ((page - 1) * 8))));
			}
			
			if ((page - 1) * 8 < messages.size()) {
				sender.sendMessage(String.format(ChatColor.GREEN + "Type " + ChatColor.GOLD + "/%s %d" + ChatColor.GREEN + " for the next page.", toString().toLowerCase().replace("_", " "), page + 1));
			}
		} catch (NullPointerException | IndexOutOfBoundsException e) {}
	}
}