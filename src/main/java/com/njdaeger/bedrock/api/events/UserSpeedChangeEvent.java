package com.njdaeger.bedrock.api.events;

import com.njdaeger.bedrock.SpeedType;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class UserSpeedChangeEvent extends Event implements Cancellable {
    
    private static final HandlerList handlerList = new HandlerList();
    private final double previous;
    private final IUser user;
    private boolean cancel;
    private SpeedType type;
    private double speed;
    
    public UserSpeedChangeEvent(IUser user, double speed, double previous, SpeedType type) {
        this.user = user;
        this.type = type;
        this.speed = speed;
        this.previous = previous;
    }
    
    public double getPrevious() {
        return previous;
    }
    
    public IUser getUser() {
        return user;
    }
    
    public double getNewSpeed() {
        return speed;
    }
    
    public void setNewSpeed(double newSpeed) {
        if (newSpeed > 10) newSpeed = 10;
        if (newSpeed < 0) newSpeed = 0;
        this.speed = newSpeed;
    }
    
    public SpeedType getType() {
        return type;
    }
    
    public void setType(SpeedType type) {
        this.type = type;
    }
    
    @Override
    public boolean isCancelled() {
        return cancel;
    }
    
    @Override
    public void setCancelled(boolean cancel) {
        this.cancel = cancel;
    }
    
    @Override
    public HandlerList getHandlers() {
        return handlerList;
    }
    
    public static HandlerList getHandlerList() {
        return handlerList;
    }
}
