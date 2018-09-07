package com.njdaeger.bedrock.api;

import com.njdaeger.bci.Utils;
import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.Command;
import com.njdaeger.bedrock.api.command.CommandStore;
import com.njdaeger.bedrock.api.command.CommandWrapper;
import com.njdaeger.bedrock.api.config.ISettings;
import com.njdaeger.bedrock.api.lang.Message;
import com.njdaeger.bedrock.api.lang.MessageFile;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.chat.Channel;
import com.njdaeger.bedrock.config.ChannelConfig;
import com.njdaeger.bedrock.user.UserMap;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IBedrock extends Plugin {
    
    /**
     * Get the plugin command store
     * @return The plugin command store
     */
    CommandStore getCommandStore();
    
    /**
     * Gets the plugin UserMap
     * @return The plugin UserMap
     */
    UserMap getUserMap();
    
    /**
     * Get the plugin configuration
     * @return The plugin config.
     */
    ISettings getSettings();
    
    /**
     * Get all the currently registered channels
     * @return The server chat channels
     */
    List<IChannel> getChannels();
    
    /**
     * Get the channel configuration file
     * @return The channel config file
     */
    ChannelConfig getChannelConfig();
    
    /**
     * Get the current message file. Language dependant.
     * @return The message file.
     */
    MessageFile getMessageFile();
    
    /**
     * Get a specific channel
     * @param name The name of the channel to get
     * @return The channel if it exists, null otherwise
     */
    default IChannel getChannel(String name) {
        return getChannels().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
    /**
     * Checks if a channel exists
     * @param name The name of the channel to look for
     * @return True if the channel exists, false otherwise
     */
    default boolean hasChannel(String name) {
        return getChannel(name) != null;
    }
    
    /**
     * Create a new channel
     * @param name The name of the channel
     * @param prefix The channel prefix
     * @param display The type of display this channel is going to use in chat
     * @param permission The permission needed to join this channel
     * @param whenToClose When to close this channel
     */
    default boolean createChannel(String name, String prefix, Display display, String permission, Close whenToClose) {
        return createChannel(new Channel(name, prefix, display, permission, getSettings().getDefaultChannelFormat(), whenToClose, !whenToClose.equals(Close.CHANNEL_EMPTY)));
    }
    
    /**
     * Create a new channel.
     * @param name The name of the channel
     * @param prefix The prefix in chat for the channel
     * @param permission The permission needed to be automatically added into this channel
     */
    default boolean createChannel(String name, String prefix, Display display, String permission) {
        return createChannel(name, prefix, display, permission, Close.NEVER);
    }
    
    /**
     * Create a new channel anyone can access. (No permission is set for this channel)
     * @param name The name of this channel
     * @param prefix The prefix in chat for the channel
     */
    default boolean createChannel(String name, String prefix, Display display) {
        return createChannel(name, prefix, display, null);
    }
    
    /**
     * Adds a new channel to the server
     * @param channel The channel to add to the server
     */
    default boolean createChannel(IChannel channel) {
        return getChannelConfig().addChannel(channel);
    }
    
    default void closeChannel(String name) {
        if (getChannel(name) != null) closeChannel(getChannel(name));
    }
    
    default void closeChannel(IChannel channel) {
        getChannelConfig().closeChannel(channel);
    }
    
    /**
     * Removes a channel from the server.
     * @param name The name of the channel to remove
     */
    default void removeChannel(String name) {
        if (getChannel(name) != null) removeChannel(getChannel(name));
    }
    
    /**
     * Removes a channel from the server
     * @param channel The channel to remove
     */
    default void removeChannel(IChannel channel) {
        getChannelConfig().removeChannel(channel);
    }
    
    /**
     * Get an online user by name or display name
     * @param name The name or display name of the user
     * @return The user if they exist, null otherwise.
     */
    default IUser getUser(String name) {
        return getUserMap().getUser(name);
    }
    
    /**
     * Get an online user via its bukkit player object
     * @param player Player to get user representation of
     * @return The user if they exist, null otherwise
     */
    default IUser getUser(Player player) {
        return getUser(player.getName());
    }
    
    /**
     * Get an online user by name
     * @param name The exact name of the user
     * @return The user if they exist, or null otherwise.
     */
    default IUser getUserExact(String name) {
        return getUserMap().getUserExact(name);
    }
    
    /**
     * Get a list of all the users currently online
     * @return The online users
     */
    default List<IUser> getUsers() {
        return getUserMap().getUsers();
    }
    
    /**
     * Get all the users that match the predicate
     * @param predicate The predicate
     * @return The users that match the given predicate
     */
    default List<IUser> getUsers(Predicate<IUser> predicate) {
        return getUsers().stream().filter(predicate).collect(Collectors.toList());
    }
    
    /**
     * Translates a string's placeholders
     * @param message The message to translate
     * @param placeholders The placeholders
     * @return The string, colored, translated, and placeholders replaced.
     */
    default String translate(String message, Object... placeholders) {
        return ChatColor.translateAlternateColorCodes('&', Utils.formatString(message, placeholders));
    }
    
    /**
     * Get a translated string colored
     * @param message The message to translate
     * @param placeholders The placeholders in the message.
     * @return The string colored, translated, and placeholders replaced.
     */
    default String translate(Message message, Object... placeholders) {
        return ChatColor.translateAlternateColorCodes('&', message.translate(placeholders));
    }
    
    /**
     * Get a translated string colored and formatted
     * @param message The message to translate
     * @param placeholders The placeholders in the message.
     * @return The string colored, translated, formatted, and placeholders replaced.
     */
    default String pluginTranslate(Message message, Object... placeholders) {
        return ChatColor.translateAlternateColorCodes('&', "&7[&aBukkitBedrock&7]&r " + message.translate(placeholders));
    }
    
    /**
     * Register a processed command.
     * @param commands The commands to register
     */
    default void registerCommand(Command... commands) {
        for (Command command : commands) {
            getCommandStore().registerCommand(new CommandWrapper(this, command));
        }
    }
    
    /**
     * Unregisters a command from the server
     * @param name The name of the command to unregister
     */
    default void unregisterCommand(String name) {
        getCommandStore().unregisterCommand(name);
    }
    
}
