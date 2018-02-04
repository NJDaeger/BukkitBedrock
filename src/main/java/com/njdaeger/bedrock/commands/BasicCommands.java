package com.njdaeger.bedrock.commands;

import com.coalesce.core.Color;
import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.ProcessedCommand;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.events.UserAfkStatusEvent;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.command.BedrockCommandRegister;
import com.njdaeger.bedrock.api.command.BedrockTabContext;
import org.bukkit.Bukkit;

public class BasicCommands {

    private final IBedrock bedrock;
    
    public BasicCommands(IBedrock bedrock) {
        this.bedrock = bedrock;
        ProcessedCommand<BedrockCommandContext, BedrockTabContext> cmd = ProcessedCommand.builder(BedrockCommandContext.class, BedrockTabContext.class, bedrock, "name")
                .executor(this::afk)
                .description("Mark yourself as away from keyboard.")
                .usage("/afk [message]")
                .permission(Permission.COMMAND_AFK.toString())
                .aliases("away", "brb")
                .senders(SenderType.PLAYER)
                .build();
        bedrock.getCommandStore().registerCommand(cmd, new BedrockCommandRegister(cmd, bedrock));
        
    }
    
    private void afk(BedrockCommandContext context) {
        if (!context.getUser().isAfk()) {
            context.getUser().setAfk(true, context.joinArgs());
            
        } else {
            context.getUser().setAfk(false, null);
        }
        
        
    }

}
