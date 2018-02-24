package com.njdaeger.bedrock.api;

public enum Permission {
    
    COMMAND_NICK("bedrock.nick"),
    COMMAND_NICK_OTHER("bedrock.nick.other"),
    COMMAND_BACK("bedrock.back"),
    COMMAND_HOME("bedrock.homes.home"),
    COMMAND_HOME_OTHER("bedrock.homes.home.other"),
    COMMAND_HOME_OTHERTOME("bedrock.homes.home.other.to-me"),
    COMMAND_HOME_METOOTHER("bedrock.homes.home.other.me-to-other"),
    COMMAND_HOME_OTHERTOOWN("bedrock.homes.home.other.to-own"),
    COMMAND_HOME_OTHERTOOTHER("bedrock.homes.home.other.to-other"),
    COMMAND_SETHOME("bedrock.homes.sethome"),
    COMMAND_SETHOME_OTHER("bedrock.homes.sethome.other"),
    COMMAND_DELHOME("bedrock.homes.delhome"),
    COMMAND_DELHOME_OTHER("bedrock.homes.delhome.other"),
    COMMAND_LISTHOMES("bedrock.homes.listhomes"),
    COMMAND_LISTHOMES_OTHER("bedrock.homes.listhomes.other"),
    COMMAND_SPEED("bedrock.speed"),
    COMMAND_SPEED_WALK("bedrock.speed.type.walk"),
    COMMAND_SPEED_FLY("bedrock.speed.type.fly"),
    COMMAND_SPEED_OTHER("bedrock.speed.other"),
    COMMAND_GAMEMODE("bedrock.gamemode"),
    COMMAND_GAMEMODE_OTHER("bedrock.gamemode.other"),
    COMMAND_GAMEMODE_CREATIVE("bedrock.gamemode.type.creative"),
    COMMAND_GAMEMODE_SURVIVAL("bedrock.gamemode.type.survival"),
    COMMAND_GAMEMODE_ADVENTURE("bedrock.gamemode.type.adventure"),
    COMMAND_GAMEMODE_SPECTATOR("bedrock.gamemode.type.spectator"),
    COMMAND_AFK("bedrock.afk"),
    COMMAND_AFK_MESSAGE("bedrock.afk.message"),
    COMMAND_AFK_NOTIFY("bedrock.afk.notify"),
    COMMAND_HEAL("bedrock.heal"),
    COMMAND_HEAL_OTHER("bedrock.heal.other");
    
    private String permission;
    
    Permission(String permission) {
        this.permission = permission;
    }
    
    @Override
    public String toString() {
        return permission;
    }
}
