package com.njdaeger.bedrock.api;

public enum SpeedType {
    
    FLYING("Flying", Permission.COMMAND_SPEED_FLY),
    WALKING("Walking", Permission.COMMAND_SPEED_WALK);
    
    private String niceName;
    
    SpeedType(String nicename, Permission permission) {
        this.niceName = nicename;
    }
    
    public String getNicename() {
        return niceName;
    }
}
