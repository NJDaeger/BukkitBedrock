package com.njdaeger.bedrock.api.events;

import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserAfkStatusEvent extends Event implements Cancellable{
    
    private static final HandlerList handlers = new HandlerList();
    private String afkMessage;
    private boolean canceled;
    private boolean status;
    private IUser user;
    
    public UserAfkStatusEvent(IUser user, boolean status, String message) {
        this.user = user;
        this.status = status;
        this.afkMessage = message;
    }

    @Override
    public boolean isCancelled() {
        return this.canceled;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
        this.canceled = cancel;
    }
    
    public String getMessage() {
        return this.afkMessage;
    }
    
    public void setMessage(String message) {
        this.afkMessage = message;
    }
    
    public boolean isAfk() {
        return this.status;
    }
    
    public IUser getUser() {
        return user;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlers;
    }
    
    public static HandlerList getHandlerList() {
        return handlers;
    }
    
}
