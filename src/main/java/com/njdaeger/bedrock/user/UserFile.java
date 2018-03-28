package com.njdaeger.bedrock.user;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.File;

import static com.njdaeger.bedrock.user.UserPath.*;

public class UserFile extends YmlConfig implements IUserFile {
    
    public UserFile(IBedrock plugin, IUser user) {
        super("users" + File.separator + user.getId().toString() + File.separator + "data", plugin);
        setEntry(NAME, user.get().getName());//no
        setEntry(AFK, false);//no
        addEntry(DISPLAYNAME, user.get().getDisplayName());
        addEntry(WALKSPEED, Math.floor(user.get().getWalkSpeed()*-8)/(-1.8+user.get().getWalkSpeed()));
        addEntry(FLYSPEED, user.get().getFlySpeed()*10);
        addEntry(GAMEMODE, user.get().getGameMode().toString());
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
