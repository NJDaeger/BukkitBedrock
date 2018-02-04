package com.njdaeger.bedrock.api.command;

import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.CommandContext;
import com.coalesce.core.wrappers.CoSender;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;

public class BedrockCommandContext extends CommandContext {
    
    private IBedrock bedrock;
    
    public BedrockCommandContext(CoSender sender, String[] args, IBedrock plugin) {
        super(sender, args, plugin);
        this.bedrock = plugin;
    }
    
    @Override
    public IBedrock getPlugin() {
        return bedrock;
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
}
