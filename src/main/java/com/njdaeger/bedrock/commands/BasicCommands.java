package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.njdaeger.bedrock.Gamemode;
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
                .description(bedrock.translate(AFK_DESC))
                .usage(bedrock.translate(AFK_USAGE))
                .senders(SenderType.PLAYER)
                .permission(COMMAND_AFK)
                .aliases("away", "brb")
                .executor(this::afk)
                .build();
        
        BedrockCommand healCommand = BedrockCommand.builder(bedrock, "heal")
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .permission(COMMAND_HEAL, COMMAND_HEAL_OTHER)
                .description(bedrock.translate(HEAL_DESC))
                .usage(bedrock.translate(HEAL_USAGE))
                .completer(this::healTab)
                .aliases("fillhearts")
                .executor(this::heal)
                .maxArgs(1)
                .build();
        
        BedrockCommand gamemodeCommand = BedrockCommand.builder(bedrock, "gamemode")
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .permission(COMMAND_GAMEMODE)
                .description(bedrock.translate(GAMEMODE_DESC))
                .usage(bedrock.translate(GAMEMODE_USAGE))
                .executor(this::gamemode)
                .completer(this::gamemodeTab)
                .maxArgs(2)
                .minArgs(1)
                .aliases("gmode", "gm")
                .build();
                
        
        bedrock.registerCommand(afkCommand, healCommand, gamemodeCommand);
        
    }
    
    /**
     * afk command
     * @param context command context
     */
    private void afk(BedrockCommandContext context) {
        
        String message = bedrock.translate(AFK_AWAY_MESSAGE, context.getDisplayName());
        
        if (context.hasArgs()) {
            if (context.hasPermission(COMMAND_AFK_MESSAGE)) {
                message = bedrock.translate(AFK_AWAY_MESSAGE_MOREINFO, context.getDisplayName(), context.joinArgs());
            }
        }
        if (!context.getUser().isAfk()) {
            context.getUser().setAfk(true, message);
        }
    }
    
    /**
     * heal command
     * @param context command context
     */
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
            user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getDisplayName());
            context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getDisplayName());
            return;
        }
        user = context.getUser();
        user.get().setHealth(20);
        user.pluginMessage(HEAL_SELF_MESSAGE);
    }
    
    /**
     * heal command for console
     * @param context command context
     */
    private void healConsole(BedrockCommandContext context) {
        //Since the command is already set to not be allowed to have more than 1 we know it has less than 1
        if (!context.isLength(1)) {
            context.notEnoughArgs(1, 0);
            return;
        }
        IUser user = context.getUser(context.argAt(0));
        if (user == null) {
            context.userNotFound(context.argAt(0));
            return;
        }
        user.get().setHealth(20);
        user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getDisplayName());
        context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getDisplayName());
    }
    
    /**
     * tab completion for heal command
     * @param context tab context
     */
    private void healTab(BedrockTabContext context) {
        if (context.hasPermission(COMMAND_HEAL_OTHER)) {
            context.playerCompletion(0);
        }
    }
    
    private void gamemode(BedrockCommandContext context) {
        if (context.subCommand(SenderType.CONSOLE, this::gamemodeConsole)) return;
        
        IUser user = context.getUser();
        
        if (context.isLength(2)) {
            if(!context.hasPermission(COMMAND_GAMEMODE_OTHER)) {
                context.noPermission(COMMAND_GAMEMODE_OTHER);
                return;
            }
            
            user = context.getUser(context.argAt(1));
            if (user == null) {
                context.userNotFound(context.argAt(1));
                return;
            }
        }
    
        Gamemode mode = Gamemode.resolveGamemode(context.argAt(0));
    
        switch (mode) {
        case SURVIVAL:
            if (!context.hasPermission(COMMAND_GAMEMODE_SURVIVAL)) {
                context.noPermission(COMMAND_GAMEMODE_SURVIVAL);
                return;
            }
        case CREATIVE:
            if (!context.hasPermission(COMMAND_GAMEMODE_CREATIVE)) {
                context.noPermission(COMMAND_GAMEMODE_CREATIVE);
                return;
            }
        case ADVENTURE:
            if (!context.hasPermission(COMMAND_GAMEMODE_ADVENTURE)) {
                context.noPermission(COMMAND_GAMEMODE_ADVENTURE);
                return;
            }
        case SPECTATOR:
            if (!context.hasPermission(COMMAND_GAMEMODE_SPECTATOR)) {
                context.noPermission(COMMAND_GAMEMODE_SPECTATOR);
                return;
            }
        default:
            user.setGamemode(mode);
            if (context.isLength(2)) {
                user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getDisplayName(), mode.getNicename());
                context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getDisplayName(), mode.getNicename());
            } else context.pluginMessage(GAMEMODE_SELF, mode.getNicename());
        }
        
    }
    
    private void gamemodeConsole(BedrockCommandContext context) {
        if (context.isLength(1)) {
            context.notEnoughArgs(2, 1);
            return;
        }
        IUser user = context.getUser(1);
        if (user == null) {
            context.userNotFound(context.argAt(1));
            return;
        }
        Gamemode mode = Gamemode.resolveGamemode(context.argAt(0));
        user.setGamemode(mode);
        user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getDisplayName(), mode.getNicename());
        context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getDisplayName(), mode.getNicename());
    }
    
    private void gamemodeTab(BedrockTabContext context) {
        context.gamemodeCompletionAt(0);
        if (context.hasPermission(COMMAND_GAMEMODE_OTHER)) {
            context.playerCompletion(1);
        }
    }
    
}
