package com.njdaeger.bedrock.user;

import com.coalesce.core.session.AbstractSession;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.Gamemode;
import com.njdaeger.bedrock.SpeedType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.events.UserSpeedChangeEvent;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class User extends AbstractSession<Player> implements IUser {
    
    private boolean afk;
    private double flySpeed;
    private double walkSpeed;
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
        get().setGameMode(gamemode.getBukkitMode());
    }
    
    @Override
    public Gamemode getGamemode() {
        return gamemode;
    }
    
    @Override
    public void setSpeed(SpeedType type, double speed) {
    
        UserSpeedChangeEvent event = new UserSpeedChangeEvent(this, speed, (type == SpeedType.FLYING ? getFlySpeed() : getWalkSpeed()), type);
        Bukkit.getPluginManager().callEvent(event);
        
        if (event.isCancelled()) return;
        
        type = event.getType();
        speed = event.getNewSpeed();
        
        if (type == SpeedType.FLYING) {
            this.flySpeed = speed;
            get().setFlySpeed(Float.parseFloat(Double.toString(speed/10)));
        } else {
            this.walkSpeed = speed;
            float walkSpeedF = Float.parseFloat(Double.toString(0.2 * Math.pow(speed, 0.69897)));
            get().setWalkSpeed(walkSpeedF);
        }
    }
    
    @Override
    public void setSpeed(double speed) {
        setSpeed(getMovementType(), speed);
    }
    
    @Override
    public SpeedType getMovementType() {
        return get().isFlying() ? SpeedType.FLYING :  SpeedType.WALKING;
    }
    
    @Override
    public double getFlySpeed() {
        return flySpeed;
    }
    
    @Override
    public double getWalkSpeed() {
        return walkSpeed;
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
        return this.displayName;
    }
    
    @Override
    public void setDisplayName(String name) {
        this.displayName = name;
        get().setDisplayName(name);
    }
    
    @Override
    public void login() {
        userFile.setEntry("name", get().getName());//no
        userFile.setEntry("afk", false);//no
        userFile.addEntry("displayname", get().getDisplayName());
        userFile.addEntry("walkspeed", Math.floor(get().getWalkSpeed()*-8)/(-1.8+get().getWalkSpeed()));
        userFile.addEntry("flyspeed", get().getFlySpeed()*10);
        userFile.addEntry("gamemode", get().getGameMode().toString());
        
        this.afk = false;
        this.displayName = userFile.getString("displayname");
        this.walkSpeed = userFile.getDouble("walkspeed");
        this.flySpeed = userFile.getDouble("flyspeed");
        this.gamemode = Gamemode.valueOf(userFile.getString("gamemode"));
    
        get().setDisplayName(displayName);
        get().setWalkSpeed(Float.parseFloat(Double.toString(0.2 * Math.pow(walkSpeed, 0.69897))));
        get().setFlySpeed((float)flySpeed/10);
        get().setGameMode(gamemode.getBukkitMode());
    }
    
    @Override
    public void logout() {
        userFile.setEntry("name", getSessionKey());
        userFile.setEntry("afk", false);
        userFile.setEntry("displayname", displayName);
        userFile.setEntry("walkspeed", walkSpeed);
        userFile.setEntry("flyspeed", flySpeed);
        userFile.setEntry("gamemode", gamemode.toString());
    }
}
