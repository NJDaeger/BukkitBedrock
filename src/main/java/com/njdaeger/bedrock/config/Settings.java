package com.njdaeger.bedrock.config;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.ISettings;

import static com.njdaeger.bedrock.config.Settings.Setting.*;

public class Settings extends Configuration implements ISettings {

    private static final String NONE = "%none%";
    private final transient boolean debug;
    private final transient String langFile;
    
    public Settings(IBedrock plugin) {
        super(plugin, ConfigType.YML, "config");

        this.langFile = (String)LANG_FILE.get();
        this.debug = (boolean)DEBUG.get();


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
    public String getAfkJoinFormat() {
        return null;
    }

    @Override
    public boolean hasAfkJoinMessage() {
        return AFK_JOIN_MESSAGE.get() != null && !AFK_JOIN_MESSAGE.get().equals(NONE);
    }

    @Override
    public String getAfkLeaveMessage() {
        return null;
    }

    @Override
    public boolean hasAfkLeaveMessage() {
        return AFK_LEAVE_MESSAGE.get() != null && !AFK_LEAVE_MESSAGE.get().equals(NONE);
    }

    @Override
    public boolean isAfkMoreInfoEnabled() {
        return false;
    }

    //Any settings which dont have a default value means the setting can be commented out.
    enum Setting {

        AFK_LEAVE_MESSAGE("afk.leave-message"),
        AFK_JOIN_MESSAGE("afk.join-message"),
        DEBUG("debug", false),
        LANG_FILE("language-file", "messages-en-us");

        private final String key;
        private final Object defVal;

        Setting(String key, Object... defVal) {
            this.key = key;
            this.defVal = defVal;
        }

        private Object getDefault() {
            return defVal;
        }

        private Object get() {
            Object val =  Bedrock.getSettings().getValue(key);
            return val == null ? defVal : val;
        }

    }

}
