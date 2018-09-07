package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractTabContext;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.lang.Message;
import com.njdaeger.bedrock.api.Permission;
import com.njdaeger.bedrock.api.command.exceptions.BedrockException;
import com.njdaeger.bedrock.api.command.exceptions.NoPermissionException;
import com.njdaeger.bedrock.api.command.exceptions.NotEnoughArgsException;
import com.njdaeger.bedrock.api.command.exceptions.TooManyArgsException;
import com.njdaeger.bedrock.api.command.exceptions.UserNotFoundException;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.stream.Stream;

public class TabContext extends AbstractTabContext<CommandContext, TabContext> {
    
    public TabContext(CommandContext commandContext) {
        super(commandContext);
    }
    
    public IUser getUser(String name) {
        return getPlugin().getUser(name);
    }
    
    public IUser userAt(int index) {
        if (getLength() <= index) return null;
        return getUser(argAt(index));
    }
    
    public IUser asUser() {
        if (getCommandContext().isPlayer()) return getUser(getName());
        return null;
    }
    
    public boolean isUserAt(int index) {
        return userAt(index) != null;
    }
    
    public String getName() {
        return getSender().getName();
    }
    
    public String getDisplayName() {
        if (getCommandContext().isConsole()) return "Console";
        if (getCommandContext().isBlock()) return "CommandBlock";
        if (getCommandContext().isPlayer()) return asUser().getDisplayName();
        else return getSender().getName();
    }
    
    public IBedrock getPlugin() {
        return getCommandContext().getPlugin();
    }
    
    public boolean hasPermission(Permission permission) {
        return hasPermission(permission.toString());
    }
    
    public boolean hasAnyPermission(Permission... permissions) {
        return Stream.of(permissions).anyMatch(this::hasPermission);
    }
    
    public boolean hasAllPermissions(Permission... permissions) {
        return Stream.of(permissions).allMatch(this::hasPermission);
    }
    
    public void pluginMessage(Message message, Object... placeholders) {
        pluginMessage(Bedrock.translate(message, placeholders));
    }
    
    public void notEnoughArgs() throws BedrockException {
        throw new NotEnoughArgsException(getCommand().getMinArgs(), getLength());
    }
    
    public void tooManyArgs() throws BedrockException {
        throw new TooManyArgsException(getCommand().getMaxArgs(), getLength());
    }
    
    public void userNotFound(String user) throws BedrockException {
        throw new UserNotFoundException(user);
    }
    
    public void noPermission() throws BedrockException {
        throw new NoPermissionException(getCommand().getPermissions());
    }
    
    public void noPermission(Permission permission) throws BedrockException {
        throw new NoPermissionException(permission.toString());
    }
    
}
