package com.njdaeger.bedrock.api.lang;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;

import java.util.function.Function;

public enum Message {
    
    /**
     * {0} - The thing that isnt a number
     */
    ERROR_NOT_A_NUMBER("errors.notANumber", "GIVEN"),
    /**
     * {0} - Array of permissions
     */
    ERROR_NO_PERMISSION("errors.noPermission", "PERMISSIONS"),
    /**
     * {0} - Minimum<p>
     * {1} - Given
     */
    ERROR_NOT_ENOUGH_ARGS("errors.notEnoughArgs", "MINIMUM", "GIVEN"),
    /**
     * {0} - Maximum<p>
     * {1} - Given
     */
    ERROR_TOO_MANY_ARGS("errors.tooManyArgs", "MAXIMUM", "GIVEN"),
    /**
     * {0} - Array of allowed senders
     */
    ERROR_INCORRECT_SENDER("errors.incorrectSender", "SENDERS"),
    /**
     * {0} - The user that was trying to be found
     */
    ERROR_USER_NOT_FOUND("errors.userNotFound", "USER"),
    
    /**
     * {0} - Home trying to be found
     */
    ERROR_HOME_NOT_EXIST("errors.homeNotExist", "HOME"),
    
    /**
     * No placeholders
     */
    ERROR_USER_NOT_SPECIFIED("errors.userNotSpecified"),
    
    /**
     * {0} - User that doesn't have any homes
     */
    ERROR_NO_HOMES("errors.noHomes"),
    
    /**
     * {0} - Name of the home trying to be created
     */
    ERROR_HOME_EXISTS("errors.homeExists"),
    
    /**
     * {0} - Name of the home that couldnt be deleted
     */
    ERROR_HOME_NOT_DELETED("errors.homeNotDeleted"),
    
    /**
     * {0} - The unknown argument
     */
    ERROR_UNKNOWN_ARGUMENT("errors.argumentUnknown"),
    
    /**
     * {0} - Name of the channel that already exists
     */
    ERROR_CHANNEL_EXISTS("errors.channelExists"),
    
    /**
     * {0} - Name of the channel that could not be found
     */
    ERROR_CHANNEL_NOT_FOUND("errors.channelNotFound"),
    
    /**
     * {0} - Name of the display that wasn't known
     */
    ERROR_UNKNOWN_DISPLAY("errors.unknownDisplay"),
    
    /**
     * {0} - The channel the user is already in
     */
    ERROR_ALREADY_IN_CHANNEL("errors.alreadyInChannel"),
    
    /**
     * {0} - The channel the user is not in
     */
    ERROR_NOT_IN_CHANNEL("errors.notInChannel"),
    
    /**
     * {0} - The name of the user
     * {1} - The name of the channel
     */
    ERROR_USER_NOT_IN_CHANNEL("errors.userNotInChannel"),
    
    /**
     * {0} - The name of the user
     * {1} - The name of the channel
     */
    ERROR_USER_ALREADY_IN_CHANNEL("errors.userAlreadyInChannel"),
    
    /**
     * {0} - The channel thats already selected
     */
    ERROR_CHANNEL_SELECTED("errors.channelSelected"),
    
    /**
     * No placeholders
     */
    AFK_DESC("afk.commandDesc"),
    /**
     * No placeholders
     */
    AFK_USAGE("afk.commandUsage"),
    /**
     * {0} - User going afk
     */
    AFK_AWAY_MESSAGE("afk.awayMessage"),
    /**
     * {0} - User coming back
     */
    AFK_BACK_MESSAGE("afk.backMessage"),
    /**
     * {0} - User going afk <p>
     * {1} - The user's reasoning to going afk
     */
    AFK_AWAY_MESSAGE_MOREINFO("afk.messageMoreInfo"),
    
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
    
    /**
     * {0} - Name of the channel created
     */
    CHANNEL_CREATE("channelCreate"),
    
    /**
     * {0} - Name of the channel created
     */
    CHANNEL_TEMP("channelTemp"),
    
    /**
     * {0} - Name of the channel deleted
     */
    CHANNEL_DELETE("channelDelete"),
    
    /**
     * {0} - Name of the channel that was joined
     */
    CHANNEL_JOIN("channelJoin"),
    
    /**
     * {0} - Name of the channel that was left
     */
    CHANNEL_LEAVE("channelLeave"),
    
    /**
     * {0} - Name of the channel that was selected
     */
    CHANNEL_SELECT("channelSelect"),
    
    /**
     * {0} - Name of the user adding the other
     * {1} - Name of the channel the other is being added to
     */
    CHANNEL_ADD_RECEIVER("channelAddReceiver"),
    
    /**
     * {0} - Name of the user being added
     * {1} - Name of channel the user is being added to
     */
    CHANNEL_ADD_SENDER("channelAddSender"),
    
    /**
     * {0} - Name of the channel the user is being kicked from
     */
    CHANNEL_KICK_RECEIVER("channelKickReceiver"),
    
    /**
     * {0} - Name of the user being kicked
     * {1} - Name of the channel the user is being kicked from
     */
    CHANNEL_KICK_SENDER("channelKickSender"),
    
    /**
     * {0} - The number of players in the channel
     */
    CHANNEL_SIZE("channelInfoSize"),
    
    /**
     * {0} - The name of the channel
     */
    CHANNEL_NAME("channelInfoName"),
    
    /**
     * {0} - The channel display type
     */
    CHANNEL_DISPLAY("channelInfoDisplay"),
    
    /**
     * {0} - When the channel will close
     */
    CHANNEL_CLOSE("channelInfoClose"),
    
    /**
     * {0} - The channel prefix
     */
    CHANNEL_PREFIX("channelInfoPrefix"),
    
    /**
     * {0} - The name of the channel
     */
    CHANNEL_INFO_TITLE("channelInfoTitle"),
    
    /**
     * {0} - The users currently in this channel
     */
    CHANNEL_USERS("channelInfoUsers"),
    
    /**
     * {0} - The user
     */
    CHANNEL_USERS_FORMAT("channelInfoUserFormat"),
    
    /**
     * No placeholders
     */
    CHANNEL_DISPLAY_ENABLE("channelDisplayEnable"),
    
    /**
     * No placeholders
     */
    CHANNEL_DISPLAY_DISABLE("channelDisplayDisable");

    private final String key;
    private final String[] placeholders;
    private final Function<IBedrock, String> alternate;

    Message(String key, Function<IBedrock, String> alternate, String... placeholders) {
        this.key = key;
        this.alternate = alternate;
        this.placeholders = placeholders;
    }

    Message(String key, String... placeholders) {
        this.placeholders = placeholders;
        this.alternate = null;
        this.key = key;
    }
    
    public String getKey() {
        return key;
    }

    public String[] getPlaceholders() {
        return placeholders;
    }

    String format() {
        String message = null;
        if (alternate != null) {
            message = alternate.apply(Bedrock.getBedrock());
        }
        if (message == null) {
            if (Bedrock.getMessageFile().contains(getKey(), true)) {
                message = Bedrock.getMessageFile().getString(getKey());
            }
            if (message == null) message = "null";
        }
        if (placeholders != null) {
            for (int i = 0; i < placeholders.length; i++) {
                message = message.replace(placeholders[i], "{" + i + "}");
            }
        }
        return message;
    }

    public String getRaw() {
        return Bedrock.getMessageFile().get(this);
    }

    public String translate(Object... placeholders) {
        String message = getRaw();
        for (int i = 0; i < placeholders.length; i++) {
            message = message.replace("{" + i + "}", placeholders[i] != null ? placeholders[i].toString() : "null");
        }
        return message;
    }

}
