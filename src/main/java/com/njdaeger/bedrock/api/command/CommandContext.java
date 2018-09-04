package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractCommandContext;
import com.njdaeger.bci.base.BCICommand;
import com.njdaeger.bci.base.BCIException;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.Message;
import com.njdaeger.bedrock.api.Permission;
import com.njdaeger.bedrock.api.command.exceptions.BedrockException;
import com.njdaeger.bedrock.api.command.exceptions.NoPermissionException;
import com.njdaeger.bedrock.api.command.exceptions.NotEnoughArgsException;
import com.njdaeger.bedrock.api.command.exceptions.TooManyArgsException;
import com.njdaeger.bedrock.api.command.exceptions.UserNotFoundException;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.stream.Stream;

public class CommandContext extends AbstractCommandContext<CommandContext, TabContext> {
    
    public CommandContext(Plugin plugin, BCICommand<CommandContext, TabContext> command, CommandSender sender, String[] args, String alias) {
        super(plugin, command, sender, args, alias);
    }
    
    @Override
    protected String getPluginMessagePrefix() {
        return ChatColor.GRAY + "[" + ChatColor.GREEN + "BukkitBedrock" + ChatColor.GRAY + "]" + ChatColor.RESET;
    }
    
    public IUser getUser(String name) {
        return getPlugin().getUser(name);
    }
    
    public IUser userAt(int index) {
        if (getLength() <= index) return null;
        return getUser(argAt(index));
    }
    
    public IUser asUser() {
        if (isPlayer()) return getUser(getName());
        return null;
    }
    
    public boolean isUserAt(int index) {
        return userAt(index) != null;
    }
    
    public String getName() {
        return sender.getName();
    }
    
    public String getDisplayName() {
        if (isConsole()) return "Console";
        if (isBlock()) return "CommandBlock";
        if (isPlayer()) return asUser().getDisplayName();
        else return sender.getName();
    }
    
    @Override
    public IBedrock getPlugin() {
        return (IBedrock)plugin;
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
    
    @Override
    public void notEnoughArgs() throws BedrockException {
        throw new NotEnoughArgsException(command.getMinArgs(), getLength());
    }
    
    @Override
    public void tooManyArgs() throws BedrockException {
        throw new TooManyArgsException(command.getMaxArgs(), getLength());
    }
    
    public void userNotFound(String user) throws BedrockException {
        throw new UserNotFoundException(user);
    }
    
    @Override
    public void noPermission() throws BedrockException {
        throw new NoPermissionException(command.getPermissions());
    }
    
    public void noPermission(Permission permission) throws BedrockException {
        throw new NoPermissionException(permission.toString());
    }
    
}
