package com.njdaeger.bedrock.api.config;

import com.njdaeger.bcm.base.IConfig;
import com.njdaeger.bedrock.config.MessageFile;

public interface ISettings extends IConfig {
    
    MessageFile.Language getLanguage();
    
    boolean autoUpdate();
    
    boolean debug();
    
    String getRawMessageFormat();
    
    String getRawDefaultChannelFormat();
    
    String getMessageFormat();
    
    String getDefaultChannelFormat();
    
}
