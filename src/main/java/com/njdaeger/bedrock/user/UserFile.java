package com.njdaeger.bedrock.user;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;

import java.io.File;

public class UserFile extends YmlConfig implements IUserFile {
    
    public UserFile(IBedrock plugin, IUser user) {
        super("users" + File.separator + user.getName() + File.separator + "data", plugin);
        setEntry("name", user.get().getName());//no
        setEntry("afk", false);//no
        addEntry("displayname", user.get().getDisplayName());
        addEntry("walkspeed", Math.floor(user.get().getWalkSpeed()*-8)/(-1.8+user.get().getWalkSpeed()));
        addEntry("flyspeed", user.get().getFlySpeed()*10);
        addEntry("gamemode", user.get().getGameMode().toString());
    }
}
