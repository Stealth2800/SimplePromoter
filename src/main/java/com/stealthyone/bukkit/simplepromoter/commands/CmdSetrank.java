package com.stealthyone.bukkit.simplepromoter.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import ru.tehkode.permissions.bukkit.PermissionsEx;

import com.stealthyone.bukkit.simplepromoter.SimplePromoter;
import com.stealthyone.bukkit.simplepromoter.SimplePromoter.PluginLogger;
import com.stealthyone.bukkit.simplepromoter.messages.ErrorMessage;
import com.stealthyone.bukkit.simplepromoter.messages.NoticeMessage;
import com.stealthyone.bukkit.simplepromoter.messages.UsageMessage;
import com.stealthyone.bukkit.stcommonlib.utils.StringUtils;

public final class CmdSetrank implements CommandExecutor {

	private SimplePromoter plugin;
	
	public CmdSetrank(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (args.length < 2) {
			UsageMessage.SETRANK.sendTo(sender);
			return true;
		} else {
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			if (!target.hasPlayedBefore()) {
				ErrorMessage.PLAYER_DOESNT_EXIST.sendTo(sender);
				return true;
			}
			
			List<String> newArgs = new ArrayList<String>(Arrays.asList(args));
			newArgs.remove(0);
			Iterator<String> newArgsIterator = newArgs.iterator();
			
			String groups = "";
			int groupCount = 0;
			String groupName;
			
			String nonexistantGroups = "";
			
			while (newArgsIterator.hasNext()) {
				groupName = newArgsIterator.next();
				if (!StringUtils.equalsIgnoreCaseMultiple(groupName, "yes", "no")) {
					if (!PermissionsEx.getPermissionManager().getGroup(groupName).isVirtual()) {
						if (!groups.equalsIgnoreCase("")) {
							groups += ", ";
						}
						groups += PermissionsEx.getPermissionManager().getGroup(groupName).getName();
						groupCount ++;
					} else {
						if (nonexistantGroups.equalsIgnoreCase("")) {
							nonexistantGroups += groupName;
						} else {
							nonexistantGroups += ", " + groupName;
						}
					}
				} else {
					newArgsIterator.remove();
				}
			}
			
			PluginLogger.debug("groupCount: " + groupCount);
			
			if (groupCount == 0) {
				if (nonexistantGroups.equalsIgnoreCase("")) {
					UsageMessage.SETRANK.sendTo(sender);
				} else {
					if (!nonexistantGroups.equalsIgnoreCase("")) {
						String groupNoExist = Arrays.toString(ErrorMessage.GROUPS_DONT_EXIST.getMessage()).replace("[", "").replace("]", "");
						if (!nonexistantGroups.contains(",")) {
							groupNoExist = groupNoExist.replace("{DONT}", "doesn't").replace("{s}", "");
						} else {
							groupNoExist = groupNoExist.replace("{DONT}", "don't").replace("{s}", "s");
						}
						sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(groupNoExist, nonexistantGroups)));
					}
				}
				return true;
			}
			
			String targetName = target.getName();
			
			String aChar = "a";
			
			if (StringUtils.startsWithVowel(groups)) {
				aChar = "an";
			} else {
				aChar = "a";
			}
			
			boolean isBroadcast = true;
			if (args[args.length - 1].equalsIgnoreCase("no")) {
				isBroadcast = false;
			}
			
			String message = NoticeMessage.PROMOTION_MESSAGE.getMessage()[0];
			if (!isBroadcast) {
				message = NoticeMessage.PRIVATE_PROMOTION_MESSAGE.getMessage()[0];
			}
			String messageToBroadcast = ChatColor.translateAlternateColorCodes('&', String.format(message, targetName, groups).replace("{a|an}", aChar));
			String youPromoted = NoticeMessage.YOU_PROMOTED.getMessage()[0].replace("[", "").replace("]", "").replace("{a|an}", aChar);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(youPromoted, targetName, groups)));
			if (isBroadcast) {
				Bukkit.broadcastMessage(messageToBroadcast);
			} else {
				if (target.isOnline()) {
					target.getPlayer().sendMessage(messageToBroadcast);
				}
			}
			if (!nonexistantGroups.equalsIgnoreCase("")) {
				String groupNoExist = Arrays.toString(ErrorMessage.GROUPS_DONT_EXIST.getMessage()).replace("[", "").replace("]", "");
				if (!nonexistantGroups.contains(",")) {
					groupNoExist = groupNoExist.replace("{DONT}", "doesn't").replace("{s}", "");
				} else {
					groupNoExist = groupNoExist.replace("{DONT}", "don't").replace("{s}", "s");
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(groupNoExist, nonexistantGroups)));
			}
			return true;
		}
	}
}