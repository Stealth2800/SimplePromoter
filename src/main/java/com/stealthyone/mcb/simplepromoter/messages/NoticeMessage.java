package com.stealthyone.mcb.simplepromoter.messages;

import com.stealthyone.mcb.simplepromoter.SimplePromoter;
import com.stealthyone.mcb.stbukkitlib.lib.messages.IMessagePath;
import com.stealthyone.mcb.stbukkitlib.lib.messages.MessageRetriever;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public enum NoticeMessage implements IMessagePath {
	
	PLAYER_PART_OF_GROUPS,
	PLUGIN_RELOADED,
	PROMOTION_MESSAGE,
	PRIVATE_PROMOTION_MESSAGE,
	YOU_PROMOTED;
	  
	private final String PREFIX = "messages.notices.";
	  
	private String path;
	private boolean isList;
	  
	private NoticeMessage() {
		this(false);
	}
	  
	private NoticeMessage(boolean isList) {
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
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message.replace("{TAG}", ChatColor.GOLD + "[" + SimplePromoter.getInstance().getName() + "] ")));
		}
	}
	  
	public final void sendTo(CommandSender sender, String... replacements) {
		MessageRetriever messageRetriever = SimplePromoter.getInstance().getMessageHandler();
		String[] messages = messageRetriever.getMessage(this);
		
		for (String message : messages) {
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message.replace("{TAG}", ChatColor.GOLD + "[" + SimplePromoter.getInstance().getName() + "] "), (Object[]) replacements)));
		}
	}
}