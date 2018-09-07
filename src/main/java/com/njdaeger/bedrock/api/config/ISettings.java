package com.njdaeger.bedrock.api.config;

import com.njdaeger.bcm.base.IConfig;

public interface ISettings extends IConfig {
    
    String getLang();
    
    boolean isDebugMode();

    String getAfkJoinFormat();

    boolean hasAfkJoinMessage();

    String getAfkLeaveMessage();

    boolean hasAfkLeaveMessage();

    boolean isAfkMoreInfoEnabled();
    
}
