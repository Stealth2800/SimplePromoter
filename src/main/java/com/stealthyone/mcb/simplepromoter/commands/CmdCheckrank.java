package com.stealthyone.mcb.simplepromoter.commands;

import com.stealthyone.mcb.simplepromoter.SimplePromoter;
import com.stealthyone.mcb.simplepromoter.messages.ErrorMessage;
import com.stealthyone.mcb.simplepromoter.messages.NoticeMessage;
import com.stealthyone.mcb.simplepromoter.messages.UsageMessage;
import com.stealthyone.mcb.simplepromoter.permissions.PermissionNode;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.Arrays;

public final class CmdCheckrank implements CommandExecutor {

	private SimplePromoter plugin;
	
	public CmdCheckrank(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!PermissionNode.CHECKRANK.isAllowed(sender)) {
			ErrorMessage.NO_PERMISSION.sendTo(sender);
			return true;
		}
		if (args.length == 0) {
			UsageMessage.CHECKRANK.sendTo(sender);
			return true;
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			
			String playerName = target.getName();
			String groupNames = Arrays.toString(PermissionsEx.getUser(playerName).getGroupsNames()).replace("[", "").replace("]", "");
			String message = Arrays.toString(NoticeMessage.PLAYER_PART_OF_GROUPS.getMessage()).replace("[", "").replace("]", "");
			if (groupNames.contains(",")) {
				message = message.replace("{s}", "s");
			} else {
				message = message.replace("{s}", "");
			}
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(message, playerName, groupNames)));
			return true;
		}
	}

}