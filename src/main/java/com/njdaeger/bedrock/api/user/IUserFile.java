package com.njdaeger.bedrock.api.user;

import com.njdaeger.bcm.base.IConfig;
import com.njdaeger.bedrock.user.UserPath;
import org.bukkit.World;

public interface IUserFile extends IConfig {
    
    <T> T get(UserPath path);
    
    void setEntry(UserPath path, Object value);
    
    void addEntry(UserPath path, Object value);
    
    World getWorld(UserPath path);
    
}
