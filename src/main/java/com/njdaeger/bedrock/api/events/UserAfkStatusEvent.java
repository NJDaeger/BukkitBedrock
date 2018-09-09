package com.njdaeger.bedrock.api.events;

import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserAfkStatusEvent extends Event implements Cancellable{
    
    private static final HandlerList handlers = new HandlerList();
    private boolean canceled = false;
    private String afkMessage;
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
    
    /**
     * The message which is sent to the server when the user changes afk status
     * @return The afk status message
     */
    public String getMessage() {
        return this.afkMessage;
    }
    
    /**
     * Set the afk status message
     * @param message The new afk status message
     */
    public void setMessage(String message) {
        this.afkMessage = message;
    }
    
    /**
     * Check if the user went afk or not.
     * @return The afk status
     */
    public boolean isAfk() {
        return this.status;
    }
    
    /**
     * Return the user which changed afk status
     * @return The user
     */
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
