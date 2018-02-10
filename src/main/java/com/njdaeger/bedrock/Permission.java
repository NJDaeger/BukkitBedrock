package com.njdaeger.bedrock;

public enum Permission {
    
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
