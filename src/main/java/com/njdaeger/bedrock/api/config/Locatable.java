package com.njdaeger.bedrock.api.config;

import org.bukkit.Location;
import org.bukkit.World;

public interface Locatable {
    
    double getX();
    
    double getY();
    
    double getZ();
    
    float getYaw();
    
    float getPitch();
    
    World getWorld();
    
    Location getLocation();
    
    void setLocation(Location location);
    
    void setWorld(World world);
    
    void setPitch(float pitch);
    
    void setYaw(float yaw);
    
    void setZ(double z);
    
    void setY(double y);
    
    void setX(double x);
    
}
