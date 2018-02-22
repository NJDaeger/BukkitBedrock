package com.njdaeger.bedrock.api.command;

import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.command.base.TabContext;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.HashSet;
import java.util.Set;

import static com.njdaeger.bedrock.Permission.*;

public class BedrockTabContext extends TabContext<BedrockCommandContext, BedrockTabContext, BedrockBuilder> {
    
    private IBedrock bedrock;
    
    public BedrockTabContext(BedrockCommandContext context, ProcessedCommand command, String[] args) {
        super(context, command, args);
        bedrock = context.getPlugin();
    }
    
    public boolean isConsole() {
        return getSender().getType() == SenderType.CONSOLE;
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
     * Get the sender as a user.
     * @return Null if the sender is not a User. the user otherwise.
     */
    public IUser getUser() {
        if (getSender().getType() == SenderType.PLAYER) return getUser(getSender().getName());
        return null;
    }
    
    /**
     * Quick gamemode completions
     * @param index Index where to do the gamemode completion
     */
    public void gamemodeCompletionAt(int index) {
        Set<String> modes = new HashSet<>();
        if (hasPermission(COMMAND_GAMEMODE_ADVENTURE)) modes.add("Adventure");
        if (hasPermission(COMMAND_GAMEMODE_CREATIVE)) modes.add("Creative");
        if (hasPermission(COMMAND_GAMEMODE_SPECTATOR)) modes.add("Spectator");
        if (hasPermission(COMMAND_GAMEMODE_SURVIVAL)) modes.add("Survival");
        completionAt(index, modes.toArray(new String[0]));
    }
    
    /**
     * Get the name of the sender
     * @return The name of the sender.
     */
    public String getName() {
        return getSender().getName();
    }
    
    public boolean hasAllPermissions(Permission... permissions) {
        for (Permission perm : permissions) {
            if (!hasPermission(perm.toString())) {
                return false;
            }
        }
        return true;
    }
    
    public boolean hasPermission(Permission permission) {
        return hasPermission(permission.toString());
    }
    
    public boolean hasAnyPermission(Permission... permissions) {
        for (Permission perm : permissions) {
            if (hasPermission(perm.toString())) {
                return true;
            }
        }
        return false;
    }
    
    /**
     * Completion at a specific index
     * @param index The index to do the completion
     * @param startRange The number to start off the completion with. (inclusive)
     * @param endRange The number to stop the completion with. (inclusive)
     */
    public void completionAt(int index, int startRange, int endRange) {
        String[] numbers = new String[1+endRange-startRange];
        for (int i = 0; startRange <= endRange; i++) {
            numbers[i] = Integer.toString(startRange);
            startRange++;
        }
        completionAt(index, numbers);
    }
    
}
