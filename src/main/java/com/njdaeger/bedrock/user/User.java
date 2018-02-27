package com.njdaeger.bedrock.user;

import com.coalesce.core.Color;
import com.coalesce.core.scoreboard.StaticScoreboard;
import com.coalesce.core.session.AbstractSession;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Gamemode;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.SpeedType;
import com.njdaeger.bedrock.api.config.IHome;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.events.UserSpeedChangeEvent;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import com.njdaeger.bedrock.config.Home;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Bed;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.njdaeger.bedrock.api.Bedrock.debug;
import static com.njdaeger.bedrock.api.Bedrock.translate;
import static com.njdaeger.bedrock.api.Message.*;
import static com.njdaeger.bedrock.user.UserPath.*;

public class User extends AbstractSession<Player> implements IUser {
    
    private boolean afk;
    private double flySpeed;
    private double walkSpeed;
    private final String name;
    private boolean infoBoard;
    private Gamemode gamemode;
    private String displayName;
    private BukkitTask infotask;
    private Location afkLocation;
    private Location lastLocation;
    private final IBedrock bedrock;
    private final IUserFile userFile;
    private final Map<String, IHome> homes;
    
    public User(IBedrock bedrock, NamespacedSessionStore<IUser> namespace, String sessionKey, Player type) {
        super(bedrock, namespace, sessionKey, type);
        
        this.name = sessionKey;
        this.bedrock = bedrock;
        this.homes = new HashMap<>();
        
        this.userFile = new UserFile(bedrock, this);
        this.userFile.create();
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public UUID getId() {
        return get().getUniqueId();
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
    public Collection<IHome> getHomes() {
        return homes.values();
    }
    
    @Override
    public IHome getHome(String name) {
        return homes.get(name);
    }
    
    @Override
    public boolean createHome(String name) {
        name = name.toLowerCase();
        if (homes.containsKey(name)) return false;
        IHome home = new Home(bedrock, this, name);
        homes.put(name, home);
        return true;
    }
    
    @Override
    public boolean deleteHome(String name) {
        name = name.toLowerCase();
        return homes.containsKey(name) && homes.remove(name).delete();
    }
    
    @Override
    public boolean hasInfobard() {
        return infoBoard;
    }
    
    @Override
    public void runInfobard(boolean value) {
        this.infoBoard = value;
        
        if (value) {
            StaticScoreboard scoreboard = new StaticScoreboard();
            infotask = new BukkitRunnable() {
                @Override
                public void run() {
                    scoreboard.setTitle(Color.GREEN + "Server Info");
                    
                    scoreboard.removeAll();
                    scoreboard.setLine(0);
                    scoreboard.setLine(1, translate(INFOBOARD_RAM_PERCENT, Bedrock.getMemUsage()));
                    scoreboard.setLine(2, translate(INFOBOARD_RAM_MEGABYTES, Bedrock.getAllocatedMemory(), Bedrock.getMaxMemory()));
                    scoreboard.setLine(3);
                    scoreboard.setLine(4, translate(INFOBOARD_CPU, Bedrock.getCPULoad()));
                    scoreboard.setLine(5);
                    scoreboard.setLine(6, translate(INFOBOARD_TPS, Bedrock.getTPS()));
                    scoreboard.setLine(7);
                    
                    scoreboard.send(get());
                }
            }.runTaskTimer(bedrock, 0L, 10); // Set timer*/
        } else {
            if (infotask != null) infotask.cancel();
            get().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }
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
        return this.afkLocation;
    }
    
    @Override
    public Location getLocation() {
        return get().getLocation();
    }
    
    @Override
    public Location getLastLocation() {
        return this.lastLocation;
    }
    
    @Override
    public void setLastLocation(Location location) {
        this.lastLocation = location;
    }
    
    @Override
    public String getDisplayName() {
        return this.displayName;
    }
    
    @Override
    public void setDisplayName(String name) {
        this.displayName = Color.translate('&', name);
        get().setDisplayName(displayName);
    }
    
    @Override
    public void login() {

        long start = System.currentTimeMillis();
        
        File homesDir = new File(getUserDirectory() + File.separator + "homes");
        if (homesDir.exists() && homesDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(homesDir.listFiles())) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                homes.put(name.toLowerCase(), new Home(bedrock, this, name.toLowerCase()));
            }
        }
        
        userFile.setEntry(NAME, get().getName());//no
        userFile.addEntry(AFK, false);
        userFile.addEntry(DISPLAYNAME, get().getDisplayName());
        userFile.addEntry(WALKSPEED, Math.floor(get().getWalkSpeed()*-8)/(-1.8+get().getWalkSpeed()));
        userFile.addEntry(FLYSPEED, get().getFlySpeed()*10);
        userFile.addEntry(GAMEMODE, get().getGameMode().toString());
        userFile.addEntry(INFOBOARD, false);
        
        userFile.addEntry(LASTX, get().getLocation().getX());
        userFile.addEntry(LASTY, get().getLocation().getY());
        userFile.addEntry(LASTZ, get().getLocation().getZ());
        userFile.addEntry(LASTYAW, get().getLocation().getYaw());
        userFile.addEntry(LASTPITCH, get().getLocation().getPitch());
        userFile.addEntry(LASTWORLD, get().getLocation().getWorld().getName());
        
        this.afk = false;
        this.displayName = userFile.get(DISPLAYNAME);
        this.walkSpeed = userFile.get(WALKSPEED);
        this.flySpeed = userFile.get(FLYSPEED);
        this.gamemode = Gamemode.valueOf(userFile.get(GAMEMODE));
        this.infoBoard = userFile.get(INFOBOARD);
        this.lastLocation = new Location(
                userFile.getWorld(LASTWORLD) == null ? get().getWorld() : userFile.getWorld(LASTWORLD),
                userFile.get(LASTX),
                userFile.get(LASTY),
                userFile.get(LASTZ),
                userFile.get(LASTYAW),
                userFile.get(LASTPITCH));
        
        get().setDisplayName(displayName);
        get().setWalkSpeed(Float.parseFloat(Double.toString(0.2 * Math.pow(walkSpeed, 0.69897))));
        get().setFlySpeed((float)flySpeed/10);
        get().setGameMode(gamemode.getBukkitMode());
        runInfobard(infoBoard);
        
        
        long finish = System.currentTimeMillis();
        debug("User " + getName() + " loaded in " + (finish-start) + " milliseconds.");
        
    }
    
    @Override
    public void logout() {
        userFile.setEntry(NAME, getSessionKey());
        userFile.setEntry(AFK, false);
        userFile.setEntry(DISPLAYNAME, displayName);
        userFile.setEntry(WALKSPEED, walkSpeed);
        userFile.setEntry(FLYSPEED, flySpeed);
        userFile.setEntry(GAMEMODE, gamemode.toString());
    }
}
