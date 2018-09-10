package com.njdaeger.bedrock.user;

import com.njdaeger.bedrock.api.Gamemode;

public enum UserPath {

    NAME("name", String.class),
    AFK("afk", Boolean.class),
    DISPLAYNAME("displayname", String.class),
    WALKSPEED("walkspeed", Float.class),
    FLYSPEED("flyspeed", Float.class),
    GAMEMODE("gamemode", Gamemode.class),
    INFOBOARD("infoboard", Boolean.class),
    LASTX("location.last.x", Double.class),
    LASTY("location.last.y", Double.class),
    LASTZ("location.last.z", Double.class),
    LASTYAW("location.last.yaw", Float.class),
    LASTPITCH("location.last.pitch", Float.class),
    LASTWORLD("location.last.world", String.class),
    LOGOUTX("location.logout.x", Double.class),
    LOGOUTY("location.logout.y", Double.class),
    LOGOUTZ("location.logout.z", Double.class),
    LOGOUTYAW("location.logout.yaw", Float.class),
    LOGOUTPITCH("location.logout.pitch", Float.class),
    LOGOUTWORLD("location.logout.world", String.class);
    
    private String key;
    private Class<?> type;
    
    <T> UserPath(String key, Class<T> t) {
        this.key = key;
        this.type = t;
    }
    
    public String getKey() {
        return key;
    }
    
    @SuppressWarnings("unchecked")
    public <T> Class<T> getType() {
        return (Class<T>)type;
    }
    
}
