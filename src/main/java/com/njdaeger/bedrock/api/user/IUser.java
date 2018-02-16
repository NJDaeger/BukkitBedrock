package com.njdaeger.bedrock.api.user;

import com.coalesce.core.session.ISession;
import com.njdaeger.bedrock.Gamemode;
import com.njdaeger.bedrock.Message;
import com.njdaeger.bedrock.SpeedType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.config.IHome;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collection;
import java.util.UUID;

public interface IUser extends ISession<Player> {
    
    /**
     * Get the name of the user
     * @return The user's name
     */
    String getName();
    
    /**
     * Get the users UUID
     * @return The user UUID
     */
    UUID getId();
    
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
    
    /**
     * Set the users gamemode
     * @param gamemode The new gamemode
     */
    void setGamemode(Gamemode gamemode);
    
    /**
     * Get the users current gamemode
     * @return The gamemode they currently have
     */
    Gamemode getGamemode();
    
    /**
     * Set the users speed
     * @param type The type of speed to set
     * @param speed The speed [0-10]
     */
    void setSpeed(SpeedType type, double speed);
    
    /**
     * Set the users speed according to whether they're flying or walking
     * @param speed The speed to set [0-10]
     */
    void setSpeed(double speed);
    
    /**
     * Get whether the user is flying or walking
     * @return The type of movement they are currently doing
     */
    SpeedType getMovementType();
    
    /**
     * Get the users fly speed
     * @return The users fly speed
     */
    double getFlySpeed();
    
    /**
     * Get the users walk speed
     * @return The users walk speed
     */
    double getWalkSpeed();
    
    /**
     * Get a list of homes this user owns
     * @return The homes list
     */
    Collection<IHome> getHomes();
    
    /**
     * Get a home from this user
     * @param name The name of the home
     * @return The home. Null if it doesnt exist.
     */
    IHome getHome(String name);
    
    /**
     * Create a new home for this user
     * @param name The name of this home
     * @return Whether the home was successfully created or not.
     */
    boolean createHome(String name);
    
    /**
     * Delete a home from this user
     * @param name The name of the home to delete
     * @return True if the home was deleted, false if it didnt exist or couldnt be deleted.
     */
    boolean deleteHome(String name);
    
    /**
     * Get a string of all the users homes formatted
     * @return The users homes
     */
    default String listHomes() {
        StringBuilder builder = new StringBuilder();
        getHomes().forEach(home -> builder.append(getSessionOwner().translate(Message.LISTHOMES_FORMAT, home.getName())));
        return builder.toString();
    }
    
    /**
     * Check whether this user has any homes
     * @return True if the user has homes, false otherwise.
     */
    default boolean hasHomes() {
        return !getHomes().isEmpty();
    }
    
    /**
     * Get the base directory of this user.
     * @return The user directory
     */
    default File getUserDirectory() {
        return getDataFile().getDirectory();
    }
    
    /**
     * Get the users X value
     * @return X value
     */
    default double getX() {
        return getLocation().getX();
    }
    
    /**
     * Get the users Y value
     * @return Y value
     */
    default double getY() {
        return getLocation().getY();
    }
    
    /**
     * GTet the users Z value
     * @return Z value
     */
    default double getZ() {
        return getLocation().getZ();
    }
    
    /**
     * Get the users Yaw
     * @return Yaw value
     */
    default float getYaw() {
        return getLocation().getYaw();
    }
    
    /**
     * Get the users Pitch
     * @return Pitch value
     */
    default float getPitch() {
        return getLocation().getPitch();
    }
    
    /**
     * Get the users current world
     * @return World user is currently in
     */
    default World getWorld() {
        return getLocation().getWorld();
    }
    
    /**
     * Teleports a user to a location
     * @param location The teleport location
     */
    default void teleport(Location location) {
        get().teleport(location);
    }
    
    /**
     * Send this user to a home
     * @param home The home to send the user to.
     */
    default void sendHome(IHome home) {
        teleport(home.getLocation());
    }
    
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
