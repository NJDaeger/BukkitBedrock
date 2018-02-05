package com.njdaeger.bedrock.api.command;

import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.command.base.TabContext;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;

public class BedrockTabContext extends TabContext {
    
    private IBedrock bedrock;
    
    public BedrockTabContext(BedrockCommandContext context, ProcessedCommand command, String[] args) {
        super(context, command, args);
        bedrock = context.getPlugin();
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
    
}
