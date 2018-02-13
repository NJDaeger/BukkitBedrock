package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.njdaeger.bedrock.Gamemode;
import com.njdaeger.bedrock.SpeedType;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.command.BedrockTabContext;
import com.njdaeger.bedrock.api.user.IUser;

import static com.njdaeger.bedrock.Message.*;
import static com.njdaeger.bedrock.Permission.*;

public final class BasicCommands {

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
                
        BedrockCommand speedCommand = BedrockCommand.builder(bedrock, "speed")
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .permission(COMMAND_SPEED, COMMAND_SPEED_OTHER)
                .description(bedrock.translate(SPEED_DESC))
                .usage(bedrock.translate(SPEED_USAGE))
                .completer(this::speedTab)
                .executor(this::speed)
                .minArgs(1)
                .maxArgs(2)
                .aliases("walkspeed", "flyspeed")
                .build();
                
        
        bedrock.registerCommand(afkCommand, healCommand, gamemodeCommand, speedCommand);
        
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
    
    /**
     * gamemode command
     * @param context command context
     */
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
    
    /**
     * gamemode command for console
     * @param context command context
     */
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
    
    /**
     * gamemode tab completion
     * @param context tab context
     */
    private void gamemodeTab(BedrockTabContext context) {
        context.gamemodeCompletionAt(0);
        if (context.hasPermission(COMMAND_GAMEMODE_OTHER)) {
            context.playerCompletion(1);
        }
    }
    
    private void speed(BedrockCommandContext context) {
        
        IUser user;
        double speed;
        SpeedType type;
        
        //
        //Checking who the sender is referring to
        //
        if (context.isLength(1)) {
            if (context.getSender().getType() == SenderType.CONSOLE) {
                context.notEnoughArgs(2, 1);
                return;
            }
            user = context.getUser();
        } else {
            //We know the sender is affecting another player, do a permission check
            if (!context.hasPermission(COMMAND_SPEED_OTHER)) {
               context.noPermission(COMMAND_SPEED_OTHER);
               return;
            }
            user = context.getUser(1);
        }
        
        //Check if the user is null. It shouldnt be if the sender is acting upon themselves
        if (user == null) {
            context.userNotFound(context.argAt(1));
            return;
        }
        
        //
        //Gotta check the alias to see if the type is specified
        //
        switch (context.getAlias().toLowerCase()) {
        case "walkspeed":
            if (!context.hasPermission(COMMAND_SPEED_WALK)) {
                context.noPermission(COMMAND_SPEED_WALK);
                return;
            }
            type = SpeedType.WALKING;
            break;
        case "flyspeed":
            if (!context.hasPermission(COMMAND_SPEED_FLY)) {
                context.noPermission(COMMAND_SPEED_FLY);
                return;
            }
            type = SpeedType.FLYING;
            break;
        default:
            //Nothing was specified, so we gotta get the type on our own
            type = user.getMovementType();
        }
    
        //Check if theyre trying to reset their speed.
        if (context.isLength(1) && context.argAt(0).equalsIgnoreCase("reset")) {
            speed = 1;
        } else {
            try {
                speed = Double.parseDouble(context.argAt(0));
            } catch (NumberFormatException e) {
                context.pluginMessage(ERROR_NOT_A_NUMBER, context.argAt(0));
                return;
            }
            if (speed > 10) {
                speed = 10;
            }
            if (speed < 0) {
                speed = 0;
            }
        }
        
        if (context.isLength(2)) {
            user.pluginMessage(SPEED_OTHER_RECEIVER, context.getDisplayName(), type.getNicename(), speed);
            context.pluginMessage(SPEED_OTHER_SENDER, user.getDisplayName(), type.getNicename(), speed);
        } else {
            context.pluginMessage(SPEED_SELF, type.getNicename(), speed);
        }
        
        user.setSpeed(type, Float.parseFloat(Double.toString(speed)));
    }
    
    private void speedTab(BedrockTabContext context) {
        context.completionAt(0, 0, 10);
        context.playerCompletion(1);
    }
    
}