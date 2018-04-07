package com.njdaeger.bedrock.config;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.ISettings;

public class Settings extends YmlConfig implements ISettings {
    
    public Settings(IBedrock plugin) {
        super("config", plugin);
        
        addEntry("language", "en_us");
        addEntry("auto-update", true);
        addEntry("debug", false);
    }
    
    
    @Override
    public MessageFile.Language getLanguage() {
        return MessageFile.Language.valueOf(getString("language").toUpperCase());
    }
    
    @Override
    public boolean autoUpdate() {
        return getBoolean("auto-update");
    }
    
    @Override
    public boolean debug() {
        return getBoolean("debug");
    }
}
