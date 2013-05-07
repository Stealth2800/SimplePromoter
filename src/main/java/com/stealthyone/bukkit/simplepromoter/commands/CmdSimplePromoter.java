package com.stealthyone.bukkit.simplepromoter.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;
import com.stealthyone.bukkit.simplepromoter.messages.ErrorMessage;
import com.stealthyone.bukkit.simplepromoter.messages.HelpMessage;
import com.stealthyone.bukkit.simplepromoter.messages.NoticeMessage;
import com.stealthyone.bukkit.simplepromoter.utils.UpdateChecker;

public final class CmdSimplePromoter implements CommandExecutor {

	private SimplePromoter plugin;
	
	public CmdSimplePromoter(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length == 0) {
			HelpMessage.SIMPLEPROMOTER_HELP.sendTo(sender, label, 1);
			return true;
		} else {
			switch (args[0]) {
				default: case "help":
					if (!sender.hasPermission("simplepromoter.help")) {
						ErrorMessage.NO_PERMISSION.sendTo(sender);
						return true;
					}
					int pageNum = 1;
					if (args.length > 1) {
						try {
							pageNum = Integer.parseInt(args[1]);
						} catch (NumberFormatException e) {}
					}
					HelpMessage.SIMPLEPROMOTER_HELP.sendTo(sender, label, pageNum);
					return true;
				
				case "reload":
					if (!sender.hasPermission("simplepromoter.reload")) {
						ErrorMessage.NO_PERMISSION.sendTo(sender);
						return true;
					}
					plugin.reloadConfig();
					plugin.getMessageHandler().reloadMessages();
					NoticeMessage.PLUGIN_RELOADED.sendTo(sender);
					return true;
					
				case "version":
					sender.sendMessage(String.format(ChatColor.GREEN + "SimplePromoter" + ChatColor.GRAY + "-" + ChatColor.GOLD + "v%s", plugin.getVersion()));
					sender.sendMessage(ChatColor.BLUE + "By Stealth2800" + ChatColor.GRAY + "-" + ChatColor.AQUA + "http://stealthyone.com/bukkit");
					sender.sendMessage(ChatColor.DARK_AQUA + "BukkitDev: " + ChatColor.AQUA + "http://bit.ly/TWTvhN");
					if (plugin.isUpdate()) {
						//Update available, notify player
						String newVersion = new UpdateChecker(plugin, "http://dev.bukkit.org/server-mods/simplepromoter/files.rss").getVersion();
						sender.sendMessage(ChatColor.RED + "Found different version on BukkitDev! (New: " + newVersion + " | Current: " + plugin.getVersion() + ")");
					}
					return true;
			}
		}
	}

}