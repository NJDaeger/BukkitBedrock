package com.njdaeger.bedrock.user;

import com.njdaeger.bedrock.api.Gamemode;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.SpeedType;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.config.IHome;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.events.UserSpeedChangeEvent;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.api.user.IUserFile;
import com.njdaeger.bedrock.config.Home;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitTask;

import java.io.File;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;

import static com.njdaeger.bedrock.api.Bedrock.debug;
import static com.njdaeger.bedrock.user.UserPath.*;

public final class User implements IUser {
    
    private boolean afk;
    private float flySpeed;
    private float walkSpeed;
    private final String name;
    private boolean infoBoard;
    private Gamemode gamemode;
    private String displayName;
    private BukkitTask infotask;
    private Location afkLocation;
    private Location lastLocation;
    private final IBedrock bedrock;
    private boolean channelDisplay;
    private IChannel currentChannel;
    private final IUserFile userFile;
    //private StaticScoreboard chanDisplay;
    private final Map<String, IHome> homes;
    private final LinkedList<IChannel> channels;
    
    private final Player player;
    
    public User(IBedrock bedrock, Player player) {
        this.bedrock = bedrock;
        this.name = player.getName();
        this.player = player;
        this.homes = new HashMap<>();
        this.channels = new LinkedList<>();
        
        this.userFile = new UserFile(bedrock, this);
    }
    
    @Override
    public Player getBase() {
        return player;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public UUID getId() {
        return getBase().getUniqueId();
    }
    
    @Override
    public IBedrock getPlugin() {
        return bedrock;
    }
    
    @Override
    public void setGamemode(Gamemode gamemode) {
        this.gamemode = gamemode;
        getBase().setGameMode(gamemode.getBukkitMode());
    }
    
    @Override
    public Gamemode getGamemode() {
        return gamemode;
    }
    
    @Override
    public void setSpeed(SpeedType type, float speed) {
    
        UserSpeedChangeEvent event = new UserSpeedChangeEvent(this, speed, (type == SpeedType.FLYING ? getFlySpeed() : getWalkSpeed()), type);
        Bukkit.getPluginManager().callEvent(event);
        
        if (event.isCancelled()) return;
        
        type = event.getType();
        speed = event.getNewSpeed();

        if (type == SpeedType.FLYING) {
            this.flySpeed = speed;
            getBase().setFlySpeed(speed/10);
        } else {
            this.walkSpeed = speed;
            float walkSpeedF = Float.parseFloat(Double.toString(0.2 * Math.pow(speed, 0.69897)));
            getBase().setWalkSpeed(walkSpeedF);
        }
    }
    
    @Override
    public void setSpeed(float speed) {
        setSpeed(getMovementType(), speed);
    }
    
    @Override
    public SpeedType getMovementType() {
        return getBase().isFlying() ? SpeedType.FLYING :  SpeedType.WALKING;
    }
    
    @Override
    public float getFlySpeed() {
        return flySpeed;
    }
    
    @Override
    public float getWalkSpeed() {
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
        /*this.infoBoard = value;
        
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
            }.runTaskTimer(bedrock, 0L, 10); // Set timer
        } else {
            if (infotask != null) infotask.cancel();
            getBase().setScoreboard(Bukkit.getScoreboardManager().getNewScoreboard());
        }*/
    }
    
    @Override
    public IChannel getSelectedChannel() {
        return this.currentChannel;
    }
    
    @Override
    public void setSelectedChannel(IChannel channel) {
        if (!hasChannel(channel)) addChannel(channel);
        channels.remove(channel);
        channels.addFirst(channel);
        updateChannelBoard();
        this.currentChannel = channel;
    }
    
    @Override
    public boolean hasChannelDisplay() {
        return this.channelDisplay;
    }
    
    @Override
    public void runChannelDisplay(boolean value) {
        /*this.channelDisplay = value;
        this.chanDisplay = new StaticScoreboard();
        if (value) {
            chanDisplay.setTitle(Color.GREEN + "Chat Channels");
            chanDisplay.setLine(0);
            if (channels.isEmpty()) {
                chanDisplay.send(get());
            }
            else updateChannelBoard();
        }*/
    }
    
    @Override
    public List<IChannel> getChannels() {
        return this.channels;
    }
    
    @Override
    public void addChannel(IChannel channel) {
        if (channel == null) return;
        if (!channels.contains(channel)) channels.add(0, channel);
        else {
            channels.remove(channel);
            channels.add(0, channel);
        }
        if (!channel.hasUser(this)) channel.addUser(this);
        if (getSelectedChannel() == null || !getSelectedChannel().equals(channel)) setSelectedChannel(channel);
        if (hasChannelDisplay()) updateChannelBoard();
    }
    
    @Override
    public void leaveChannel(IChannel channel) {
        channels.remove(channel);
        if (getSelectedChannel().equals(channel)) setSelectedChannel(channels.get(0));
        if (channel.hasUser(this)) channel.kickUser(this);
        if (hasChannelDisplay()) updateChannelBoard();
    }
    
    @Override
    public IChannel getChannel(String name) {
        return channels.stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    private void updateChannelBoard() {
        /*chanDisplay.removeAll();
        chanDisplay.setLine(0);
        channels.forEach(c -> chanDisplay.addLine(c.getName()));
        chanDisplay.send(get());*/
    }
    
    @Override
    public IUserFile getDataFile() {
        return this.userFile;
    }
    
    @Override
    public boolean isAfk() {
        return this.afk;
    }
    
    @Override
    public void setAfk(boolean value, boolean hasReason, String message) {

        if (value) {
            if ((hasReason && !bedrock.getSettings().hasAfkAwayMoreInfoMessage()) || !bedrock.getSettings().hasAfkAwayMessage()) message = null;
        } else {
            if (!bedrock.getSettings().hasAfkBackMessage()) message = null;
        }

        UserAfkStatusEvent event = new UserAfkStatusEvent(this, value, message);
        Bukkit.getPluginManager().callEvent(event);
        
        //Check if the event was cancelled
        if (event.isCancelled()) return;

        this.afkLocation = value ? getLocation() : null;

        if (event.getMessage() != null) Bukkit.broadcastMessage(event.getMessage());
        
        this.afk = value;
    }
    
    @Override
    public Location getAfkLocation() {
        return this.afkLocation;
    }
    
    @Override
    public Location getLocation() {
        return getBase().getLocation();
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
        this.displayName = ChatColor.translateAlternateColorCodes('&', name);
        getBase().setDisplayName(displayName);
    }
    
    @Override
    public IUser login() {

        long start = System.currentTimeMillis();
        
        File homesDir = new File(getUserDirectory() + File.separator + "homes");
        if (homesDir.exists() && homesDir.listFiles() != null) {
            for (File file : Objects.requireNonNull(homesDir.listFiles())) {
                String name = file.getName().substring(0, file.getName().lastIndexOf("."));
                homes.put(name.toLowerCase(), new Home(bedrock, this, name.toLowerCase()));
            }
        }
        
        userFile.setEntry(NAME, getBase().getName());//no
        userFile.addEntry(AFK, false);
        userFile.addEntry(DISPLAYNAME, getBase().getDisplayName());
        userFile.addEntry(WALKSPEED, Math.floor(getBase().getWalkSpeed()*-8)/(-1.8+getBase().getWalkSpeed()));
        userFile.addEntry(FLYSPEED, getBase().getFlySpeed()*10);
        userFile.addEntry(GAMEMODE, getBase().getGameMode().toString());
        userFile.addEntry(INFOBOARD, false);
        
        userFile.addEntry(LASTX, getBase().getLocation().getX());
        userFile.addEntry(LASTY, getBase().getLocation().getY());
        userFile.addEntry(LASTZ, getBase().getLocation().getZ());
        userFile.addEntry(LASTYAW, getBase().getLocation().getYaw());
        userFile.addEntry(LASTPITCH, getBase().getLocation().getPitch());
        userFile.addEntry(LASTWORLD, getBase().getLocation().getWorld().getName());
        
        this.afk = false;
        this.displayName = userFile.get(DISPLAYNAME);
        this.walkSpeed = userFile.get(WALKSPEED);
        this.flySpeed = userFile.get(FLYSPEED);
        this.gamemode = userFile.get(GAMEMODE);
        this.infoBoard = userFile.get(INFOBOARD);
        this.lastLocation = new Location(
                userFile.getWorld(LASTWORLD) == null ? getBase().getWorld() : userFile.getWorld(LASTWORLD),
                userFile.get(LASTX),
                userFile.get(LASTY),
                userFile.get(LASTZ),
                userFile.get(LASTYAW),
                userFile.get(LASTPITCH));

        getBase().setDisplayName(displayName);
        getBase().setWalkSpeed(Float.parseFloat(Double.toString(0.2 * Math.pow(walkSpeed, 0.69897))));
        getBase().setFlySpeed((float)flySpeed/10);
        getBase().setGameMode(gamemode.getBukkitMode());
        runInfobard(infoBoard);
        
        
        long finish = System.currentTimeMillis();
        debug("User " + getName() + " loaded in " + (finish-start) + " milliseconds.");
        return this;
    }
    
    @Override
    public void logout() {
        userFile.setEntry(NAME, getName());
        userFile.setEntry(AFK, false);
        userFile.setEntry(DISPLAYNAME, displayName);
        userFile.setEntry(WALKSPEED, walkSpeed);
        userFile.setEntry(FLYSPEED, flySpeed);
        userFile.setEntry(GAMEMODE, gamemode.toString());
        userFile.setEntry(INFOBOARD, infoBoard);
        
    }
}
