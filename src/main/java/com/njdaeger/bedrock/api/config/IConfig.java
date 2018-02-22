package com.njdaeger.bedrock.api.config;

import com.njdaeger.bedrock.MessageFile;

public interface IConfig extends com.coalesce.core.config.base.IConfig {
    
    MessageFile.Language getLanguage();
    
    boolean autoUpdate();
    
    boolean debug();
    
}
