package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.plugin.ICoPlugin;
import com.njdaeger.bedrock.api.IBedrock;

public class BedrockCommand extends ProcessedCommand<BedrockCommandContext, BedrockTabContext> {
    
    BedrockCommand(ICoPlugin plugin, String name) {
        super(plugin, name);
    }
    
    public static BedrockBuilder builder(IBedrock bedrock, String name) {
        return new BedrockBuilder(bedrock, name);
    }
    
}
