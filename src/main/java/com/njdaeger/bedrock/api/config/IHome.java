package com.njdaeger.bedrock.api.config;

import com.coalesce.core.config.base.IConfig;
import com.njdaeger.bedrock.api.user.IUser;

public interface IHome extends Locatable, IConfig {
    
    IUser getOwner();
    
    void sendHere(IUser user);
    
}
