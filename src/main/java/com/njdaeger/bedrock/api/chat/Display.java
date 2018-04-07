package com.njdaeger.bedrock.api.chat;

public enum Display {
    
    PREFIX,
    NAME,
    NONE;
    
    public static boolean contains(String value) {
        try {
            valueOf(value);
        } catch (IllegalArgumentException e) {
            return false;
        }
        return true;
    }
}
