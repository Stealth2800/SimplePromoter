package com.stealthyone.mcb.simplepromoter.commands;

import com.stealthyone.mcb.simplepromoter.SimplePromoter;
import com.stealthyone.mcb.simplepromoter.UpdateChecker;
import com.stealthyone.mcb.simplepromoter.messages.ErrorMessage;
import com.stealthyone.mcb.simplepromoter.messages.HelpMessage;
import com.stealthyone.mcb.simplepromoter.messages.NoticeMessage;
import com.stealthyone.mcb.simplepromoter.permissions.PermissionNode;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public final class CmdSimplePromoter implements CommandExecutor {

	private SimplePromoter plugin;
	
	public CmdSimplePromoter(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if (args.length == 0) {
			HelpMessage.SIMPLEPROMOTER_HELP.sendTo(sender, label, 1);
		} else {
			switch (args[0]) {
				/* Help command */
				case "help":
					cmdHelp(sender, command, label, args);
					return true;
				
				/* Reload command */
				case "reload":
					cmdReload(sender, command, label, args);
					return true;
					
				/* Version command */
				case "version":
					cmdVersion(sender, command, label, args);
					return true;
					
				/* Unknown command */
				default:
					ErrorMessage.UNKNOWN_COMMAND.sendTo(sender);
					break;
			}
		}
		
		HelpMessage.SIMPLEPROMOTER_HELP.sendTo(sender, label);
		return true;
	}
	
	/**
	 * Handler for help command
	 * @param sender
	 * @param command
	 * @param label
	 * @param args
	 */
	private final void cmdHelp(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionNode.HELP.isAllowed(sender)) {
			ErrorMessage.NO_PERMISSION.sendTo(sender);
		} else {
			int pageNum = 1;
			if (args.length > 1) {
				try {
					pageNum = Integer.parseInt(args[1]);
				} catch (NumberFormatException e) {}
			}
			HelpMessage.SIMPLEPROMOTER_HELP.sendTo(sender, label, pageNum);
		}
	}
	
	/**
	 * Handler for reload command
	 * @param sender
	 * @param command
	 * @param label
	 * @param args
	 */
	private final void cmdReload(CommandSender sender, Command command, String label, String[] args) {
		if (!PermissionNode.ADMIN_RELOAD.isAllowed(sender)) {
			ErrorMessage.NO_PERMISSION.sendTo(sender);
		} else {
			plugin.reloadConfig();
			plugin.getMessageHandler().reloadMessages();
			NoticeMessage.PLUGIN_RELOADED.sendTo(sender);
		}
	}
	
	/**
	 * Handler for version command
	 * @param sender
	 * @param command
	 * @param label
	 * @param args
	 */
	private final void cmdVersion(CommandSender sender, Command command, String label, String[] args) {
		sender.sendMessage(ChatColor.GREEN + plugin.getName() + ChatColor.GOLD + " v" + plugin.getVersion());
		sender.sendMessage(ChatColor.GOLD + "Created by " + plugin.getDescription().getAuthors() + ChatColor.GRAY + "-" + ChatColor.AQUA + "http://stealthyone.com/bukkit");
		sender.sendMessage(ChatColor.GOLD + "BukkitDev: " + ChatColor.AQUA + plugin.getDescription().getWebsite());
		UpdateChecker updateChecker = new UpdateChecker(plugin);
		if (updateChecker.isUpdateNeeded()) {
			String curVer = plugin.getVersion();
			String remVer = updateChecker.getNewVersion();
			sender.sendMessage(ChatColor.RED + "A different version was found on BukkitDev! (Current: " + curVer + " | Remote: " + remVer + ")");
			sender.sendMessage(ChatColor.RED + "You can download it from " + updateChecker.getVersionLink());
		}
	}

}