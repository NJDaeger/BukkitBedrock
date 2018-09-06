package com.njdaeger.bedrock.user;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

import static com.njdaeger.bedrock.user.UserPath.*;

public class UserFile extends Configuration implements IUserFile {
    
    public UserFile(IBedrock plugin, IUser user) {
        super(plugin, ConfigType.YML,"users" + File.separator + user.getId().toString() + File.separator + "data");
        setEntry(NAME, user.getBase().getName());//no
        setEntry(AFK, false);//no
        addEntry(DISPLAYNAME, user.getBase().getDisplayName());
        addEntry(WALKSPEED, Math.floor(user.getBase().getWalkSpeed()*-8)/(-1.8+user.getBase().getWalkSpeed()));
        addEntry(FLYSPEED, user.getBase().getFlySpeed()*10);
        addEntry(GAMEMODE, user.getBase().getGameMode().toString());
        addEntry(INFOBOARD, false);
    }
    
    @Override
    public <T> T get(UserPath path) {
        return getValueAs(path.getKey(), path.getType());
    }
    
    @Override
    public World getWorld(UserPath path) {
        return Bukkit.getWorld(getString(path.getKey()));
    }
    
    @Override
    public void setEntry(UserPath path, Object value) {
        setEntry(path.getKey(), value);
    }
    
    @Override
    public void addEntry(UserPath path, Object value) {
        addEntry(path.getKey(), value);
    }
    
}
