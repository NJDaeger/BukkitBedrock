package com.njdaeger.bedrock.chat;

import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.ArrayList;
import java.util.List;

public abstract class Channel implements IChannel {
    
    private final boolean closeOnExit;
    private final List<IUser> users;
    private final String permission;
    private final String prefix;
    private final String name;
    private final IUser owner;
    
    public Channel(String name, String prefix, String permission, IUser owner) {
        this.users = new ArrayList<>();
        this.closeOnExit = owner == null;
        this.permission = permission;
        this.prefix = prefix;
        this.owner = owner;
        this.name = name;
    }
    
    @Override
    public List<IUser> getUsers() {
        return users;
    }
    
    @Override
    public boolean closeOnExit() {
        return closeOnExit;
    }
    
    @Override
    public void remove() {
    
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
    public IUser getOwner() {
        return owner;
    }
}
