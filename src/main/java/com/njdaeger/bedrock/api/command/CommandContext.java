package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractCommandContext;
import com.njdaeger.bci.base.BCICommand;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Gamemode;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.lang.Message;
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
    public final void notEnoughArgs() throws BedrockException {
        notEnoughArgs(command.getMinArgs(), getLength());
    }

    public final void notEnoughArgs(int minimum, int given) throws BedrockException {
        throw new NotEnoughArgsException(minimum, given);
    }

    @Override
    public final void tooManyArgs() throws BedrockException {
        tooManyArgs(command.getMaxArgs(), getLength());
    }

    public final void tooManyArgs(int maximum, int given) throws BedrockException {
        throw new TooManyArgsException(maximum, given);
    }

    public final void userNotFound(String user) throws BedrockException {
        throw new UserNotFoundException(user);
    }
    
    public final void userNotFound(int index) throws UserNotFoundException {
        throw new UserNotFoundException(argAt(index));
    }
    
    @Override
    public final void noPermission() throws BedrockException {
        throw new NoPermissionException(command.getPermissions());
    }
    
    public final void noPermission(Permission permission) throws BedrockException {
        throw new NoPermissionException(permission.toString());
    }
    
    public Gamemode gamemodeAt(int index) {
        if (hasArgAt(index)) return Gamemode.resolveGamemode(argAt(index));
        return null;
    }
    
}
