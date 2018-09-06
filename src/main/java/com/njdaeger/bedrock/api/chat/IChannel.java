package com.njdaeger.bedrock.api.chat;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IChannel {
    
    /**
     * Whether this channel is temporary or not.
     * @return True if it's suppose to close when the channel becomes empty, false does not close it when empty
     */
    default boolean closeWhenEmpty() {
        return getClose().equals(Close.CHANNEL_EMPTY);
    }
    
    /**
     * Whether this channel is saved in the channels.yml
     * @return True if the channel is saved, false otherwise.
     */
    boolean isSaved();
    
    /**
     * Get a list of users currently in this channel.
     * @return Users in this channel
     */
    List<IUser> getUsers();
    
    /**
     * When to close this channel
     * @return When to close the channel
     */
    Close getClose();
    
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
        if (getUsers().size() == 0 && closeWhenEmpty()) close();
    }
    
    /**
     * Kicks all the users in this channel
     */
    default void kickAll() {
        List<IUser> usersCopy = new ArrayList<>(getUsers());
        usersCopy.forEach(this::kickUser);
    }
    
    /**
     * Kicks all the users that match the given predicate
     * @param predicate The predicate to match
     */
    default void kickIf(Predicate<IUser> predicate) {
        List<IUser> usersCopy = new ArrayList<>(getUsers()).stream().filter(predicate).collect(Collectors.toList());
        usersCopy.forEach(this::kickUser);
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
     * Send a message to all the users in this channel. no formatting
     * @param message The message to send
     */
    default void message(String message) {
        if (message == null) message = "";
        String finalMessage = message;
        getUsers().forEach(u -> u.sendMessage(finalMessage));
    }
    
    /**
     * Sends a user message with standard formatting
     * @param user The user sending the message
     * @param message The message being sent
     */
    default void userMessage(IUser user, String message) {
        if (message == null) message = "";
        message(Bedrock.translate(getRawChannelFormat(),
                message,
                user.getName(),
                user.getDisplayName(),
                user.getBase().getHealth(),
                user.getBase().getFoodLevel(),
                user.getWorld().getName(),
                getName(),
                getPrefix(),
                (getDisplay().equals(Display.PREFIX) ? getPrefix() : getName())));
    }
    
    /**
     * Closes the channel and removes everyone from it.
     */
    default void close() {
        Bedrock.closeChannel(this);
    }
    
    /**
     * Get the type of display this channel uses in chat
     * @return The display type
     */
    Display getDisplay();
    
    /**
     * Removes the channel from the server
     */
    default void remove() {
        Bedrock.getBedrock().removeChannel(this);
    }
    
    /**
     * Check if the channel requires a permission to join
     * @return True if the channel requires a permission, false otherwise.
     */
    default boolean hasPermission() {
        return getPermission() != null;
    }
    
    /**
     * Gets the raw channel format meant for the string replacer
     * @return The raw channel format.
     */
    String getRawChannelFormat();
    
    
    /**
     * Gets this channels chat format
     * @return The channel chat format. If this doesn't have a custom one, the default is used.
     */
    String getChannelFormat();
    
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
