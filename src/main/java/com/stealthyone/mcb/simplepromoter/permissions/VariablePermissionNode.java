package com.stealthyone.mcb.simplepromoter.permissions;

import org.bukkit.command.CommandSender;

import java.util.Arrays;

public enum VariablePermissionNode {

    FROM(1),
    GROUP(1);

    private String permission;
    private int variableCount;

    private VariablePermissionNode(int variableCount) {
        this.variableCount = variableCount;
        this.permission = PermissionNode.PREFIX + this.toString().toLowerCase().replace("_", ".");
    }

    public final boolean isAllowed(CommandSender sender, String... variables) {
        if (variables.length != variableCount) {
            throw new IllegalArgumentException("Too many variables for VariablePermissionNode: " + toString());
        } else {
            return sender.hasPermission(permission + "." + Arrays.asList(variables).toString().replace("[", "").replace("]", "").replace(", ", "."));
        }
    }

}