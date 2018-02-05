package com.njdaeger.bedrock;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.IConfig;

public class Config extends YmlConfig implements IConfig {
    
    public Config(IBedrock plugin) {
        super("config", plugin);
        
        addEntry("language", "en_us");
        addEntry("auto-update", true);
    }
    
    
    @Override
    public MessageFile.Language getLanguage() {
        return MessageFile.Language.valueOf(getString("language").toUpperCase());
    }
    
    @Override
    public boolean autoUpdate() {
        return getBoolean("auto-update");
    }
}
