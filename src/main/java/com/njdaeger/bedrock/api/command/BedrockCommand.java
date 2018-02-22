package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.plugin.ICoPlugin;
import com.njdaeger.bedrock.api.Bedrock;

public class BedrockCommand extends ProcessedCommand<BedrockCommandContext, BedrockTabContext, BedrockBuilder> {
    
    BedrockCommand(ICoPlugin plugin, String name) {
        super(plugin, name);
    }
    
    public static BedrockBuilder builder(String name) {
        return new BedrockBuilder(Bedrock.getBedrock(), name);
    }
    
}
