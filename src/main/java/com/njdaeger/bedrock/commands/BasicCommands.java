package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.command.BedrockTabContext;
import com.njdaeger.bedrock.api.user.IUser;

import static com.njdaeger.bedrock.Message.*;
import static com.njdaeger.bedrock.Permission.*;

public class BasicCommands {

    private final IBedrock bedrock;
    
    public BasicCommands(IBedrock bedrock) {
        this.bedrock = bedrock;
        BedrockCommand afkCommand = BedrockCommand.builder(bedrock, "afk")
                .executor(this::afk)
                .description(bedrock.translate(AFK_DESC))
                .usage(bedrock.translate(AFK_USAGE))
                .permission(COMMAND_AFK.toString())
                .aliases("away", "brb")
                .senders(SenderType.PLAYER)
                .build();
        
        BedrockCommand healCommand = BedrockCommand.builder(bedrock, "heal")
                .permission(COMMAND_HEAL.toString(), COMMAND_HEAL_OTHER.toString())
                .description(bedrock.translate(HEAL_DESC))
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .usage(bedrock.translate(HEAL_USAGE))
                .aliases("fillhearts")
                .executor(this::heal)
                .completer(this::healTab)
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
        
        //If the sender was the console, we dont need to continue executing this command.
        if (context.subCommand(SenderType.CONSOLE, this::healConsole)) return;
        
        //We know the sender is a player
        IUser user;
        
        //Check if the sender is trying to use this on someone
        if (context.hasArgs()) {
            
            if (!context.hasPermission(COMMAND_HEAL_OTHER)) {
                context.noPermission(COMMAND_HEAL_OTHER);
                return;
            }
            user = context.getUser(context.argAt(0));
            
            if (user == null) {
                context.userNotFound(context.argAt(0));
                return;
            }
            
            user.get().setHealth(20);
            user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getName());
            context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getName());
            return;
        }
        user = context.getUser();
        user.get().setHealth(20);
        user.pluginMessage(HEAL_SELF_MESSAGE);
    }
    
    private void healConsole(BedrockCommandContext context) {
        //Since the command is already set to not be allowed to have more than 1 we know it has less than 1
        if (!context.isLength(1)) context.notEnoughArgs(1, 0);
        IUser user = context.getUser(context.argAt(0));
        if (user == null) {
            context.userNotFound(context.argAt(0));
            return;
        }
        user.get().setHealth(20);
        user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getName());
        context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getName());
    }
    
    private void healTab(BedrockTabContext context) {
        if (context.hasPermission(COMMAND_HEAL_OTHER)) {
            context.playerCompletion(0);
        }
    }
}
