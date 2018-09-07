package com.njdaeger.bedrock.api.config;

import com.njdaeger.bcm.base.IConfig;

public interface ISettings extends IConfig {
    
    String getLang();
    
    boolean autoUpdate();
    
    boolean debug();
    
    String getRawMessageFormat();
    
    String getRawDefaultChannelFormat();
    
    String getMessageFormat();
    
    String getDefaultChannelFormat();
    
}
