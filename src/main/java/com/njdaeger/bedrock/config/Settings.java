package com.njdaeger.bedrock.config;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.ISettings;

public class Settings extends Configuration implements ISettings {
    
    private boolean autoUpdate;
    private boolean debug;
    private String langFile;
    private String rawMessageFormat;
    private String rawChannelFormat;
    private String messageFormat;
    private String channelFormat;
    
    public Settings(IBedrock plugin) {
        super(plugin, ConfigType.YML, "config");
        
        addEntry("language-file", "messages-en-us");
        addEntry("auto-update", true);
        addEntry("debug", false);
        addEntry("messageFormat", "&7{DISPLAYNAME}:&r {MESSAGE}");
        addEntry("channelFormat", "&7[&2{DISPLAYTYPE}&7] &r{DISPLAYNAME}&7:&r {MESSAGE}");
        
        autoUpdate = getBoolean("auto-update");
        debug = getBoolean("debug");

        langFile = getString("language-file");
        
        messageFormat = getString("messageFormat");
        rawMessageFormat = messageFormat;
        rawMessageFormat = rawMessageFormat.replace("{MESSAGE}", "{0}");
        rawMessageFormat = rawMessageFormat.replace("{NAME}", "{1}");
        rawMessageFormat = rawMessageFormat.replace("{DISPLAYNAME}", "{2}");
        rawMessageFormat = rawMessageFormat.replace("{HEALTH}", "{3}");
        rawMessageFormat = rawMessageFormat.replace("{FOOD}", "{4}");
        rawMessageFormat = rawMessageFormat.replace("{WORLDNAME}", "{5}");
        
        channelFormat = getString("channelFormat");
        rawChannelFormat = channelFormat;
        rawChannelFormat = rawChannelFormat.replace("{MESSAGE}", "{0}");
        rawChannelFormat = rawChannelFormat.replace("{NAME}", "{1}");
        rawChannelFormat = rawChannelFormat.replace("{DISPLAYNAME}", "{2}");
        rawChannelFormat = rawChannelFormat.replace("{HEALTH}", "{3}");
        rawChannelFormat = rawChannelFormat.replace("{FOOD}", "{4}");
        rawChannelFormat = rawChannelFormat.replace("{WORLDNAME}", "{5}");
        rawChannelFormat = rawChannelFormat.replace("{CHANNELNAME}", "{6}");
        rawChannelFormat = rawChannelFormat.replace("{CHANNELPREFIX}", "{7}");
        rawChannelFormat = rawChannelFormat.replace("{DISPLAYTYPE}", "{8}");
        
    }
    
    
    @Override
    public String getLang() {
        return langFile;
    }
    
    @Override
    public boolean autoUpdate() {
        return autoUpdate;
    }
    
    @Override
    public boolean debug() {
        return debug;
    }
    
    @Override
    public String getRawMessageFormat() {
        return rawMessageFormat;
    }
    
    @Override
    public String getRawDefaultChannelFormat() {
        return rawChannelFormat;
    }
    
    @Override
    public String getMessageFormat() {
        return messageFormat;
    }
    
    @Override
    public String getDefaultChannelFormat() {
        return channelFormat;
    }
    
}
