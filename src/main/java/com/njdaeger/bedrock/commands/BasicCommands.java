package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.coalesce.core.command.base.ProcessedCommand;
import static com.njdaeger.bedrock.Message.*;
import static com.njdaeger.bedrock.Permission.*;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.command.BedrockTabContext;
import com.njdaeger.bedrock.api.user.IUser;

public class BasicCommands {

    private final IBedrock bedrock;
    
    public BasicCommands(IBedrock bedrock) {
        this.bedrock = bedrock;
        BedrockCommand afkCommand = (BedrockCommand)BedrockCommand.builder(bedrock, "afk")
                .executor(this::afk)
                .description(bedrock.translate(AFK_DESC))
                .usage(bedrock.translate(AFK_USAGE))
                .permission(COMMAND_AFK.toString())
                .aliases("away", "brb")
                .senders(SenderType.PLAYER)
                .build();
        
        BedrockCommand healCommand = (BedrockCommand)BedrockCommand.builder(bedrock, "afk")
                .permission(COMMAND_HEAL.toString(), COMMAND_HEAL_OTHER.toString())
                .description(bedrock.translate(HEAL_DESC))
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .usage(bedrock.translate(HEAL_USAGE))
                .aliases("fillhearts")
                .executor(this::heal)
                .maxArgs(1)
                .build();
        
        bedrock.registerCommand(afkCommand, healCommand);
        
    }
    
    private void afk(BedrockCommandContext context) {
        
        String message = bedrock.translate(AFK_AWAY_MESSAGE, context.getName());
        
        if (context.hasArgs()) {
            if (context.hasPermission(COMMAND_AFK_MESSAGE)) {
                message = bedrock.translate(AFK_AWAY_MESSAGE_MOREINFO, context.getName(), context.joinArgs());
            }
        }
        if (!context.getUser().isAfk()) {
            context.getUser().setAfk(true, message);
        }
    }
    
    private void heal(BedrockCommandContext context) {
        if (context.hasArgs()) {
            if (context.hasPermission(COMMAND_HEAL_OTHER)) {
                context.noPermission(COMMAND_HEAL_OTHER);
                return;
            }
            IUser user = context.getUser(context.argAt(0));
            if (user == null) {
            
            }
        }
    }
    
    private void healTab(BedrockTabContext context) {
        if (context.hasPermission(COMMAND_HEAL_OTHER)) {
            context.playerCompletion(0);
        }
    }

}
