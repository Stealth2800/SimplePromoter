package com.stealthyone.mcb.simplepromoter.permissions;

import org.bukkit.command.CommandSender;

public enum PermissionNode {

    ADMIN_RELOAD,
    CHECKRANK,
    HELP,
    SETRANK;

    public final static String PREFIX = "simplepromoter.";

    private String permission;

    private PermissionNode() {
        this.permission = PermissionNode.PREFIX + this.toString().toLowerCase().replace("_", ".");
    }

    public final boolean isAllowed(CommandSender sender) {
        return sender.hasPermission(permission);
    }

    public final static VariablePermissionNode FROM = VariablePermissionNode.FROM;
    public final static VariablePermissionNode GROUP = VariablePermissionNode.GROUP;

}