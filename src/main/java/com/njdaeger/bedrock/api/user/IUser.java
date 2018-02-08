package com.njdaeger.bedrock.api.user;

import com.coalesce.core.session.ISession;
import com.njdaeger.bedrock.Gamemode;
import com.njdaeger.bedrock.Message;
import com.njdaeger.bedrock.api.IBedrock;
import org.bukkit.Location;
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
     * Get the location where the user went afk originally
     * @return The user's afk location.
     */
    Location getAfkLocation();
    
    /**
     * Get the user's current location
     * @return The user's current location
     */
    Location getLocation();
    
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
    
    @Override
    IBedrock getSessionOwner();
    
    void setGamemode(Gamemode gamemode);
    
    Gamemode getGamemode();
    
    /**
     * Send the user a message
     * @param message the message to send
     */
    default void sendMessage(String message) {
        get().sendMessage(message);
    }
    
    /**
     * Send the user a plugin message with placeholders
     * @param message The message to send
     * @param placeholders The placeholders
     */
    default void sendMessage(String message, Object... placeholders) {
        sendMessage(getSessionOwner().getCoFormatter().formatString(message, placeholders));
    }
    
    /**
     * Send the user a translated message
     * @param message The message to send
     * @param placeholder The message placeholders
     */
    default void sendMessage(Message message, Object... placeholder) {
        sendMessage(getSessionOwner().translate(message, placeholder));
    }
    
    /**
     * Send the user a formatted message
     * @param message The message to send
     */
    default void pluginMessage(String message) {
        sendMessage(getSessionOwner().getCoFormatter().format(message));
    }
    
    /**
     * Send the user a formatted message with placeholders
     * @param message The message to send
     * @param placeholders the placeholders
     */
    default void pluginMessage(String message, Object... placeholders) {
        sendMessage(getSessionOwner().getCoFormatter().format(message, placeholders));
    }
    
    /**
     * Send the user a translated formatted message
     * @param message The message to send
     * @param placeholder The message placeholders
     */
    default void pluginMessage(Message message, Object... placeholder) {
        sendMessage(getSessionOwner().pluginTranslate(message, placeholder));
    }
    
}
