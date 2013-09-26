package com.stealthyone.mcb.simplepromoter.commands;

import com.stealthyone.mcb.simplepromoter.SimplePromoter;
import com.stealthyone.mcb.simplepromoter.SimplePromoter.Log;
import com.stealthyone.mcb.simplepromoter.config.ConfigHelper;
import com.stealthyone.mcb.simplepromoter.messages.ErrorMessage;
import com.stealthyone.mcb.simplepromoter.messages.NoticeMessage;
import com.stealthyone.mcb.simplepromoter.messages.UsageMessage;
import com.stealthyone.mcb.simplepromoter.permissions.PermissionNode;
import com.stealthyone.mcb.stbukkitlib.lib.utils.PlayerUtils;
import com.stealthyone.mcb.stbukkitlib.lib.utils.StringUtils;
import com.stealthyone.mcb.stbukkitlib.lib.utils.exceptions.NameMatchesMultiplePlayersException;
import com.stealthyone.mcb.stbukkitlib.lib.utils.exceptions.NameMatchesNoPlayers;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import ru.tehkode.permissions.bukkit.PermissionsEx;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;

public final class CmdSetrank implements CommandExecutor {

	private SimplePromoter plugin;
	
	public CmdSetrank(SimplePromoter plugin) {
		this.plugin = plugin;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!PermissionNode.SETRANK.isAllowed(sender)) {
			// Permission check
			ErrorMessage.NO_PERMISSION.sendTo(sender);
		} else if (args.length < 2) {
			// Argument check
			UsageMessage.SETRANK.sendTo(sender);
		} else {
			/* Get player */
			String playerName = args[0];
			OfflinePlayer target = Bukkit.getOfflinePlayer(args[0]);
			try {
				target = PlayerUtils.getPlayer(playerName);
			} catch (NameMatchesNoPlayers e) {
				target = Bukkit.getOfflinePlayer(playerName);
			} catch (NameMatchesMultiplePlayersException e) {
				ErrorMessage.NAME_MATCHES_MULTIPLE.sendTo(sender);
				return true;
			}
			
			/* Get groups */
			List<String> newArgs = new ArrayList<String>(Arrays.asList(args));
			newArgs.remove(0);
			Iterator<String> newArgsIterator = newArgs.iterator();
			
			String groups = "";
			int groupCount = 0;
			String groupName;
			
			String nonexistantGroups = "";
			String noPermGroups = "";
			String notFromGroups = "";
			
			List<String> allowedGroups = new ArrayList<String>();
			
			while (newArgsIterator.hasNext()) {
				groupName = newArgsIterator.next();
				if (!StringUtils.equalsIgnoreCaseMultiple(groupName, "yes", "no")) {
					if (!PermissionsEx.getPermissionManager().getGroup(groupName).isVirtual()) {
                        if (!PermissionNode.GROUP.isAllowed(sender, groupName.toLowerCase())) {
							if (!noPermGroups.equalsIgnoreCase("")) {
								noPermGroups += ", ";
							}
							noPermGroups += PermissionsEx.getPermissionManager().getGroup(groupName).getName();
							continue;
						}
						for (String fromGroup : PermissionsEx.getUser(target.getName()).getGroupsNames()) {
                            if (!PermissionNode.FROM.isAllowed(sender, groupName.toLowerCase())) {
								if (!notFromGroups.equalsIgnoreCase("")) {
									notFromGroups += ", ";
								}
								notFromGroups += PermissionsEx.getPermissionManager().getGroup(groupName).getName();
								continue;
							}
						}
						if (!groups.equalsIgnoreCase("")) {
							groups += ", ";
						}
						groups += PermissionsEx.getPermissionManager().getGroup(groupName).getName();
						allowedGroups.add(PermissionsEx.getPermissionManager().getGroup(groupName).getName());
						groupCount ++;
					} else {
						nonexistantGroups += nonexistantGroups.equalsIgnoreCase("") ? groupName : "," + groupName;
					}
				} else {
					newArgsIterator.remove();
				}
			}
			
			Log.debug("groupCount: " + groupCount);
			
			if (groupCount == 0) {
				if (nonexistantGroups.equalsIgnoreCase("") && noPermGroups.equalsIgnoreCase("")) {
					UsageMessage.SETRANK.sendTo(sender);
				} else if (!nonexistantGroups.equalsIgnoreCase("")) {
					String groupNoExist = Arrays.toString(ErrorMessage.GROUPS_DONT_EXIST.getMessage()).replace("[", "").replace("]", "");
					if (!nonexistantGroups.contains(",")) {
						groupNoExist = groupNoExist.replace("{DONT}", "doesn't").replace("{s}", "");
					} else {
						groupNoExist = groupNoExist.replace("{DONT}", "don't").replace("{s}", "s");
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(groupNoExist, nonexistantGroups)));
				}
				
				if (!noPermGroups.equalsIgnoreCase("")) {
					String groupNoPermMsg = Arrays.toString(ErrorMessage.NO_PERM_FOR_GROUPS.getMessage()).replace("[", "").replace("]", "");
					if (!noPermGroups.contains(",")) {
						groupNoPermMsg = groupNoPermMsg.replace("{DONT}", "doesn't").replace("{s}", "");
					} else {
						groupNoPermMsg = groupNoPermMsg.replace("{DONT}", "don't").replace("{s}", "s");
					}
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(groupNoPermMsg, noPermGroups)));
				}
				
				if (!notFromGroups.equalsIgnoreCase("")) {
					sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(Arrays.toString(ErrorMessage.NO_PERM_FOR_FROM.getMessage()).replace("[", "").replace("]", ""), notFromGroups)));
				}
				return true;
			}
			
			String targetName = target.getName();
			
			String aChar = "a";
			
			aChar = StringUtils.startsWithVowel(groups) ? "an" : "a";
			
			boolean isBroadcast = true;
			if (args[args.length - 1].equalsIgnoreCase("no"))
				isBroadcast = false;
			
			PermissionsEx.getUser(targetName).setGroups(allowedGroups.toArray(new String[allowedGroups.size()]));

			/* Set the rank(s) of the player */
			String[] groupCast = allowedGroups.toArray(new String[allowedGroups.size()]);
			
			String messageToBroadcast = ChatColor.translateAlternateColorCodes('&', String.format(NoticeMessage.PROMOTION_MESSAGE.getMessage()[0], targetName, groups).replace("{a|an}", aChar));
			String youPromoted = NoticeMessage.YOU_PROMOTED.getMessage()[0].replace("[", "").replace("]", "").replace("{a|an}", aChar);
			sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(youPromoted, targetName, groups)));
			if (target.isOnline() && ConfigHelper.ENABLE_PROMOTION_SOUND.getBoolean()) {
				target.getPlayer().playSound(target.getPlayer().getLocation(), Sound.LEVEL_UP, 1.0F, 1.0F);
			}
			
			if (isBroadcast) {
				Bukkit.broadcastMessage(messageToBroadcast);
			} else if (target.isOnline()) {
				String privateMessage = NoticeMessage.PRIVATE_PROMOTION_MESSAGE.getMessage()[0];
				privateMessage = String.format(privateMessage.replace("{a|an}", aChar), groups);
				target.getPlayer().sendMessage(ChatColor.translateAlternateColorCodes('&', privateMessage));
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
			
			if (!noPermGroups.equalsIgnoreCase("")) {
				String groupNoPermMsg = Arrays.toString(ErrorMessage.NO_PERM_FOR_GROUPS.getMessage()).replace("[", "").replace("]", "");
				if (!noPermGroups.contains(",")) {
					groupNoPermMsg = groupNoPermMsg.replace("{DONT}", "doesn't").replace("{s}", "");
				} else {
					groupNoPermMsg = groupNoPermMsg.replace("{DONT}", "don't").replace("{s}", "s");
				}
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(groupNoPermMsg, noPermGroups)));
			}
			
			if (!notFromGroups.equalsIgnoreCase("")) {
				sender.sendMessage(ChatColor.translateAlternateColorCodes('&', String.format(Arrays.toString(ErrorMessage.NO_PERM_FOR_FROM.getMessage()).replace("[", "").replace("]", ""), notFromGroups)));
			}
		}
		return true;
	}
}