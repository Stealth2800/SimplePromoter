package com.stealthyone.mcb.simplepromoter.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum UsageMessage {

	CHECKRANK("/checkrank <player>"),
	SETRANK("/setrank <player> <rank> [rank]... [yes|no]");
	
	private String message;
	
	private UsageMessage(String message) {
		this.message = message;
	}
	
	public final void sendTo(CommandSender sender) {
		sender.sendMessage(ChatColor.DARK_RED + "USAGE: " + ChatColor.RED + message);
	}
}