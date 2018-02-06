package com.njdaeger.bedrock.api;

import com.coalesce.core.Color;
import com.coalesce.core.plugin.ICoPlugin;
import com.njdaeger.bedrock.Message;
import com.njdaeger.bedrock.MessageFile;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandRegister;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.List;

public interface IBedrock extends ICoPlugin {
    
    /**
     * Get the plugin configuration
     * @return The plugin config.
     */
    IConfig getConf();
    
    /**
     * Get an online user by name
     * @param name The name of the user
     * @return The user if they exist, null otherwise.
     */
    IUser getUser(String name);
    
    /**
     * Get a list of all the users currently online
     * @return The online users
     */
    List<IUser> getUsers();
    
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
