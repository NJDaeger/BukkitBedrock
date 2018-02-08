package com.njdaeger.bedrock.api.command;

import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.CommandContext;
import com.coalesce.core.wrappers.CoSender;
import com.njdaeger.bedrock.Message;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Stream;

public class BedrockCommandContext extends CommandContext<BedrockCommandContext, BedrockTabContext> {
    
    private IBedrock bedrock;
    
    public BedrockCommandContext(CoSender sender, String[] args, IBedrock plugin) {
        super(sender, args, plugin);
        this.bedrock = plugin;
    }
    
    /**
     * Get a user from the server
     * @param name The name of the user to get.
     * @return Null if the user does not exist/is online.
     */
    public IUser getUser(String name) {
        return bedrock.getUser(name);
    }
    
    /**
     * Get a user from the server via an argument
     * @param index The arg to get the user name from
     * @return Null if the user does not exist or if the index is greater than the amount the command has
     */
    public IUser getUser(int index) {
        if (length()-1 < index) return null;
        return getUser(argAt(index));
    }
    
    /**
     * Get the sender as a user.
     * @return Null if the sender is not a User. the user otherwise.
     */
    public IUser getUser() {
        if (getSender().getType() == SenderType.PLAYER) return getUser(getSender().getName());
        return null;
    }
    
    /**
     * Get the name of the sender
     * @return The name of the sender.
     */
    public String getName() {
        return getSender().getName();
    }
    
    public String getDisplayName() {
        if (getSender().getType() == SenderType.CONSOLE) return "Console";
        else return getUser().getDisplayName();
    }
    
    /**
     * Check if the user has all the permissions specified
     * @param permissions The permissions needed
     * @return True if the sender has all the permissions.
     */
    public boolean hasAllPermissions(Permission... permissions) {
        for (Permission perm : permissions) {
            if (!hasPermission(perm.toString())) {
                return false;
            }
        }
        return true;
    }
    
    /**
     * Check if the user has the permission specified
     * @param permission The permission to check
     * @return True if the user has permission.
     */
    public boolean hasPermission(Permission permission) {
        return hasPermission(permission.toString());
    }
    
    /**
     * Check if the user has one of any of the given permissions
     * @param permissions The permissions to check
     * @return True if the user has one or more permissions.
     */
    public boolean hasAnyPermission(Permission... permissions) {
        for (Permission perm : permissions) {
            if (hasPermission(perm.toString())) {
                return true;
            }
        }
        return false;
    }
    
    public void send(Message message, Object... objects) {
        send(bedrock.translate(message, objects));
    }
    
    public void pluginMessage(Message message, Object... placeholders) {
        pluginMessage(bedrock.translate(message, placeholders));
    }
    
    /**
     * Error message if the user given is not found.
     * @param user The user that wasn't found
     */
    public void userNotFound(String user) {
        pluginMessage(Message.ERROR_USER_NOT_FOUND, user);
    }
    
    /**
     * The no permission message
     * @param permissions The permissions needed to run this command.
     */
    public void noPermission(Permission... permissions) {
        Set<String> perm = new HashSet<>();
        Stream.of(permissions).forEach(p -> perm.add(p.toString()));
        noPermission(perm.toArray(new String[0]));
    }
    
    @Override
    public void noPermission(String... permissionsNeeded) {
        pluginMessage(Message.ERROR_NO_PERMISSION, (Object[])permissionsNeeded);
    }
    
    @Override
    public void tooManyArgs(int max, int given) {
        pluginMessage(Message.ERROR_TOO_MANY_ARGS, max, given);
    }
    
    @Override
    public void notEnoughArgs(int min, int given) {
        pluginMessage(Message.ERROR_NOT_ENOUGH_ARGS, min, given);
    }
    
    @Override
    public void notCorrectSender(SenderType... allowed) {
        pluginMessage(Message.ERROR_INCORRECT_SENDER, (Object[])allowed);
    }
    
    @Override
    public IBedrock getPlugin() {
        return bedrock;
    }
    
}
