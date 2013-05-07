package com.stealthyone.bukkit.simplepromoter.messages;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;

public enum ErrorMessage implements IMessagePath {
	
	GROUPS_DONT_EXIST,
	PLAYER_DOESNT_EXIST,
	NO_PERMISSION,
	NO_PERM_FOR_GROUPS;
	  
	private final String PREFIX = "messages.errors.";
	  
	private String path;
	private boolean isList;
	  
	private ErrorMessage() {
		this(false);
	}
	  
	private ErrorMessage(boolean isList) {
		this.path = this.toString().toLowerCase();
		this.isList = isList;
	}
	
	public final String[] getMessage() {
		return SimplePromoter.getInstance().getMessageHandler().getMessage(this);
	}
	
	@Override
	public final String getPrefix() {
		return PREFIX;
	}
	
	@Override
	public final String getMessagePath() {
		return this.path;
	}
	  
	@Override
	public final boolean isList() {
		return this.isList;
	}
	  
	public final void sendTo(CommandSender sender) {
		MessageRetriever messageRetriever = SimplePromoter.getInstance().getMessageHandler();
		String[] messages = messageRetriever.getMessage(this);
		
		for (String message : messages) {
			message = ChatColor.RED + message;
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{TAG}", ChatColor.GOLD + "[" + SimplePromoter.getInstance().getName() + "] " + ChatColor.RED)));
		}
	}
	  
	public final void sendTo(CommandSender sender, String... replacements) {
		MessageRetriever messageRetriever = SimplePromoter.getInstance().getMessageHandler();
		String[] messages = messageRetriever.getMessage(this);
		
		for (String message : messages) {
			message = ChatColor.RED + message;
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message.replace("{TAG}", ChatColor.GOLD + "[" + SimplePromoter.getInstance().getName() + "] " + ChatColor.RED), (Object[]) replacements)));
		}
	}
}