package com.njdaeger.bedrock;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.CommandStore;
import com.njdaeger.bedrock.api.config.ISettings;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.commands.BasicCommands;
import com.njdaeger.bedrock.commands.ChatCommands;
import com.njdaeger.bedrock.commands.HomeCommands;
import com.njdaeger.bedrock.config.ChannelConfig;
import com.njdaeger.bedrock.config.MessageFile;
import com.njdaeger.bedrock.config.Settings;
import com.njdaeger.bedrock.listeners.PlayerListener;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class BedrockPlugin extends JavaPlugin implements IBedrock, Listener {
    
    private CommandStore commandStore;
    private ChannelConfig channelConfig;
    private MessageFile messageFile;
    private ISettings configuration;
    
    @Override
    public void onLoad() {
        
        try {
            //This moves all the currently existing language files to lang folder
            File langFolder = new File(getDataFolder() + File.separator + "lang");
            if (!getDataFolder().exists()) getDataFolder().createNewFile();
            if (!langFolder.exists()) langFolder.mkdir();
    
            for (MessageFile.Language language : MessageFile.Language.values()) {
                File langFile = new File(langFolder + File.separator + language.getFileName() + ".yml");
                if (!langFile.exists()) {
                    langFile.createNewFile();
                }
        
                InputStream stream;
                OutputStream resStreamOut;
        
                stream = getClass().getResourceAsStream("/" + language.getFileName() + ".yml");
        
                int readBytes;
                byte[] buffer = new byte[4096];
                resStreamOut = new FileOutputStream(langFile);
                while ((readBytes = stream.read(buffer)) > 0) {
                    resStreamOut.write(buffer, 0, readBytes);
                }
                stream.close();
                resStreamOut.close();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    
    @Override
    public void onEnable() {
        this.commandStore = new CommandStore(this);
    
        Bedrock.setBedrock(this);
        //this.userNameSpace = new NamespacedSessionStore<>("users", IUser.class);
        this.configuration = new Settings(this);
        this.channelConfig = new ChannelConfig(this);
        this.messageFile = new MessageFile(this);
        
        Bukkit.getPluginManager().registerEvents(this, this);
        Bukkit.getPluginManager().registerEvents(new PlayerListener(this), this);
        
        new BasicCommands();
        new HomeCommands();
        new ChatCommands();
        
        /*for (Player player : Bukkit.getOnlinePlayers()) {
            userNameSpace.addSession(new User(this, userNameSpace, player.getName(), player)).login();
        }*/
    }
    
    /*@Override
    public void onDisable() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            userNameSpace.removeSession(player.getName()).logout();
        }
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userNameSpace.addSession(new User(this, userNameSpace, event.getPlayer().getName(), event.getPlayer())).login();
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        userNameSpace.removeSession(event.getPlayer().getName()).logout();
    }*/
    
    @Override
    public IUser getUser(String name) {
        return null;
    }
    
    @Override
    public List<IUser> getUsers() {
        return null;
    }
    
    @Override
    public MessageFile getMessageFile() {
        return messageFile;
    }
    
    @Override
    public ISettings getSettings() {
        return configuration;
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
}
