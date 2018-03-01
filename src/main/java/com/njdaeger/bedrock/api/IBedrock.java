package com.njdaeger.bedrock.api;

import com.coalesce.core.Color;
import com.coalesce.core.plugin.ICoPlugin;
import com.njdaeger.bedrock.MessageFile;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandRegister;
import com.njdaeger.bedrock.api.config.IConfig;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.entity.Player;

import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public interface IBedrock extends ICoPlugin {
    
    /**
     * Get the plugin configuration
     * @return The plugin config.
     */
    IConfig getConf();
    
    /**
     * Get all the currently registered channels
     * @return The server chat channels
     */
    List<IChannel> getChannels();
    
    /**
     * Get a specific channel
     * @param name The name of the channel to get
     * @return The channel if it exists, null otherwise
     */
    IChannel getChannel(String name);
    
    /**
     * Create a new channel.
     * @param name The name of the channel
     * @param prefix The prefix in chat for the channel
     * @param permission The permission needed to be automatically added into this channel
     */
    default void createChannel(String name, String prefix, String permission) {
    
    }
    
    /**
     * Create a new channel anyone can access. (No permission is set for this channel)
     * @param name The name of this channel
     * @param prefix The prefix in chat for the channel
     */
    void createChannel(String name, String prefix);
    
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
        channel.close();
        channel.remove();
    }
    
    /**
     * Get an online user by name
     * @param name The name of the user
     * @return The user if they exist, null otherwise.
     */
    IUser getUser(String name);
    
    /**
     * Get an online user via its bukkit player object
     * @param player Player to get user representation of
     * @return The user if they exist, null otherwise
     */
    default IUser getUser(Player player) {
        return getUser(player.getName());
    }
    
    /**
     * Get a list of all the users currently online
     * @return The online users
     */
    List<IUser> getUsers();
    
    /**
     * Get all the users that match the predicate
     * @param predicate The predicate
     * @return The users that match the given predicate
     */
    default List<IUser> getUsers(Predicate<IUser> predicate) {
        return getUsers().stream().filter(predicate).collect(Collectors.toList());
    }
    
    /**
     * Get the current message file. Language dependant.
     * @return The message file.
     */
    MessageFile getMessageFile();
    
    /**
     * Get the language this plugin is currently using
     * @return The plugin language
     */
    default MessageFile.Language getLanguage() {
        return getConf().getLanguage();
    }
    
    /**
     * Get a translated string colored
     * @param message The message to translate
     * @param placeholders The placeholders in the message.
     * @return The string colored, translated, and placeholders replaced.
     */
    default String translate(Message message, Object... placeholders) {
        return Color.translate('&',getCoFormatter().formatString(getMessageFile().translate(message), placeholders));
    }
    
    /**
     * Get a translated string colored and formatted
     * @param message The message to translate
     * @param placeholders The placeholders in the message.
     * @return The string colored, translated, formatted, and placeholders replaced.
     */
    default String pluginTranslate(Message message, Object... placeholders) {
        return Color.translate('&',getCoFormatter().format(getMessageFile().translate(message), placeholders));
    }
    
    /**
     * Register a processed command.
     * @param commands The commands to register
     */
    default void registerCommand(BedrockCommand... commands) {
        for (BedrockCommand command : commands) {
            getCommandStore().registerCommand(command, new BedrockCommandRegister(command, this));
        }
    }
    
}
