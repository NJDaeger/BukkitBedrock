package com.njdaeger.bedrock.config;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bcm.base.ISection;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.ISettings;

import java.util.function.BiFunction;

import static com.njdaeger.bedrock.api.Bedrock.debug;
import static com.njdaeger.bedrock.config.Settings.Setting.*;

public class Settings extends Configuration implements ISettings {

    private static final String NONE = "%none%";

    private transient boolean hasSpeedSpecificPerms;
    private transient boolean hasMaxSpeedBypass;
    private transient float maxWalkSpeed;
    private transient float maxFlySpeed;
    private transient boolean hasGamemodeSpecificPerms;
    private transient boolean hasAfkAwayMessage;
    private transient boolean hasAfkBackMessage;
    private transient boolean hasAfkAwayMoreInfoMessage;
    private transient boolean isAfkMoreInfoEnabled;
    private transient String afkAwayMoreInfoFormat;
    private transient String afkAwayFormat;
    private transient String afkBackFormat;
    private transient String langFile;
    private transient boolean debug;
    
    public Settings(IBedrock plugin) {
        super(plugin, ConfigType.YML, "config");
    }

    @Override
    public void reloadSettings() {
        this.langFile = LANG_FILE.getString();
        this.debug = DEBUG.getBoolean();

        //afk more info options
        this.isAfkMoreInfoEnabled = AFK_MORE_INFO.getBoolean();
        this.hasAfkAwayMoreInfoMessage = isAfkMoreInfoEnabled && (AFK_AWAY_MORE_INFO_MESSAGE.get() == null || !AFK_AWAY_MORE_INFO_MESSAGE.get().equals(NONE));
        this.afkAwayMoreInfoFormat = hasAfkAwayMoreInfoMessage ? AFK_AWAY_MORE_INFO_MESSAGE.getString() : null;
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

        this.hasGamemodeSpecificPerms = GAMEMODE_SPECIFIC_PERMISSIONS.getBoolean();
        debug("Gamemode Specific Perms: " + hasGamemodeSpecificPerms);

        this.hasSpeedSpecificPerms = SPEED_SPECIFIC_PERMISSIONS.getBoolean();
        this.hasMaxSpeedBypass = SPEED_BYPASS_MAX_PERMISSION.getBoolean();
        this.maxWalkSpeed = SPEED_MAX_WALK.getFloat();
        this.maxFlySpeed = SPEED_MAX_FLY.getFloat();

    }

    @Override
    public String getLang() {
        return langFile;
    }
    
    @Override
    public boolean isDebugMode() {
        return debug;
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

    @Override
    public boolean hasGamemodeSpecificPermissions() {
        return hasGamemodeSpecificPerms;
    }

    @Override
    public boolean hasSpeedSpecificPermissions() {
        return false;
    }

    @Override
    public boolean hasMaxSpeedBypass() {
        return false;
    }

    @Override
    public float getMaxWalkSpeed() {
        return 0;
    }

    @Override
    public float getMaxFlySpeed() {
        return 0;
    }

    enum Setting {

        AFK_MORE_INFO("afk.allow-more-info", false),
        AFK_BACK_MESSAGE("afk.back-format"),
        AFK_AWAY_MESSAGE("afk.away-format"),
        AFK_AWAY_MORE_INFO_MESSAGE("afk.away-more-info-format"),

        GAMEMODE_SPECIFIC_PERMISSIONS("gamemode.gamemode-specific-permissions", true),

        SPEED_SPECIFIC_PERMISSIONS("speed.speed-type-permissions", true),
        SPEED_BYPASS_MAX_PERMISSION("speed.allow-max-speed-bypass-permission", false),
        SPEED_MAX_WALK("speed.max-walk-speed", 10.),
        SPEED_MAX_FLY("speed.max-fly-speed", 10.),

        DEBUG("debug", false),
        LANG_FILE("language-file", "messages-en-us");

        private final String key;
        private final Object defVal;

        Setting(String key) {
            this.key = key;
            this.defVal = null;
        }

        Setting(String key, Object defVal) {
            this.key = key;
            this.defVal = defVal;
        }

        private <T> T get(BiFunction<ISettings, String, T> function) {
            ISettings settings = Bedrock.getSettings();
            if (settings.contains(key)) {
                return function.apply(settings, key);
            }
            return (T)defVal;
        }

        private Object get() {
            return get(ISection::getValue);
        }

        private Float getFloat() {
            return get(ISettings::getFloat);
        }

        private String getString() {
            return get(ISettings::getString);
        }

        private boolean getBoolean() {
            return get(ISettings::getBoolean);
        }


    }

}
