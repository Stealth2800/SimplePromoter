package com.stealthyone.mcb.simplepromoter;

import com.stealthyone.mcb.simplepromoter.commands.CmdCheckrank;
import com.stealthyone.mcb.simplepromoter.commands.CmdSetrank;
import com.stealthyone.mcb.simplepromoter.commands.CmdSimplePromoter;
import com.stealthyone.mcb.simplepromoter.config.ConfigHelper;
import com.stealthyone.mcb.stbukkitlib.lib.messages.HelpManager;
import com.stealthyone.mcb.stbukkitlib.lib.messages.MessageRetriever;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.logging.Level;
import java.util.logging.Logger;

public final class SimplePromoter extends JavaPlugin {
	
	public final static class Log {
		
		public final static void debug(String message) {
			if (ConfigHelper.DEBUG.getBoolean())
				instance.logger.log(Level.INFO, String.format("[%s DEBUG] %s", instance.getName(), message));
		}
		
		public final static void info(String message) {
			instance.logger.log(Level.INFO, String.format("[%s] %s", instance.getName(), message));
		}
		
		public final static void warning(String message) {
			instance.logger.log(Level.WARNING, String.format("[%s] %s", instance.getName(), message));
		}
		
		public final static void severe(String message) {
			instance.logger.log(Level.SEVERE, String.format("[%s] %s", instance.getName(), message));
		}

	}
	
	private static SimplePromoter instance;
	{
		instance = this;
	}
	
	public final static SimplePromoter getInstance() {
		return instance;
	}
	
	public final static String UPDATE_URL = "http://dev.bukkit.org/server-mods/simplepromoter/files.rss";
	
	private Logger logger;
	
	private HelpManager helpHandler;
	private MessageRetriever messageHandler;
	
	@Override
	public final void onLoad() {
		logger = getServer().getLogger();
		if (!getDataFolder().exists())
			getDataFolder().mkdir();
	}
	
	@Override
	public final void onEnable() {
		/* Setup config */
		saveDefaultConfig();
		getConfig().options().copyDefaults(true);
		saveConfig();
		
		/* Setup important plugin pieces */
		helpHandler =  new HelpManager(this);
		messageHandler = new MessageRetriever(this);
		
		/* Register commands */
		getCommand("checkrank").setExecutor(new CmdCheckrank(this));
		getCommand("setrank").setExecutor(new CmdSetrank(this));
		getCommand("simplepromoter").setExecutor(new CmdSimplePromoter(this));
		
		getServer().getScheduler().runTaskTimerAsynchronously(this, new UpdateCheckRunnable(this), 40, 432000);
		Log.info(String.format("%s v%s by Stealth2800 enabled!", getName(), getVersion()));
	}
	
	@Override
	public final void onDisable() {
		Log.info(String.format("%s v%s by Stealth2800 disabled!", getName(), getVersion()));
	}
	
	public final String getVersion() {
		return getDescription().getVersion();
	}
	
	public final HelpManager getHelpHandler() {
		return this.helpHandler;
	}
	
	public final MessageRetriever getMessageHandler() {
		return this.messageHandler;
	}

}