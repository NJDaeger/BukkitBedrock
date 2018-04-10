package com.njdaeger.bedrock.api.config;

import com.njdaeger.bedrock.config.MessageFile;

public interface ISettings extends com.coalesce.core.config.base.IConfig {
    
    MessageFile.Language getLanguage();
    
    boolean autoUpdate();
    
    boolean debug();
    
    String getRawMessageFormat();
    
    String getRawDefaultChannelFormat();
    
    String getMessageFormat();
    
    String getDefaultChannelFormat();
    
}
