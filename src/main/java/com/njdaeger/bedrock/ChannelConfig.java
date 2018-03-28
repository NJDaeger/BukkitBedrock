package com.njdaeger.bedrock;

import com.coalesce.core.config.YmlConfig;
import com.coalesce.core.config.base.ISection;
import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.chat.Channel;

import java.util.ArrayList;
import java.util.List;

public class ChannelConfig extends YmlConfig {
    
    private final List<IChannel> channels;
    
    public ChannelConfig(IBedrock bedrock) {
        super("channels", bedrock);
        this.channels = new ArrayList<>();
        
        if (!contains("channels", false)) {
            addEntry("channels.main.commandName", "main");
            addEntry("channels.main.prefix", "M");
            addEntry("channels.main.display", Display.NONE.name());
            addEntry("channels.main.close", Close.NEVER.name());
        }
    
        System.out.println(getKeys(false));
        System.out.println(getSection("channels").getKeys(false));
        
        for (ISection channel : getSections("channels")) {
            System.out.println(channel.getName());
            System.out.println("test");
            String prefix = channel.getString("prefix");
            String permission = channel.getString("permission");
            String owner = channel.getString("owner");
            Close whenToClose = channel.getValueAs("close", Close.class);
            Display type = channel.getValueAs("display", Display.class);
            System.out.println(prefix);
            System.out.println(permission);
            System.out.println(owner);
            System.out.println(whenToClose);
            System.out.println(type);
            System.out.println(channel.getCurrentPath());
            //boolean closeOnExit = channel.contains("closeOnExit") && channel.getBoolean("closeOnExit");
            
            channels.add(new Channel(channel.getName(), prefix, permission, bedrock.getUser(owner), type, whenToClose, true));
        }
    
        System.out.println(channels);
        
    }
    
    /**
     * gets a list of all the currently open channels.
     * @return The currently open channels
     */
    public List<IChannel> getChannels() {
        return channels;
    }
    
    /**
     * Closes a channel
     * @param channel The channel to close
     * @return True means the channel was removed from the open channels list, false means it wasn't removed, or it doesn't exist.
     */
    public boolean closeChannel(IChannel channel) {
        channel.close();
        return channels.remove(channel);
    }
    
    /**
     * Completely removes a channel from the server configuration
     * @param channel
     * @return  True means it was successfully removed. False means it either didn't close properly, or it wasnt removed from the channels config
     */
    public boolean removeChannel(IChannel channel) {
        getSection("channels").setEntry(channel.getName(), null);
        return closeChannel(channel) && !getSection("channels").contains(channel.getName());
    }
    
    /**
     * Adds a channel to the server
     * @param channel The channel to add
     * @return True if the channel was created, false if the channel already exists.
     */
    public boolean addChannel(IChannel channel) {
        if (channel.isSaved()) {
            if (getSection("channels").contains(channel.getName())) return false;
            if (channels.contains(channel)) return false;
            if (getChannel(channel.getName()) != null) return false;
            ISection s = getSection("channels");
            s.addEntry(channel.getName() + ".commandName", channel.getName());
            s.addEntry(channel.getName() + ".prefix", channel.getPrefix());
            s.addEntry(channel.getName() + ".display", channel.getDisplay().name());
            s.addEntry(channel.getName() + ".permission", channel.getPermission());
            s.addEntry(channel.getName() + ".owner", channel.getOwner().getName());
            s.addEntry(channel.getName() + ".close", channel.whenToClose().name());
        }
        channels.add(channel);
        return true;
    }
    
    public IChannel getChannel(String name) {
        return getChannels().stream().filter(c -> c.getName().equalsIgnoreCase(name)).findFirst().orElse(null);
    }
    
}
