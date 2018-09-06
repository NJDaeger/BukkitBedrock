package com.njdaeger.bedrock.api;

import org.bukkit.GameMode;

import java.util.stream.Stream;

public enum Gamemode {
    
    SURVIVAL(GameMode.SURVIVAL, Permission.COMMAND_GAMEMODE_SURVIVAL, "Survival", "0", "s", "surv", "smp"),
    SPECTATOR(GameMode.SPECTATOR, Permission.COMMAND_GAMEMODE_SPECTATOR, "Spectator", "3", "sp", "spect"),
    CREATIVE(GameMode.CREATIVE, Permission.COMMAND_GAMEMODE_CREATIVE, "Creative", "1", "c", "cr", "cmp"),
    ADVENTURE(GameMode.ADVENTURE, Permission.COMMAND_GAMEMODE_ADVENTURE, "Adventure", "2", "a", "adv", "amp");
    
    private final Permission permission;
    private final GameMode bukkitMode;
    private final String[] aliases;
    private final String nicename;
    
    Gamemode(GameMode bukkitMode, Permission permission, String... aliases) {
        this.aliases = aliases;
        this.nicename = aliases[0];
        this.bukkitMode = bukkitMode;
        this.permission = permission;
    }
    
    public String getNicename() {
        return nicename;
    }
    
    public String[] getAliases() {
        return aliases;
    }
    
    public GameMode getBukkitMode() {
        return bukkitMode;
    }
    
    public Permission getPermission() {
        return permission;
    }
    
    public static Gamemode resolveGamemode(String alias) {
        for (Gamemode gm : values()) {
            if (gm.getNicename().equalsIgnoreCase(alias) || Stream.of(gm.aliases).anyMatch(alias::equalsIgnoreCase)) {
                return gm;
            }
        }
        return null;
    }
}
