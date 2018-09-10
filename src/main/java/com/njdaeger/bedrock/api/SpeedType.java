package com.njdaeger.bedrock.api;

public enum SpeedType {
    
    FLYING("Flying", Permission.COMMAND_SPEED_FLY),
    WALKING("Walking", Permission.COMMAND_SPEED_WALK);
    
    private final String niceName;
    private final Permission permission;

    SpeedType(String nicename, Permission permission) {
        this.niceName = nicename;
        this.permission = permission;
    }

    public Permission getPermission() {
        return permission;
    }

    public String getNicename() {
        return niceName;
    }
}
