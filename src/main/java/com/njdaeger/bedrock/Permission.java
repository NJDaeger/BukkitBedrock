package com.njdaeger.bedrock;

public enum Permission {
    
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
