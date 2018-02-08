package com.njdaeger.bedrock.user;

import com.coalesce.core.session.AbstractSession;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.Gamemode;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.Objects;

public class User extends AbstractSession<Player> implements IUser {
    
    private boolean afk;
    private final String name;
    private Gamemode gamemode;
    private String displayName;
    private Location afkLocation;
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
    public IBedrock getSessionOwner() {
        return bedrock;
    }
    
    @Override
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
        get().setGameMode(GameMode.valueOf(gamemode.getNicename().toUpperCase()));
    }
    
    @Override
    public Gamemode getGamemode() {
        return gamemode;
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
        
        if (value) {
            this.afkLocation = getLocation();
        } else this.afkLocation = null;
        
        Bukkit.broadcastMessage(event.getMessage());
        
        this.afk = value;
    }
    
    @Override
    public Location getAfkLocation() {
        return afkLocation;
    }
    
    @Override
    public Location getLocation() {
        return get().getLocation();
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
