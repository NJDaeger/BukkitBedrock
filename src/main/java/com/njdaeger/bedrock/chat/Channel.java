package com.njdaeger.bedrock.chat;

import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.ArrayList;
import java.util.List;

public class Channel implements IChannel {
    
    private final List<IUser> users;
    private final String permission;
    private final Close whenToClose;
    private final Display display;
    private final String prefix;
    private final boolean save;
    private final String name;
    private final IUser owner;
    
    /**
     * Create a new channel
     * @param name Name of the channel to create
     * @param prefix Channel prefix (Nullable)
     * @param permission Permission needed to join (Nullable)
     * @param owner Owner (Nullable)
     * @param whenToClose When to close the channel
     * @param save Whether to save to the channels.yml
     */
    public Channel(String name, String prefix, String permission, IUser owner, Display display, Close whenToClose, boolean save) {
        this.users = new ArrayList<>();
        this.whenToClose =whenToClose;
        this.permission = permission;
        this.display = display;
        this.prefix = prefix;
        this.owner = owner;
        this.name = name;
        this.save = save || whenToClose.equals(Close.CHANNEL_EMPTY);
    }
    
    @Override
    public List<IUser> getUsers() {
        return users;
    }
    
    @Override
    public boolean closeOnExit() {
        return whenToClose.equals(Close.OWNER_EXIT);
    }
    
    @Override
    public boolean isSaved() {
        return save;
    }
    
    @Override
    public void remove() {
        if (!isSaved()) return;
    }
    
    @Override
    public Display getDisplay() {
        return display;
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
    public Close whenToClose() {
        return whenToClose;
    }
    
    @Override
    public boolean closeWhenEmpty() {
        return whenToClose.equals(Close.CHANNEL_EMPTY);
    }
    
    @Override
    public IUser getOwner() {
        return owner;
    }
}
