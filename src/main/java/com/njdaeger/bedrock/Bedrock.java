package com.njdaeger.bedrock;

import com.coalesce.core.CoPlugin;
import com.coalesce.core.session.NamespacedSessionStore;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.IConfig;
import com.njdaeger.bedrock.api.user.IUser;
import com.njdaeger.bedrock.commands.BasicCommands;
import com.njdaeger.bedrock.user.User;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class Bedrock extends CoPlugin implements IBedrock {
    
    private NamespacedSessionStore<IUser> userNameSpace;
    private IConfig configuration;
    
    @Override
    public void onPluginLoad() throws Exception {
        
        if (!getDataFolder().exists()) getDataFolder().createNewFile();
        
        for (MessageFile.Language language : MessageFile.Language.values()) {
            File langFile = new File(getDataFolder() + File.separator + language.getFileName() + ".yml");
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
    }
    
    
    
    @Override
    public void onPluginEnable() throws Exception {
        this.userNameSpace = new NamespacedSessionStore<>("users", IUser.class);
        this.configuration = new Config(this);
        configuration.create();
        registerListener(this);
        new BasicCommands(this);
        new MessageFile(this);
    }
    
    @Override
    public void onPluginDisable() throws Exception {
    
    }
    
    @EventHandler
    public void onJoin(PlayerJoinEvent event) {
        userNameSpace.addSession(new User(this, userNameSpace, event.getPlayer().getName(), event.getPlayer())).login();
    }
    
    @EventHandler
    public void onLeave(PlayerQuitEvent event) {
        userNameSpace.removeSession(event.getPlayer().getName()).logout();
    }
    
    @Override
    public IUser getUser(String name) {
        return userNameSpace.getSession(name);
    }
    
    @Override
    public List<IUser> getUsers() {
        return userNameSpace.getSessions();
    }
    
    @Override
    public MessageFile getMessageFile() {
        return null;
    }
    
    @Override
    public IConfig getConf() {
        return configuration;
    }
}
