package com.stealthyone.bukkit.simplepromoter;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.bukkit.plugin.java.JavaPlugin;

import com.stealthyone.bukkit.simplepromoter.commands.CmdCheckrank;
import com.stealthyone.bukkit.simplepromoter.commands.CmdSetrank;
import com.stealthyone.bukkit.simplepromoter.commands.CmdSimplePromoter;
import com.stealthyone.bukkit.simplepromoter.config.ConfigHelper;
import com.stealthyone.bukkit.stcommonlib.messages.HelpManager;
import com.stealthyone.bukkit.stcommonlib.messages.MessageRetriever;

public final class SimplePromoter extends JavaPlugin {

	private static SimplePromoter instance;
	{
		instance = this;
	}
	
	public final static SimplePromoter getInstance() {
		return instance;
	}
	
	public final static class PluginLogger {
		
		public final static void debug(String message) {
			if (SimplePromoter.getInstance().isDebug()) {
				SimplePromoter.getInstance().log.log(Level.INFO, String.format("[%s DEBUG] %s", SimplePromoter.getInstance().getName(), message));
			}
		}
		
		public final static void info(String message) {
			SimplePromoter.getInstance().log.log(Level.INFO, String.format("[%s] %s", SimplePromoter.getInstance().getName(), message));
		}
		
		public final static void warning(String message) {
			SimplePromoter.getInstance().log.log(Level.WARNING, String.format("[%s] %s", SimplePromoter.getInstance().getName(), message));
		}
		
		public final static void severe(String message) {
			SimplePromoter.getInstance().log.log(Level.SEVERE, String.format("[%s] %s", SimplePromoter.getInstance().getName(), message));
		}
	}
	
	private Logger log;
	
	private MessageRetriever messageHandler;
	private HelpManager helpHandler;
	
	@Override
	public final void onLoad() {
		log = getServer().getLogger();
		if (!getDataFolder().exists()) {
			getDataFolder().mkdir();
		}
	}
	
	@Override
	public final void onEnable() {
		// Setup config //
		for (ConfigHelper obj : ConfigHelper.values()) {
			obj.addDefault();
		}
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		// Setup important plugin pieces //
		messageHandler = new MessageRetriever(this, "messages");
		helpHandler =  new HelpManager(this, "help");
		
		// Register commands //
		getCommand("checkrank").setExecutor(new CmdCheckrank(this));
		getCommand("setrank").setExecutor(new CmdSetrank(this));
		getCommand("simplepromoter").setExecutor(new CmdSimplePromoter(this));
		
		PluginLogger.info(String.format("%s v%s by Stealth2800 enabled!", getName(), getVersion()));
	}
	
	@Override
	public final void onDisable() {
		PluginLogger.info(String.format("%s v%s by Stealth2800 disabled!", getName(), getVersion()));
	}
	
	public final String getVersion() {
		return getDescription().getVersion();
	}
	
	public final boolean isDebug() {
		return (boolean) ConfigHelper.DEBUG.get();
	}
	
	public final MessageRetriever getMessageHandler() {
		return this.messageHandler;
	}
	
	public final HelpManager getHelpHandler() {
		return this.helpHandler;
	}
}