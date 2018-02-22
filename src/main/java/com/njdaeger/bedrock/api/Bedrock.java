package com.njdaeger.bedrock.api;

import com.njdaeger.bedrock.MessageFile;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.config.IConfig;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.entity.Player;

import java.util.List;

public final class Bedrock {
    
    private static IBedrock bedrock;
    
    private Bedrock() {}
    
    public static void setBedrock(IBedrock bedrock) {
        if (Bedrock.bedrock == null) {
            Bedrock.bedrock = bedrock;
        } else throw new UnsupportedOperationException("Cannot set the IBedrock class again.");
    }
    
    public static IBedrock getBedrock() {
        return bedrock;
    }
    
    public static IUser getUser(String name) {
        return bedrock.getUser(name);
    }
    
    public static IUser getUser(Player player) {
        return bedrock.getUser(player);
    }
    
    public static List<IUser> getUsers() {
        return bedrock.getUsers();
    }
    
    public static String translate(Message message, Object... placeholders) {
        return bedrock.translate(message, placeholders);
    }
    
    public static String pluginTranslate(Message message, Object... placeholders) {
        return bedrock.pluginTranslate(message, placeholders);
    }
    
    public static MessageFile getMessageFile() {
        return bedrock.getMessageFile();
    }
    
    public static IConfig getConf() {
        return bedrock.getConf();
    }
    
    public static void debug(String message) {
        if (getConf().debug()) getBedrock().getCoLogger().debug(message);
    }
    
    public static void registerCommand(BedrockCommand... commands) {
        bedrock.registerCommand(commands);
    }
    
}
