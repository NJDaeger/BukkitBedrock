package com.njdaeger.bedrock.settings;

import com.njdaeger.bcm.base.ISection;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.settings.ISettings;

import java.util.function.BiFunction;

enum Key {

    AFK_MORE_INFO("afk.allow-more-info", false),
    AFK_BACK_MESSAGE("afk.back-format"),
    AFK_AWAY_MESSAGE("afk.away-format"),
    AFK_AWAY_MORE_INFO_MESSAGE("afk.away-more-info-format"),

    GAMEMODE_SPECIFIC_PERMISSIONS("gamemode.gamemode-specific-permissions", true),


    SPEED_SPECIFIC_PERMISSIONS("speed.speed-type-permissions", true),
    SPEED_BYPASS_MAX_PERMISSION("speed.max-speed-bypass-permission", false),
    SPEED_BYPASS_MIN_PERMISSION("speed.min-speed-bypass-permission", false),
    SPEED_MAX_WALK("speed.max-walk-speed", 10.F),
    SPEED_MIN_WALK("speed.min-walk-speed", -10.F),
    SPEED_MAX_FLY("speed.max-fly-speed", 10.F),
    SPEED_MIN_FLY("speed.min-fly-speed", -10.F),
    SPEED_ALLOW_NEGATIVE("speed.allow-negative-speed", true),
    SPEED_NEGATIVE_PERMISSION("speed.negative-permission", true),

    NICK_CHARACTER_WHITELIST("nick.character-whitelist"),
    NICK_INVERSE_WHITELIST("nick.inverse-whitelist", false),
    NICK_CHATCOLOR("nick.allow-chatcolor", true),
    NICK_COLOR_PERMISSION("nick.chatcolor-permission", true),
    NICK_MAXLENGTH("nick.maxlength", 16),
    NICK_REUSE("nick.allow-reuse", false),
    NICK_REUSE_PERMiSSION("nick.reuse-permission", true),

    BACK_MAX_RECORD("back.history-max-record", 20),
    BACK_SAVE_HISTORY("back.save-history", true),
    BACK_SAVE_HISTORY_MAX("back.save-history-limit", 10),

    DEBUG("debug", false),
    LANG_FILE("language-file", "messages-en-us");

    private final String key;
    private final Object defVal;

    Key(String key) {
        this.key = key;
        this.defVal = null;
    }

    Key(String key, Object defVal) {
        this.key = key;
        this.defVal = defVal;
    }

    <T> T get(BiFunction<Settings, String, T> function) {
        Settings settings = (Settings)Bedrock.getSettings();
        if (settings.contains(key)) {
            return function.apply(settings, key);
        }
        return (T)defVal;
    }

    Object get() {
        return get(Settings::getValue);
    }

    Integer getInt() {
        return get(Settings::getInt);
    }

    Float getFloat() {
        return get(Settings::getFloat);
    }

    String getString() {
        return get(Settings::getString);
    }

    boolean getBoolean() {
        return get(Settings::getBoolean);
    }

}
