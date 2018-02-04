package com.njdaeger.bedrock.user;

import com.coalesce.core.session.AbstractSession;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.entity.Player;

public class User extends AbstractSession<Player> implements IUser {
    
    private boolean afk;
    private String displayName;
    private final String name;
    private final IUserFile userFile;
    
    public User(IBedrock sessionOwner, NamespacedSessionStore<IUser> namespace, String sessionKey, Player type) {
        super(sessionOwner, namespace, sessionKey, type);
        
        this.name = sessionKey;
    
        this.userFile = new UserFile(sessionOwner, this);
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
        this.afk = value;
    }
    
    @Override
    public String getDisplayName() {
        return get().getDisplayName();
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
