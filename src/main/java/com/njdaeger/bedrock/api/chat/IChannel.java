package com.njdaeger.bedrock.api.chat;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.List;

public interface IChannel {
    
    /**
     * Get the owner of this channel if it exists.
     * @return The owner. Null if no one owns this channel.
     */
    IUser getOwner();
    
    /**
     * Get a list of users currently in this channel.
     * @return Users in this channel
     */
    List<IUser> getUsers();
    
    /**
     * Whether to delete this channel when the owner exists
     * @return Ill write this eventually
     */
    boolean closeOnExit();
    
    /**
     * Get a user from this channel
     * @param name The name of the user you are trying to find.
     * @return The user if they are in this channel, null otherwise.
     */
    default IUser getUser(String name) {
        return getUsers().stream().filter(u -> u.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    /**
     * Whether this channel has a specific user or not
     * @param user The user to check for
     * @return True if this channel contains the user, false otherwise.
     */
    default boolean hasUser(IUser user) {
        return getUsers().contains(user);
    }
    
    /**
     * Kick a user from the channel
     * @param name The name of the user to kick
     */
    default void kickUser(String name) {
        if (Bedrock.getUser(name) == null) return;
        kickUser(Bedrock.getUser(name));
    }
    
    /**
     * Kick a user from the channel
     * @param user The user to kick
     */
    default void kickUser(IUser user) {
        if (user.hasChannel(this)) user.leaveChannel(this);
        getUsers().remove(user);
    }
    
    /**
     * Add a user to this channel
     * @param user The user to add to this channel
     */
    default void addUser(IUser user) {
        if (!user.hasChannel(this)) user.addChannel(this);
        getUsers().add(user);
    }
    
    /**
     * Add a user to this channel
     * @param name The name of the user to add
     */
    default void addUser(String name) {
        if (Bedrock.getUser(name) == null) return;
        addUser(Bedrock.getUser(name));
    }
    
    /**
     * Send a message to all the users in this channel
     * @param message The message to send
     */
    default void sendMessage(String message) {
        getUsers().forEach(u -> u.sendMessage(message));
    }
    
    /**
     * Closes the channel and removes everyone from it.
     */
    default void close() {
        sendMessage(getPrefix() + " Channel closing.");
        getUsers().forEach(this::kickUser);
    }
    
    /**
     * Completely removes this channel from the server.
     */
    void remove();
    
    /**
     * Get the name of this channel
     * @return The name of this channel
     */
    String getName();
    
    /**
     * Get this channels prefix.
     * @return The channels prefix
     */
    String getPrefix();
    
    /**
     * Permission needed to join this channel
     * @return The permission needed to join the channel
     */
    String getPermission();
    
}
