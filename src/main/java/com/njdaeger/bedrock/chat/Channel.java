package com.njdaeger.bedrock.chat;

import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.ArrayList;
import java.util.List;

public class Channel implements IChannel {
    
    private String rawChannelFormat;
    private final String channelFormat;
    private final List<IUser> users;
    private final String permission;
    private final Close whenToClose;
    private final Display display;
    private final String prefix;
    private final boolean save;
    private final String name;
    
    /**
     * Create a new channel
     * @param name Name of the channel to create
     * @param prefix Channel prefix (Nullable)
     * @param permission Permission needed to join (Nullable)
     * @param whenToClose When to close the channel
     * @param save Whether to save to the channels.yml
     */
    public Channel(String name, String prefix, Display display, String permission, String channelFormat, Close whenToClose, boolean save) {
        this.users = new ArrayList<>();
        this.channelFormat = channelFormat;
        this.whenToClose = whenToClose;
        this.permission = permission;
        this.display = display;
        this.prefix = prefix;
        this.name = name;
        this.save = save;
    
        rawChannelFormat = channelFormat;
        rawChannelFormat = rawChannelFormat.replace("{MESSAGE}", "{0}");
        rawChannelFormat = rawChannelFormat.replace("{NAME}", "{1}");
        rawChannelFormat = rawChannelFormat.replace("{DISPLAYNAME}", "{2}");
        rawChannelFormat = rawChannelFormat.replace("{HEALTH}", "{3}");
        rawChannelFormat = rawChannelFormat.replace("{FOOD}", "{4}");
        rawChannelFormat = rawChannelFormat.replace("{WORLDNAME}", "{5}");
        rawChannelFormat = rawChannelFormat.replace("{CHANNELNAME}", "{6}");
        rawChannelFormat = rawChannelFormat.replace("{CHANNELPREFIX}", "{7}");
        rawChannelFormat = rawChannelFormat.replace("{DISPLAYTYPE}", "{8}");
        
        
    }
    
    @Override
    public List<IUser> getUsers() {
        return users;
    }
    
    @Override
    public boolean isSaved() {
        return save;
    }
    
    @Override
    public Display getDisplay() {
        return display;
    }
    
    @Override
    public String getRawChannelFormat() {
        return rawChannelFormat;
    }
    
    @Override
    public String getChannelFormat() {
        return channelFormat;
    }
    
    @Override
    public String getName() {
        return name;
    }
    
    @Override
    public String getPrefix() {
        return prefix;
    }
    
    @Override
    public String getPermission() {
        return permission;
    }
    
    @Override
    public Close getClose() {
        return whenToClose;
    }
}
