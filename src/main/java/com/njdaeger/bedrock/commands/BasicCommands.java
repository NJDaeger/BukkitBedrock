package com.njdaeger.bedrock.commands;

import com.njdaeger.bci.SenderType;
import com.njdaeger.bci.base.BCIException;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Gamemode;
import com.njdaeger.bedrock.api.command.BedrockBuilder;
import com.njdaeger.bedrock.api.command.CommandContext;
import com.njdaeger.bedrock.api.command.TabContext;
import com.njdaeger.bedrock.api.command.exceptions.BedrockException;
import com.njdaeger.bedrock.api.command.exceptions.UnknownGamemodeException;
import com.njdaeger.bedrock.api.user.IUser;

import java.util.stream.Stream;

import static com.njdaeger.bedrock.api.lang.Message.*;
import static com.njdaeger.bedrock.api.Permission.*;

public final class BasicCommands {

    public BasicCommands() {
        BedrockBuilder.builder("afk")
                      .description(AFK_DESC)
                      .usage(AFK_USAGE)
                      .senders(SenderType.PLAYER)
                      .permission(COMMAND_AFK)
                      .aliases("away", "brb")
                      .executor(this::afk)
                      .build()
                      .register();

        BedrockBuilder.builder("heal")
                      .senders(SenderType.PLAYER, SenderType.CONSOLE)
                      .permission(COMMAND_HEAL, COMMAND_HEAL_OTHER)
                      .description(HEAL_DESC)
                      .usage(HEAL_USAGE)
                      .completer(this::healTab)
                      .aliases("fillhearts")
                      .executor(this::heal)
                      .maxArgs(1)
                      .build()
                      .register();

        BedrockBuilder.builder("gamemode")
                      .senders(SenderType.PLAYER, SenderType.CONSOLE)
                      .permission(COMMAND_GAMEMODE)
                      .description(GAMEMODE_DESC)
                      .usage(GAMEMODE_USAGE)
                      .executor(this::gamemode)
                      .completer(this::gamemodeTab)
                      .maxArgs(2)
                      .minArgs(1)
                      .aliases("gmode", "gm")
                      .build()
                      .register();
        /*
        BedrockCommand speedCommand = BedrockCommand.builder("speed")
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .permission(COMMAND_SPEED, COMMAND_SPEED_OTHER)
                .description(SPEED_DESC)
                .usage(SPEED_USAGE)
                .completer(this::speedTab)
                .executor(this::speed)
                .minArgs(1)
                .maxArgs(2)
                .aliases("walkspeed", "flyspeed")
                .build();
                
        BedrockCommand backCommand = BedrockCommand.builder("back")
                .senders(SenderType.PLAYER)
                .permission(COMMAND_BACK)
                .description(BACK_DESC)
                .usage(BACK_USAGE)
                .executor(this::back)
                .maxArgs(0)
                .build();
               
        BedrockCommand nickCommand = BedrockCommand.builder("nick")
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .description(NICK_DESC)
                .usage(NICK_USAGE)
                .permission(COMMAND_NICK_OTHER, COMMAND_NICK)
                .aliases("setnick", "nickname", "displayname")
                .minArgs(1)
                .maxArgs(2)
                .executor(this::nick)
                .build();
        
        BedrockCommand infoBoardCommand = BedrockCommand.builder("infoboard")
                .senders(SenderType.PLAYER)
                .description(INFOBOARD_DESC)
                .usage(INFOBOARD_USAGE)
                .aliases("serverinfo", "resources", "sinfo", "iboard")
                .maxArgs(0)
                .executor(this::infoBoard)
                .build();
        */

    }

    /**
     * afk command
     *
     * @param context command context
     */
    private void afk(CommandContext context) {

        String message = Bedrock.translate(AFK_AWAY_MESSAGE, context.getDisplayName());

        if (context.hasArgs()) {
            if (context.hasPermission(COMMAND_AFK_MESSAGE)) {
                message = Bedrock.translate(AFK_AWAY_MESSAGE_MOREINFO, context.getDisplayName(), context.joinArgs());
            }
        }
        if (!context.asUser().isAfk()) {
            context.asUser().setAfk(true, message);
        }
    }

    /**
     * heal command
     *
     * @command /heal [user]
     */
    private void heal(CommandContext context) throws BCIException {

        //If the sender was the console, we dont need to continue executing this command.
        if (context.subCommand(SenderType.CONSOLE, this::healConsole)) return;

        //We know the sender is a player
        IUser user;

        //Check if the sender is trying to use this on someone
        if (context.hasArgs()) {

            if (!context.hasPermission(COMMAND_HEAL_OTHER)) context.noPermission(COMMAND_HEAL_OTHER);

            if (!context.isUserAt(0)) context.userNotFound(0);
            user = context.userAt(0);

            user.getBase().setHealth(20);
            user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getDisplayName());
            context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getDisplayName());
            return;
        }
        user = context.asUser();
        user.getBase().setHealth(20);
        user.pluginMessage(HEAL_SELF_MESSAGE);
    }

    /**
     * heal command for console
     *
     * @param context command context
     */
    private void healConsole(CommandContext context) throws BCIException {
        //Since the command is already set to not be allowed to have more than 1 we know it has less than 1
        if (!context.isLength(1)) context.notEnoughArgs(1, 0);

        if (!context.isUserAt(0)) context.userNotFound(0);
        IUser user = context.userAt(0);

        user.getBase().setHealth(20);
        user.pluginMessage(HEAL_OTHER_MESSAGE_RECEIVER, context.getDisplayName());
        context.pluginMessage(HEAL_OTHER_MESSAGE_SENDER, user.getDisplayName());
    }

    /**
     * tab completion for heal command
     *
     * @param context tab context
     */
    private void healTab(TabContext context) {
        context.playerCompletionIf(c -> c.isLength(0) && context.hasPermission(COMMAND_HEAL_OTHER));
    }

    /**
     * gamemode command
     *
     * @param context command context
     */
    private void gamemode(CommandContext context) throws BCIException {

        if (context.subCommand(SenderType.CONSOLE, this::gamemodeConsole)) return;
        IUser user = context.asUser();

        if (context.isLength(2)) {
            if (!context.hasPermission(COMMAND_GAMEMODE_OTHER)) context.noPermission(COMMAND_GAMEMODE_OTHER);
            if (!context.isUserAt(1)) context.userNotFound(1);
            user = context.userAt(1);
        }

        Gamemode mode = context.gamemodeAt(0);
        if (mode == null) throw new UnknownGamemodeException(context.argAt(0));
        if (!context.hasPermission(mode.getPermission())) context.noPermission(mode.getPermission());

        user.setGamemode(mode);

        if (context.isLength(2)) {
            user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getDisplayName(), mode.getNicename());
            context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getDisplayName(), mode.getNicename());
        } else context.pluginMessage(GAMEMODE_SELF, mode.getNicename());


    }

    /**
     * gamemode command for console
     *
     * @param context command context
     */
    private void gamemodeConsole(CommandContext context) throws BedrockException {
        if (context.isLength(1)) context.notEnoughArgs(2, 1);

        if (!context.isUserAt(1)) context.userNotFound(1);
        IUser user = context.userAt(1);

        Gamemode mode = context.gamemodeAt(0);
        if (mode == null) throw new UnknownGamemodeException(context.argAt(0));

        user.setGamemode(mode);
        user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getDisplayName(), mode.getNicename());
        context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getDisplayName(), mode.getNicename());
    }

    /**
     * gamemode tab completion
     *
     * @param context tab context
     */
    private void gamemodeTab(TabContext context) {
        context.completionAt(0, Stream.of(Gamemode.values()).map(Gamemode::getNicename).toArray(String[]::new));
        context.playerCompletionIf(c -> c.hasPermission(COMMAND_GAMEMODE_OTHER) && c.isLength(1));
    }
}
    /*
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
    
    private void back(BedrockCommandContext context) {
        context.getUser().teleport(context.getUser().getLastLocation());
        context.pluginMessage(BACK_MESSAGE);
    }
    
    //Remake this entire command it looks like shit
    private void nick(BedrockCommandContext context) {
        
        boolean reset = context.argAt(0).equalsIgnoreCase("off") || context.argAt(0).equalsIgnoreCase("reset");
        IUser user;
        
        if (context.isLength(1)) {
            if (context.isConsole()) {
                context.notEnoughArgs(2, 1);
                return;
            }
            user = context.getUser();
            if (reset) {
                user.setDisplayName(user.getName());
                user.pluginMessage(NICK_RESET_SELF);
                return;
            }
            user.setDisplayName(context.argAt(0));
            context.pluginMessage(NICK_SELF_MESSAGE, context.argAt(0));
            return;
        }
        
        user = context.getUser(1);
        
        if (user == null) {
            context.userNotFound(context.argAt(1));
            return;
        }
        
        if (reset) {
            user.setDisplayName(user.getName());
            user.pluginMessage(NICK_RESET_OTHER_RECEIVER, context.getDisplayName());
            context.pluginMessage(NICK_RESET_OTHER_SENDER, user.getDisplayName());
            return;
        }
    
        user.pluginMessage(NICK_OTHER_RECEIVER, context.getDisplayName(), context.argAt(0));
        context.pluginMessage(NICK_OTHER_SENDER, user.getDisplayName(), context.argAt(0));
        user.setDisplayName(context.argAt(0));
    }
    
    private void infoBoard(BedrockCommandContext context) {
        boolean run = true;
        Message msg = Message.INFOBOARD_ENABLED;
        if (context.getUser().hasInfobard()) {
            run = false;
            msg = Message.INFOBOARD_DISABLED;
        }
        context.getUser().runInfobard(run);
        context.pluginMessage(msg);
        
    }
}*/
