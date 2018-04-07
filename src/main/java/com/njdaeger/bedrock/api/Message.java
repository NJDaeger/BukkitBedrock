package com.njdaeger.bedrock.api;

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
     * {0} - Home trying to be found
     * {1} - User home trying to be found from
     */
    ERROR_HOME_NOT_FOUND("errorHomeNotFound"),
    
    /**
     * No placeholders
     */
    ERROR_USER_NOT_SPECIFIED("errorUserNotSpecified"),
    
    /**
     * {0} - User that doesn't have any homes
     */
    ERROR_NO_HOMES("errorNoHomes"),
    
    /**
     * {0} - Name of the home trying to be created
     */
    ERROR_HOME_EXISTS("errorHomeExists"),
    
    /**
     * {0} - Name of the home that couldnt be deleted
     */
    ERROR_HOME_NOT_DELETED("errorHomeNotDeleted"),
    
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
    SPEED_OTHER_SENDER("speedOtherSender"),
    
    /**
     * No placeholders
     */
    HOME_DESC("homeCommandDesc"),
    
    /**
     * No placeholders
     */
    HOME_USAGE("homeCommandUsage"),
    
    /**
     * {0} - Name of the home
     */
    HOME_SENDER_TO_OWN("homeSenderToOwnHome"),
    
    /**
     * {0} - User being sent<p>
     * {1} - Home user is being sent to
     */
    HOME_SENDER_SEND_OTHER("homeSenderSentToOther"),
    
    /**
     * {0} - User running command<p>
     * {1} - Name of the home the receiver is being sent to
     */
    HOME_RECEIVER_SEND_OTHER("homeReceiverSentToOther"),
    
    /**
     * {0} - User being sent<p>
     * {1} - User who owns the home being sent to<p>
     * {2} - Name of the home
     */
    HOME_SENDER_OTHER_TO_OTHER("homeSenderSentOtherToOther"),
    
    /**
     * {0} - User sending command<p>
     * {1} - User who owns the home being sent to<p>
     * {2} - Name of the home
     */
    HOME_RECEIVER_OTHER_TO_OTHER("homeReceiverSentOtherToOther"),
    
    /**
     * {0} - User who owns the home being sent to<p>
     * {1} - Name of the home
     */
    HOME_SENDER_TO_OTHER("homeSenderToOther"),
    
    /**
     * No placeholders
     */
    SETHOME_DESC("sethomeCommandDesc"),
    
    /**
     * No placeholders
     */
    SETHOME_USAGE("sethomeCommandUsage"),
    
    /**
     * {0} - The home created
     */
    SETHOME_SENDER("sethomeSender"),
    
    /**
     * {0} - User creating the home<p>
     * {1} - Home created
     */
    SETHOME_OTHER_CREATED_RECEIVER("sethomeReceiverOther"),
    
    /**
     * {0} - Home created<p>
     * {1} - User home being created for
     */
    SETHOME_OTHER_CREATED_SENDER("sethomeSenderOther"),
    
    /**
     * No placeholders
     */
    DELHOME_DESC("delhomeCommandDesc"),
    
    /**
     * No placeholders
     */
    DELHOME_USAGE("delhomeCommandUsage"),
    
    /**
     * {0} - Home deleted
     */
    DELHOME_SENDER("delhomeSender"),
    
    /**
     * {0} - Home being deleted<p>
     * {1} - User home being deleted from
     */
    DELHOME_OTHER_SENDER("delhomeOtherSender"),
    
    /**
     * {0} - User deleting home<p>
     * {1} - Home being deleted
     */
    DELHOME_OTHER_RECEIVER("delhomeOtherReceiver"),
    
    /**
     * No placeholders
     */
    LISTHOMES_DESC("listhomesCommandDesc"),
    
    /**
     * No placeholders
     */
    LISTHOMES_USAGE("listhomesCommandUsage"),
    
    /**
     * {0} - name of the home
     */
    LISTHOMES_FORMAT("listhomesFormat"),
    
    /**
     * {0} - The user the homes are from<p>
     * {1} - The string of homes
     */
    LISTHOMES_MESSAGE_FORMAT("listhomesMessage"),
    
    /**
     * No placeholders
     */
    BACK_DESC("backDesc"),
    
    /**
     * No placeholders
     */
    BACK_USAGE("backUsage"),
    
    /**
     * No placeholders
     */
    BACK_MESSAGE("backMessage"),
    
    /**
     * No placeholders
     */
    NICK_DESC("nickDesc"),
    
    /**
     * No placeholders
     */
    NICK_USAGE("nickUsage"),
    
    /**
     * {0} - New nickname
     */
    NICK_SELF_MESSAGE("nickSelf"),
    
    /**
     * {0} - User changing nickname
     * {1} - New nickname
     */
    NICK_OTHER_RECEIVER("nickOtherReceiver"),
    
    /**
     * {0} - User whos nickname is being set
     * {1} - New nickname
     */
    NICK_OTHER_SENDER("nickOtherSender"),
    
    /**
     * No placeholders
     */
    NICK_RESET_SELF("nickResetSelf"),
    
    /**
     * {0} - User who reset the user's nickname
     */
    NICK_RESET_OTHER_RECEIVER("nickResetOtherReceiver"),
    
    /**
     * {0} - User whos nickname was reset
     */
    NICK_RESET_OTHER_SENDER("nickResetOtherSender"),
    
    /**
     * No placeholders
     */
    INFOBOARD_DESC("infoBoardDesc"),
    
    /**
     * No placeholders
     */
    INFOBOARD_USAGE("infoBoardUsage"),
    
    /**
     * No placeholders
     */
    INFOBOARD_ENABLED("infoBoardEnabled"),
    
    /**
     * No placeholders
     */
    INFOBOARD_DISABLED("infoBoardDisabled"),
    
    /**
     * {0} - Ram usage percent
     */
    INFOBOARD_RAM_PERCENT("infoBoardRamPercent"),
    
    /**
     * {0} - Memory used
     * {1} - Max memory available
     */
    INFOBOARD_RAM_MEGABYTES("infoBoardRamMegabytes"),
    
    /**
     * {0} - Cpu usage percent
     */
    INFOBOARD_CPU("infoBoardCpu"),
    
    /**
     * {0} - Ticks per second
     */
    INFOBOARD_TPS("infoBoardTps"),
    
    /**
     * {0} - Name of the channel
     */
    CUSTOM_CHANNEL_COMMAND_DESC("customChannelCommandDesc"),
    
    /**
     * {0} - Name of the channel
     */
    CUSTOM_CHANNEL_COMMAND_USAGE("customChannelCommandUsage"),
    
    /**
     * {0} - The channel prefix.
     * {1} - The user sending the message.
     * {2} - The message
     */
    CHANNEL_MESSAGE_FORMAT("channelMessageFormat"),
    
    /**
     * No placeholders
     */
    CHANNEL_DESC("channelDesc"),
    
    /**
     * No placeholders
     */
    CHANNEL_USAGE("channelUsage"),
    
    CHANNEL_CREATE("channelCreate"),
    
    CHANNEL_TEMP("channelTemp"),
    
    CHANNEL_DELETE("channelDelete"),
    
    CHANNEL_JOIN("channelJoin"),
    
    CHANNEL_LEAVE("channelLeave"),
    
    CHANNEL_ADD_RECEIVER("channelAddReceiver"),
    
    CHANNEL_ADD_SENDER("channelAddSender"),
    
    CHANNEL_KICK_RECEIVER("channelKickReceiver"),
    
    CHANNEL_KICK_SENDER("channelKickSender"),
    
    CHANNEL_SIZE("channelInfoSize"),
    
    CHANNEL_NAME("channelInfoName"),
    
    CHANNEL_DISPLAY("channelInfoDisplay"),
    
    CHANNEL_CLOSE("channelInfoClose"),
    
    CHANNEL_PREFIX("channelInfoPrefix"),
    
    CHANNEL_USERS("channelInfoUsers");

    private final String key;
    
    Message(String key) {
        this.key = key;
    }
    
    public String key() {
        return key;
    }
    
}
