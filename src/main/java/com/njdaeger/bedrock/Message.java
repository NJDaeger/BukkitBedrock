package com.njdaeger.bedrock;

public enum Message {
    
    AFK_DESC("afkCommandDesc"),
    AFK_USAGE("afkCommandUsage"),
    AFK_MESSAGE("afkMessage"),
    AFK_MESSAGE_MOREINFO("afkMessageMoreInfo");

    private final String key;
    
    Message(String key) {
        this.key = key;
    }
    
    public String key() {
        return key;
    }
    
}
