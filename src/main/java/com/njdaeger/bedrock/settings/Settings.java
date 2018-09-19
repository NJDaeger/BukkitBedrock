package com.njdaeger.bedrock.settings;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.settings.ISettings;

import java.util.regex.Pattern;

import static com.njdaeger.bedrock.api.Bedrock.debug;
import static com.njdaeger.bedrock.settings.Key.*;

public final class Settings extends Configuration implements ISettings {

    static final String NONE = "%none%";

    private transient boolean hasGamemodeSpecificPerms;
    private transient String lang;
    private transient boolean debugMode;

    private transient boolean hasAfkAwayMessage;
    private transient boolean hasAfkBackMessage;
    private transient boolean hasAfkAwayMoreInfoMessage;
    private transient boolean isAfkMoreInfoEnabled;
    private transient String afkAwayMoreInfoFormat;
    private transient String afkAwayFormat;
    private transient String afkBackFormat;

    private transient int historyRecordLimit;
    private transient boolean saveHistory;
    private transient int maxSaveHistory;

    private transient Pattern characterPattern;
    private transient boolean invertedWhitelist;
    private transient boolean allowChatColor;
    private transient boolean chatColorPerm;
    private transient int maxLength;
    private transient boolean allowReuse;
    private transient boolean reusePermission;

    private transient boolean speedSpecificPerms;
    private transient boolean maxSpeedBypass;
    private transient float maxWalk;
    private transient float maxFly;
    private transient boolean allowNegative;
    private transient boolean negativePerm;
    private transient boolean minSpeedBypass;
    private transient float minWalk;
    private transient float minFly;

    public Settings(IBedrock plugin) {
        super(plugin, ConfigType.YML, "config");
    }

    @Override
    public void reloadSettings() {
        this.lang = LANG_FILE.getString();
        this.debugMode = DEBUG.getBoolean();
        this.hasGamemodeSpecificPerms = GAMEMODE_SPECIFIC_PERMISSIONS.getBoolean();
        debug("Gamemode Specific Perms: " + hasGamemodeSpecificPerms);

        //afk more info options
        this.isAfkMoreInfoEnabled = AFK_MORE_INFO.getBoolean();
        this.hasAfkAwayMoreInfoMessage = isAfkMoreInfoEnabled && (AFK_AWAY_MORE_INFO_MESSAGE.get() == null || !AFK_AWAY_MORE_INFO_MESSAGE
                .get()
                .equals(NONE));
        this.afkAwayMoreInfoFormat = hasAfkAwayMoreInfoMessage ? AFK_AWAY_MORE_INFO_MESSAGE.getString() : null;
        System.out.println(afkAwayMoreInfoFormat);
        debug("AFK More Info Enabled: " + isAfkMoreInfoEnabled);
        debug("AFK More Info Format: " + (hasAfkAwayMoreInfoMessage ? "custom" : "default"));

        //afk back options
        this.hasAfkBackMessage = AFK_BACK_MESSAGE.get() == null || AFK_BACK_MESSAGE.get().equals(NONE);
        this.afkBackFormat = hasAfkBackMessage ? AFK_BACK_MESSAGE.getString() : null;
        debug("AFK Back Message: " + hasAfkBackMessage);

        //afk away options
        this.hasAfkAwayMessage = AFK_AWAY_MESSAGE.get() == null || AFK_AWAY_MESSAGE.get().equals(NONE);
        this.afkAwayFormat = hasAfkAwayMessage ? AFK_AWAY_MESSAGE.getString() : null;
        debug("AFK Away Message: " + hasAfkAwayMessage);

        this.maxSaveHistory = BACK_SAVE_HISTORY_MAX.getInt();
        this.historyRecordLimit = BACK_MAX_RECORD.getInt();
        this.saveHistory = BACK_SAVE_HISTORY.getBoolean();

        this.characterPattern = Pattern.compile(NICK_CHARACTER_WHITELIST.getString());
        this.invertedWhitelist = NICK_INVERSE_WHITELIST.getBoolean();
        this.allowChatColor = NICK_CHATCOLOR.getBoolean();
        this.chatColorPerm = allowChatColor && NICK_COLOR_PERMISSION.getBoolean();
        this.maxLength = NICK_MAXLENGTH.getInt();
        this.allowReuse = NICK_REUSE.getBoolean();
        this.reusePermission = allowReuse && NICK_REUSE_PERMiSSION.getBoolean();

        this.speedSpecificPerms = SPEED_SPECIFIC_PERMISSIONS.getBoolean();
        this.maxSpeedBypass = SPEED_BYPASS_MAX_PERMISSION.getBoolean();
        this.minSpeedBypass = SPEED_BYPASS_MIN_PERMISSION.getBoolean();
        this.allowNegative = SPEED_ALLOW_NEGATIVE.getBoolean();
        this.negativePerm = allowNegative && SPEED_NEGATIVE_PERMISSION.getBoolean();
        this.minWalk = SPEED_MIN_WALK.getFloat();
        this.maxWalk = SPEED_MAX_WALK.getFloat();
        this.minFly = SPEED_MIN_FLY.getFloat();
        this.maxFly = SPEED_MAX_FLY.getFloat();

    }

    @Override
    public String getLang() {
        return lang;
    }

    @Override
    public boolean isDebugMode() {
        return debugMode;
    }

    @Override
    public boolean hasGamemodeSpecificPermissions() {
        return hasGamemodeSpecificPerms;
    }

    @Override
    public boolean hasSpeedSpecificPermissions() {
        return speedSpecificPerms;
    }

    @Override
    public boolean hasMaxSpeedBypass() {
        return maxSpeedBypass;
    }

    @Override
    public float getMaxWalkSpeed() {
        return maxWalk;
    }

    @Override
    public float getMaxFlySpeed() {
        return maxFly;
    }

    @Override
    public boolean allowNegativeSpeed() {
        return allowNegative;
    }

    @Override
    public boolean hasNegativeSpeedPermission() {
        return negativePerm;
    }

    @Override
    public boolean hasMinSpeedBypass() {
        return minSpeedBypass;
    }

    @Override
    public float getMinWalkSpeed() {
        return minWalk;
    }

    @Override
    public float getMinFlySpeed() {
        return minFly;
    }

    @Override
    public Pattern getNickCharacterWhitelist() {
        return characterPattern;
    }

    @Override
    public boolean hasNickInversedWhitelist() {
        return invertedWhitelist;
    }

    @Override
    public boolean allowNickColor() {
        return allowChatColor;
    }

    @Override
    public boolean useNickColorPermission() {
        return chatColorPerm;
    }

    @Override
    public int getMaxNickLength() {
        return maxLength;
    }

    @Override
    public boolean allowNickDuplicates() {
        return allowReuse;
    }

    @Override
    public boolean useNickDuplicatePermission() {
        return reusePermission;
    }

    @Override
    public int getHistoryRecordLimit() {
        return historyRecordLimit;
    }

    @Override
    public boolean saveHistoryLog() {
        return saveHistory;
    }

    @Override
    public int getMaxSaveHistory() {
        return maxSaveHistory;
    }

    @Override
    public String getAfkAwayFormat() {
        return afkAwayFormat;
    }

    @Override
    public boolean hasAfkAwayMessage() {
        return hasAfkAwayMessage;
    }

    @Override
    public String getAfkBackMessage() {
        return afkBackFormat;
    }

    @Override
    public boolean hasAfkBackMessage() {
        return hasAfkBackMessage;
    }

    @Override
    public String getAfkAwayMoreInfoMessage() {
        return afkAwayMoreInfoFormat;
    }

    @Override
    public boolean hasAfkAwayMoreInfoMessage() {
        return hasAfkAwayMoreInfoMessage;
    }

    @Override
    public boolean isAfkMoreInfoEnabled() {
        return isAfkMoreInfoEnabled;
    }

}
