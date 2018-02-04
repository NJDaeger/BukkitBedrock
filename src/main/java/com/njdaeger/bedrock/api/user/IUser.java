package com.njdaeger.bedrock.api.user;

import com.coalesce.core.session.ISession;
import org.bukkit.entity.Player;

public interface IUser extends ISession<Player> {
    
    /**
     * Get the name of the user
     * @return The user's name
     */
    String getName();
    
    /**
     * Get the user data file
     * @return The user data file
     */
    IUserFile getDataFile();
    
    /**
     * Check whether the user is afk or not.
     * @return True if the user is afk, false otherwise.
     */
    boolean isAfk();
    
    /**
     * Set the user afk
     * @param value True marks the user afk
     */
    void setAfk(boolean value, String message);
    
    /**
     * Get the displayname of the user
     * @return The user's displayname
     */
    String getDisplayName();
    
    /**
     * Set the users displayname
     * @param name The user's new displayname
     */
    void setDisplayName(String name);
    
    /**
     * Runs login process.
     */
    void login();
    
    /**
     * Runs logout process.
     */
    void logout();
    
}
