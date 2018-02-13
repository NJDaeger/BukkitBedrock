package com.njdaeger.bedrock;

public enum Message {
    
    ERROR_NOT_A_NUMBER("errorNotANumber"),
    
    /**
     * {0} - Array of permissions
     */
    ERROR_NO_PERMISSION("errorNoPermission"),
    /**
     * {0} - Minimum<p>
     * {1} - Given
     */
    ERROR_NOT_ENOUGH_ARGS("errorNotEnoughArgs"),
    /**
     * {0} - Maximum<p>
     * {1} - Given
     */
    ERROR_TOO_MANY_ARGS("errorTooManyArgs"),
    /**
     * {0} - Array of allowed senders
     */
    ERROR_INCORRECT_SENDER("errorIncorrectSender"),
    /**
     * {0} - The user that was trying to be found
     */
    ERROR_USER_NOT_FOUND("errorUserNotFound"),
    
    /**
     * No placeholders
     */
    AFK_DESC("afkCommandDesc"),
    /**
     * No placeholders
     */
    AFK_USAGE("afkCommandUsage"),
    /**
     * {0} - User going afk
     */
    AFK_AWAY_MESSAGE("afkAwayMessage"),
    /**
     * {0} - User coming back
     */
    AFK_BACK_MESSAGE("afkBackMessage"),
    /**
     * {0} - User going afk <p>
     * {1} - The user's reasoning to going afk
     */
    AFK_AWAY_MESSAGE_MOREINFO("afkMessageMoreInfo"),
    
    /**
     * No placeholders
     */
    HEAL_DESC("healCommandDesc"),
    /**
     * No placeholders
     */
    HEAL_USAGE("healCommandUsage"),
    /**
     * No placeholders
     */
    HEAL_SELF_MESSAGE("healSelfMessage"),
    /**
     * {0} - User healed
     */
    HEAL_OTHER_MESSAGE_SENDER("healOtherSender"),
    /**
     * {0} - The healer
     */
    HEAL_OTHER_MESSAGE_RECEIVER("healOtherReceiver"),
    
    /**
     * No placeholders
     */
    GAMEMODE_DESC("gamemodeCommandDesc"),
    
    /**
     * No placeholders
     */
    GAMEMODE_USAGE("gamemodeCommandUsage"),
    
    /**
     * {0} - New gamemode
     */
    GAMEMODE_SELF("gamemodeSelfMessage"),
    
    /**
     * {0} - User running command<p>
     * {1} - New gamemode
     */
    GAMEMODE_OTHER_RECEIVER("gamemodeOtherReceiver"),
    
    /**
     * {0} - User getting gamemode changed<p>
     * {1} - New gamemode
     */
    GAMEMODE_OTHER_SENDER("gamemodeOtherSender"),
    
    /**
     * No placeholders
     */
    SPEED_DESC("speedCommandDesc"),
    
    /**
     * No placeholders
     */
    SPEED_USAGE("speedCommandUsage"),
    
    /**
     * {0} - Type of speed<p>
     * {1} - New speed
     */
    SPEED_SELF("speedSelfMessage"),
    
    /**
     * {0} - User running command<p>
     * {1} - Type of speed<p>
     * {2} - New speed
     */
    SPEED_OTHER_RECEIVER("speedOtherReceiver"),
    
    /**
     * {0} - User being affected<p>
     * {1} - Type of speed<p>
     * {2} - New speed
     */
    SPEED_OTHER_SENDER("speedOtherSender");

    private final String key;
    
    Message(String key) {
        this.key = key;
    }
    
    public String key() {
        return key;
    }
    
}
