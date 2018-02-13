package com.njdaeger.bedrock.config;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.IHome;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.io.File;

public final class Home extends YmlConfig implements IHome {
    
    private final IUser user;
    private final String name;
    private double x;
    private double y;
    private double z;
    private float yaw;
    private float pitch;
    private World world;
    
    public Home(IBedrock bedrock, IUser user, String name) {
        super("users" + File.separator + user.getId().toString() + File.separator +  "homes" + File.separator + name, bedrock);
        
        this.user = user;
        this.name = name;
        
        addEntry("x", user.getX());
        addEntry("y", user.getY());
        addEntry("z", user.getZ());
        addEntry("yaw", user.getYaw());
        addEntry("pitch", user.getPitch());
        addEntry("world", user.getWorld().getName());
        
        this.x = getDouble("x");
        this.y = getDouble("y");
        this.z = getDouble("z");
        this.yaw = getFloat("yaw");
        this.pitch = getFloat("pitch");
        this.world = Bukkit.getWorld(getString("world"));
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public IUser getOwner() {
        return user;
    }
    
    @Override
    public void sendHere(IUser user) {
        user.sendHome(this);
    }
    
    @Override
    public double getX() {
        return x;
    }
    
    @Override
    public double getY() {
        return y;
    }
    
    @Override
    public double getZ() {
        return z;
    }
    
    @Override
    public float getYaw() {
        return yaw;
    }
    
    @Override
    public float getPitch() {
        return pitch;
    }
    
    @Override
    public World getWorld() {
        return world;
    }
    
    @Override
    public Location getLocation() {
        return new Location(world, x, y, z, yaw, pitch);
    }
    
    @Override
    public void setLocation(Location location) {
        setX(location.getX());
        setY(location.getY());
        setZ(location.getZ());
        setYaw(location.getYaw());
        setPitch(location.getPitch());
        setWorld(location.getWorld());
    }
    
    @Override
    public void setWorld(World world) {
        this.setEntry("world", world.getName());
        this.world = world;
    }
    
    @Override
    public void setPitch(float pitch) {
        this.setEntry("pitch", pitch);
        this.pitch = pitch;
    }
    
    @Override
    public void setYaw(float yaw) {
        this.setEntry("yaw", yaw);
        this.yaw = yaw;
    }
    
    @Override
    public void setZ(double z) {
        this.setEntry("z", z);
        this.z = z;
    }
    
    @Override
    public void setY(double y) {
        this.setEntry("y", y);
        this.y = y;
    }
    
    @Override
    public void setX(double x) {
        this.setEntry("x", x);
        this.x = x;
    }
}
