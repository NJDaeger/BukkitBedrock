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

    private transient boolean gamemodeSpecificPermissions;
    private transient String lang;
    private transient boolean debugMode;

    private transient boolean afkAwayMessage;
    private transient boolean hasAfkBackMessage;
    private transient boolean afkAwayMoreInfoMessage;
    private transient boolean afkMoreInfoEnabled;
    private transient String afkAwayMoreInfoFormat;
    private transient String afkAwayFormat;
    private transient String afkBackFormat;

    private transient int historyRecordLimit;
    private transient boolean saveHistoryLog;
    private transient int maxSaveHistory;

    private transient Pattern nickCharacterWhitelist;
    private transient boolean nickInversedWhitelist;
    private transient boolean allowNickColor;
    private transient boolean nickColorPermission;
    private transient int maxNickLength;
    private transient boolean allowNickDuplicates;
    private transient boolean nickDuplicatePermission;

    private transient boolean speedSpecificPermissions;
    private transient boolean maxSpeedBypass;
    private transient float maxWalkSpeed;
    private transient float maxFlySpeed;
    private transient boolean allowNegativeSpeed;
    private transient boolean negativeSpeedPermission;
    private transient boolean minSpeedBypass;
    private transient float minWalkSpeed;
    private transient float minFlySpeed;

    public Settings(IBedrock plugin) {
        super(plugin, ConfigType.YML, "config");
    }

    @Override
    public void reloadSettings() {
        this.lang = LANG_FILE.getString();
        this.debugMode = DEBUG.getBoolean();
        this.gamemodeSpecificPermissions = GAMEMODE_SPECIFIC_PERMISSIONS.getBoolean();
        debug("Gamemode Specific Perms: " + gamemodeSpecificPermissions);

        //afk more info options
        this.afkMoreInfoEnabled = AFK_MORE_INFO.getBoolean();
        this.afkAwayMoreInfoMessage = afkMoreInfoEnabled && (AFK_AWAY_MORE_INFO_MESSAGE.get() == null || !AFK_AWAY_MORE_INFO_MESSAGE
                .get()
                .equals(NONE));
        this.afkAwayMoreInfoFormat = afkAwayMoreInfoMessage ? AFK_AWAY_MORE_INFO_MESSAGE.getString() : null;
        System.out.println(afkAwayMoreInfoFormat);
        debug("AFK More Info Enabled: " + afkMoreInfoEnabled);
        debug("AFK More Info Format: " + (afkAwayMoreInfoMessage ? "custom" : "default"));

        //afk back options
        this.hasAfkBackMessage = AFK_BACK_MESSAGE.get() == null || AFK_BACK_MESSAGE.get().equals(NONE);
        this.afkBackFormat = hasAfkBackMessage ? AFK_BACK_MESSAGE.getString() : null;
        debug("AFK Back Message: " + hasAfkBackMessage);

        //afk away options
        this.afkAwayMessage = AFK_AWAY_MESSAGE.get() == null || AFK_AWAY_MESSAGE.get().equals(NONE);
        this.afkAwayFormat = afkAwayMessage ? AFK_AWAY_MESSAGE.getString() : null;
        debug("AFK Away Message: " + afkAwayMessage);

        this.maxSaveHistory = BACK_SAVE_HISTORY_MAX.getInt();
        this.historyRecordLimit = BACK_MAX_RECORD.getInt();
        this.saveHistoryLog = BACK_SAVE_HISTORY.getBoolean();

        this.nickCharacterWhitelist = Pattern.compile(NICK_CHARACTER_WHITELIST.getString());
        this.nickInversedWhitelist = NICK_INVERSE_WHITELIST.getBoolean();
        this.allowNickColor = NICK_CHATCOLOR.getBoolean();
        this.nickColorPermission = allowNickColor && NICK_COLOR_PERMISSION.getBoolean();
        this.maxNickLength = NICK_MAXLENGTH.getInt();
        this.allowNickDuplicates = NICK_REUSE.getBoolean();
        this.nickDuplicatePermission = allowNickDuplicates && NICK_REUSE_PERMiSSION.getBoolean();

        this.speedSpecificPermissions = SPEED_SPECIFIC_PERMISSIONS.getBoolean();
        this.maxSpeedBypass = SPEED_BYPASS_MAX_PERMISSION.getBoolean();
        this.minSpeedBypass = SPEED_BYPASS_MIN_PERMISSION.getBoolean();
        this.allowNegativeSpeed = SPEED_ALLOW_NEGATIVE.getBoolean();
        this.negativeSpeedPermission = allowNegativeSpeed && SPEED_NEGATIVE_PERMISSION.getBoolean();
        this.minWalkSpeed = SPEED_MIN_WALK.getFloat();
        this.maxWalkSpeed = SPEED_MAX_WALK.getFloat();
        this.minFlySpeed = SPEED_MIN_FLY.getFloat();
        this.maxFlySpeed = SPEED_MAX_FLY.getFloat();

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
        return gamemodeSpecificPermissions;
    }

    @Override
    public boolean hasSpeedSpecificPermissions() {
        return speedSpecificPermissions;
    }

    @Override
    public boolean hasMaxSpeedBypass() {
        return maxSpeedBypass;
    }

    @Override
    public float getMaxWalkSpeed() {
        return maxWalkSpeed;
    }

    @Override
    public float getMaxFlySpeed() {
        return maxFlySpeed;
    }

    @Override
    public boolean allowNegativeSpeed() {
        return allowNegativeSpeed;
    }

    @Override
    public boolean hasNegativeSpeedPermission() {
        return negativeSpeedPermission;
    }

    @Override
    public boolean hasMinSpeedBypass() {
        return minSpeedBypass;
    }

    @Override
    public float getMinWalkSpeed() {
        return minWalkSpeed;
    }

    @Override
    public float getMinFlySpeed() {
        return minFlySpeed;
    }

    @Override
    public Pattern getNickCharacterWhitelist() {
        return nickCharacterWhitelist;
    }

    @Override
    public boolean hasNickInversedWhitelist() {
        return nickInversedWhitelist;
    }

    @Override
    public boolean allowNickColor() {
        return allowNickColor;
    }

    @Override
    public boolean useNickColorPermission() {
        return nickColorPermission;
    }

    @Override
    public int getMaxNickLength() {
        return maxNickLength;
    }

    @Override
    public boolean allowNickDuplicates() {
        return allowNickDuplicates;
    }

    @Override
    public boolean useNickDuplicatePermission() {
        return nickDuplicatePermission;
    }

    @Override
    public int getHistoryRecordLimit() {
        return historyRecordLimit;
    }

    @Override
    public boolean saveHistoryLog() {
        return saveHistoryLog;
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
        return afkAwayMessage;
    }

    @Override
    public String getAfkBackFormat() {
        return afkBackFormat;
    }

    @Override
    public boolean hasAfkBackMessage() {
        return hasAfkBackMessage;
    }

    @Override
    public String getAfkAwayMoreInfoFormat() {
        return afkAwayMoreInfoFormat;
    }

    @Override
    public boolean hasAfkAwayMoreInfoMessage() {
        return afkAwayMoreInfoMessage;
    }

    @Override
    public boolean isAfkMoreInfoEnabled() {
        return afkMoreInfoEnabled;
    }

}
