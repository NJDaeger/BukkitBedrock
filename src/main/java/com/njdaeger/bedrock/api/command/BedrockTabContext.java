package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.command.base.TabContext;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.user.IUser;

public class BedrockTabContext extends TabContext {
    
    private IBedrock bedrock;
    
    public BedrockTabContext(BedrockCommandContext context, ProcessedCommand command, String[] args) {
        super(context, command, args);
        bedrock = context.getPlugin();
    }
    
    public IUser getUser(String name) {
        return bedrock.getUser(name);
    }
    
}
