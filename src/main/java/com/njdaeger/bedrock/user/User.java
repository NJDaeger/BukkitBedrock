package com.njdaeger.bedrock.user;

import com.coalesce.core.session.AbstractSession;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.Objects;

public class User extends AbstractSession<Player> implements IUser {
    
    private boolean afk;
    private String displayName;
    private final String name;
    private final IBedrock bedrock;
    private final IUserFile userFile;
    
    public User(IBedrock bedrock, NamespacedSessionStore<IUser> namespace, String sessionKey, Player type) {
        super(bedrock, namespace, sessionKey, type);
        
        this.name = sessionKey;
        this.bedrock = bedrock;
        
        this.userFile = new UserFile(bedrock, this);
        this.userFile.create();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public IUserFile getDataFile() {
        return userFile;
    }
    
    @Override
    public boolean isAfk() {
        return this.afk;
    }
    
    @Override
    public void setAfk(boolean value, String message) {
        
        UserAfkStatusEvent event = new UserAfkStatusEvent(this, value, message);
        Bukkit.getPluginManager().callEvent(event);
        
        //Check if the event was cancelled
        if (event.isCancelled()) return;
        
        Bukkit.broadcastMessage(event.getMessage());
        
        this.afk = value;
    }
    
    @Override
    public String getDisplayName() {
        if (!Objects.equals(this.displayName, get().getDisplayName())) setDisplayName(name);
        return this.displayName;
    }
    
    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
        get().setDisplayName(name);
    }
    
    @Override
    public void login() {
        userFile.setEntry("name", get().getName());
        userFile.setEntry("afk", false);
        userFile.addEntry("displayname", get().getDisplayName());
    }
    
    @Override
    public void logout() {
    
    }
}
