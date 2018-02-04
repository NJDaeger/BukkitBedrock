package com.njdaeger.bedrock.user;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;

import java.io.File;

public class UserFile extends YmlConfig implements IUserFile {
    
    public UserFile(IBedrock plugin, IUser user) {
        super("users" + File.separator + user.getName() + File.separator + "data", plugin);
        setEntry("name", user.getName());
        addEntry("displayname", user.getDisplayName());
    }
}
