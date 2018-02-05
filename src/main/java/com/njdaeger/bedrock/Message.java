package com.njdaeger.bedrock;

public enum Message {
    
    ERROR_NO_PERMISSION("errorNoPermission"),
    ERROR_NOT_ENOUGH_ARGS("errorNotEnoughArgs"),
    ERROR_TOO_MANY_ARGS("errorTooManyArgs"),
    ERROR_INCORRECT_SENDER("errorIncorrectSender"),
    ERROR_USER_NOT_FOUND("errorUserNotFound"),
    
    AFK_DESC("afkCommandDesc"),
    AFK_USAGE("afkCommandUsage"),
    AFK_AWAY_MESSAGE("afkAwayMessage"),
    AFK_BACK_MESSAGE("afkBackMessage"),
    AFK_AWAY_MESSAGE_MOREINFO("afkMessageMoreInfo"),
    
    HEAL_DESC("healCommandDesc"),
    HEAL_USAGE("healCommandUsage"),
    HEAL_SELF_MESSAGE("healSelfMessage"),
    HEAL_OTHER_MESSAGE_SENDER("healOtherSender"),
    HEAL_OTHER_MESSAGE_RECEIVER("healOtherReceiver");

    private final String key;
    
    Message(String key) {
        this.key = key;
    }
    
    public String key() {
        return key;
    }
    
}
