package com.njdaeger.bedrock;

public enum SpeedType {
    
    FLYING("Flying"),
    WALKING("Walking");
    
    private String niceName;
    
    SpeedType(String nicename) {
        this.niceName = nicename;
    }
    
    public String getNicename() {
        return niceName;
    }
}
