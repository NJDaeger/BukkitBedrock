package com.njdaeger.bedrock;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.CommandStore;
import com.njdaeger.bedrock.api.config.ISettings;
import com.njdaeger.bedrock.api.lang.ExtractJarContent;
import com.njdaeger.bedrock.api.lang.MessageFile;
import com.njdaeger.bedrock.commands.BasicCommands;
import com.njdaeger.bedrock.commands.ChatCommands;
import com.njdaeger.bedrock.commands.HomeCommands;
import com.njdaeger.bedrock.config.ChannelConfig;
import com.njdaeger.bedrock.config.Settings;
import com.njdaeger.bedrock.listeners.PlayerListener;
import com.njdaeger.bedrock.user.UserMap;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.List;

public class BedrockPlugin extends JavaPlugin implements IBedrock, Listener {
    
    private UserMap userMap;
    private CommandStore commandStore;
    private ChannelConfig channelConfig;
    private MessageFile messageFile;
    private ISettings settings;
    
    @Override
    public void onLoad() {
        File langFolder = new File(getDataFolder() + File.separator + "lang");
        getDataFolder().mkdirs();
        langFolder.mkdirs();

        new ExtractJarContent("/lang/", langFolder);
    }
    
    @Override
    public void onEnable() {
        Bedrock.setBedrock(this);
        this.commandStore = new CommandStore(this);
        this.userMap = new UserMap(this);
        this.settings = new Settings(this);
        this.channelConfig = new ChannelConfig(this);
        this.messageFile = new MessageFile(this, settings.getLang());

        messageFile.reloadMap();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        new BasicCommands();
        new HomeCommands();
        new ChatCommands();
        
        for (Player player : Bukkit.getOnlinePlayers()) {
            userMap.addUser(player);
        }
    }
    
    @Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            userMap.removeUser(player);
        }
    }
    
    @Override
    public MessageFile getMessageFile() {
        return messageFile;
    }
    
    @Override
    public ISettings getSettings() {
        return settings;
    }
    
    @Override
    public List<IChannel> getChannels() {
        return getChannelConfig().getChannels();
    }
    
    @Override
    public IChannel getChannel(String name) {
        return null;
    }
    
    @Override
    public ChannelConfig getChannelConfig() {
        return this.channelConfig;
    }

    public CommandStore getCommandStore() {
        return commandStore;
    }
    
    @Override
    public UserMap getUserMap() {
        return userMap;
    }
}
