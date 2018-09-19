package com.njdaeger.bedrock;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.CommandStore;
import com.njdaeger.bedrock.api.settings.ISettings;
import com.njdaeger.bedrock.api.lang.ExtractJarContent;
import com.njdaeger.bedrock.api.lang.MessageFile;
import com.njdaeger.bedrock.commands.BasicCommands;
import com.njdaeger.bedrock.commands.ServerCommands;
import com.njdaeger.bedrock.config.ChannelConfig;
import com.njdaeger.bedrock.settings.Settings;
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
        File config = new File(getDataFolder() + File.separator + "config.yml");
        /*try {
            config.createNewFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        langFolder.mkdirs();

        new ExtractJarContent("/lang/", langFolder);
        if (!config.exists()) saveResource("config.yml", false);
    }
    
    @Override
    public void onEnable() {
        Bedrock.setBedrock(this);
        this.commandStore = new CommandStore(this);
        this.userMap = new UserMap(this);

        //Loading settings
        this.settings = new Settings(this);
        settings.reloadSettings();

        //this.channelConfig = new ChannelConfig(this);

        //Loading messages
        this.messageFile = new MessageFile(this, settings.getLang());
        messageFile.reloadMessages();

        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        new BasicCommands();
        //new HomeCommands(this);
        //new ChatCommands(this);
        new ServerCommands(this);

        loadPlayers();
    }
    
    @Override
    public void onDisable() {
        unloadPlayers();
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

    @Override
    public void reload() {
        getLogger().info("Reloading...");
        unloadPlayers();

        this.settings = new Settings(this);
        settings.reloadSettings();

        this.messageFile = new MessageFile(this, settings.getLang());
        messageFile.reloadMessages();

        loadPlayers();
        getLogger().info("Reload complete.");
    }

    public CommandStore getCommandStore() {
        return commandStore;
    }
    
    @Override
    public UserMap getUserMap() {
        return userMap;
    }

    private void unloadPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            userMap.removeUser(player);
        }
    }

    private void loadPlayers() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            userMap.addUser(player);
        }
    }

}
