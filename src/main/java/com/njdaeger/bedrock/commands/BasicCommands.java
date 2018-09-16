package com.njdaeger.bedrock.commands;

import com.njdaeger.bci.SenderType;
import com.njdaeger.bci.base.BCIException;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Gamemode;
import com.njdaeger.bedrock.api.SpeedType;
import com.njdaeger.bedrock.api.command.BedrockBuilder;
import com.njdaeger.bedrock.api.command.CommandContext;
import com.njdaeger.bedrock.api.command.TabContext;
import com.njdaeger.bedrock.api.command.exceptions.BedrockException;
import com.njdaeger.bedrock.api.command.exceptions.UnknownGamemodeException;
import com.njdaeger.bedrock.api.config.ISettings;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.Location;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static com.njdaeger.bedrock.api.Bedrock.translate;
import static com.njdaeger.bedrock.api.Permission.*;
import static com.njdaeger.bedrock.api.lang.Message.*;

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

        BedrockBuilder.builder("speed")
                      .senders(SenderType.PLAYER, SenderType.CONSOLE)
                      .permission(COMMAND_SPEED, COMMAND_SPEED_OTHER)
                      .description(SPEED_DESC)
                      .usage(SPEED_USAGE)
                      .completer(this::speedTab)
                      .executor(this::speed)
                      .minArgs(1)
                      .maxArgs(2)
                      .aliases("walkspeed", "flyspeed")
                      .build()
                      .register();

        BedrockBuilder.builder("back")
                      .senders(SenderType.PLAYER)
                      .permission(COMMAND_BACK)
                      .description(BACK_DESC)
                      .usage(BACK_USAGE)
                      .executor(this::back)
                      .maxArgs(0)
                      .build()
                      .register();

        BedrockBuilder.builder("nick")
                      .senders(SenderType.PLAYER, SenderType.CONSOLE)
                      .description(NICK_DESC)
                      .usage(NICK_USAGE)
                      .permission(COMMAND_NICK_OTHER, COMMAND_NICK)
                      .aliases("setnick", "nickname", "displayname")
                      .minArgs(1)
                      .maxArgs(2)
                      .executor(this::nick)
                      .build()
                      .register();
        /*
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

        ISettings settings = context.getSettings();

        Location location = context.getLocation();
        String message = AFK_AWAY_MESSAGE.translate(
                context.getName(),
                context.getDisplayName(),
                location.getBlockX(),
                location.getBlockY(),
                location.getBlockZ(),
                location.getWorld().getName());

        boolean hasReason = context.hasArgs() && settings.isAfkMoreInfoEnabled() && context.hasPermission(COMMAND_AFK_MESSAGE);

        if (hasReason) {
            message = AFK_AWAY_MESSAGE_MOREINFO.translate(
                    context.getName(),
                    context.getDisplayName(),
                    location.getBlockX(),
                    location.getBlockY(),
                    location.getBlockZ(),
                    location.getWorld().getName(),
                    context.joinArgs());

        }
        if (!context.asUser().isAfk()) {
            context.asUser().setAfk(true, hasReason, translate(message));
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

        ISettings settings = context.getSettings();

        if (context.subCommand(SenderType.CONSOLE, this::gamemodeConsole)) return;
        IUser user = context.asUser();

        if (context.isLength(2)) {
            if (!context.hasPermission(COMMAND_GAMEMODE_OTHER)) context.noPermission(COMMAND_GAMEMODE_OTHER);
            if (!context.isUserAt(1)) context.userNotFound(1);
            user = context.userAt(1);
        }

        Gamemode mode = context.gamemodeAt(0);
        if (mode == null) throw new UnknownGamemodeException(context.argAt(0));
        if (settings.hasGamemodeSpecificPermissions() && !context.hasPermission(mode.getPermission())) context.noPermission(mode.getPermission());

        user.setGamemode(mode);

        if (context.isLength(2)) {
            user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getName(), context.getDisplayName(), mode.getNicename());
            context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getName(), user.getDisplayName(), mode.getNicename());
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
        user.pluginMessage(GAMEMODE_OTHER_RECEIVER, context.getName(), context.getDisplayName(), mode.getNicename());
        context.pluginMessage(GAMEMODE_OTHER_SENDER, user.getName(), user.getDisplayName(), mode.getNicename());
    }

    /**
     * gamemode tab completion
     *
     * @param context tab context
     */
    private void gamemodeTab(TabContext context) {
        ISettings settings = Bedrock.getSettings();
        String[] gamemodes =
                settings.hasGamemodeSpecificPermissions() ?
                        Stream.of(Gamemode.values()).filter(g -> context.hasPermission(g.getPermission())).map(Gamemode::getNicename).toArray(String[]::new) :
                        Stream.of(Gamemode.values()).map(Gamemode::getNicename).toArray(String[]::new);

        context.completionAt(0, gamemodes);
        context.playerCompletionIf(c -> c.hasPermission(COMMAND_GAMEMODE_OTHER) && c.isLength(1));
    }

    private void speed(CommandContext context) throws BCIException {

        ISettings settings = context.getSettings();

        //Send the speed off to the console if the console is sending it.
        if (context.subCommand(SenderType.CONSOLE, this::speedConsole)) return;

        //Get the speed from the command arguments
        float speed = context.floatAt(0, 1.F);

        //Doing the pre checks on the speed to keep it within bounds.
        //If the speed is greater than 10 or less than -10 we need to set
        //the speed to 10 (for greater than 10) or -10 (for less than 10)
        speed = (speed > 10 || speed < -10 ? ((speed < -10) ? -10 : 10) : speed);

        //Retrieving the user if possible... or throwing an error, that works too
        IUser user = null;
        if (context.isLength(1)) user = context.asUser();
        else if (context.isUserAt(1)) user = context.userAt(1);
        else context.userNotFound(1);

        //Retrieving the speed type being set.
        SpeedType type;
        if (context.getAlias().equalsIgnoreCase("walkspeed")) type = SpeedType.WALKING;
        else if (context.getAlias().equalsIgnoreCase("flyspeed")) type = SpeedType.FLYING;
        else type = user.getMovementType();

        //Check if the user has the permissions to change the specific speed types
        if (settings.hasSpeedSpecificPermissions() && !context.hasPermission(type.getPermission())) context.noPermission(type.getPermission());

        //negative speed checks
        if (speed < 0) {

            float min = type == SpeedType.FLYING ? settings.getMinFlySpeed() : settings.getMinWalkSpeed();

            //Just set the speed to 0 if negatives arent allowed
            if (!settings.allowNegativeSpeed()) speed = 0;
            else if (settings.hasNegativeSpeedPermission() && !context.hasPermission(COMMAND_SPEED_NEGATIVE)) context.noPermission(COMMAND_SPEED_NEGATIVE);

            //If the speed is below the minimum and the bypass is either not enabled or is enabled and the user doesnt have permission, set the speed to the min.
            if ((speed < min) && (!settings.hasMinSpeedBypass() || (settings.hasMinSpeedBypass() && !context.hasPermission(COMMAND_SPEED_MIN_BYPASS)))) speed = min;

        }

        float max = type == SpeedType.FLYING ? settings.getMaxFlySpeed() : settings.getMaxWalkSpeed();

        if (speed > max && (!settings.hasMaxSpeedBypass() || (settings.hasMaxSpeedBypass() && !context.hasPermission(COMMAND_SPEED_MAX_BYPASS)))) speed = max;

        user.setSpeed(type, speed);
        if (context.isLength(1)) context.pluginMessage(SPEED_SELF, type.getNicename(), speed);
        else {
            context.pluginMessage(SPEED_OTHER_SENDER, user.getName(), user.getDisplayName(), type.getNicename(), speed);
            user.pluginMessage(SPEED_OTHER_RECEIVER, context.getName(), context.getDisplayName(), type.getNicename(), speed);
        }

    }

    private void speedConsole(CommandContext context) throws BedrockException {
        if (context.isLength(1)) context.notEnoughArgs(2, 1);

        float speed = context.floatAt(0, 1.F);

        if (!context.isUserAt(1)) context.userNotFound(1);
        IUser user = context.userAt(1);

        SpeedType type;
        if (context.getAlias().equalsIgnoreCase("walkspeed")) type = SpeedType.WALKING;
        else if (context.getAlias().equalsIgnoreCase("flyspeed")) type = SpeedType.FLYING;
        else type = user.getMovementType();

        user.setSpeed(type, speed);
        context.pluginMessage(SPEED_OTHER_SENDER, user.getName(), user.getDisplayName(), type, speed);
        user.pluginMessage(SPEED_OTHER_RECEIVER, context.getName(), context.getDisplayName(), type, speed);
    }

    private void speedTab(TabContext context) {
        context.completionAt(0, tabContext -> IntStream.rangeClosed(0, 10).mapToObj(Integer::toString).collect(Collectors.toList()));
        context.playerCompletionAt(1);
    }

    //todo: back history?
    // history max recording
    // save history
    // forward
    // back [user]
    private void back(CommandContext context) {
        IUser user = context.asUser();
        context.asUser().teleport(user.getLastLocation());
        context.pluginMessage(BACK_MESSAGE, user.getName(), user.getDisplayName(), user.getX(), user.getY(), user.getZ(), user.getWorld().getName());
    }

    /* todo options:

        characterWhitelist
        inverseWhitelist
        maxlength
        multisame
        resetwords


     */
    private void nick(CommandContext context) throws BedrockException {
        
        boolean reset = context.argAt(0).equalsIgnoreCase("off") || context.argAt(0).equalsIgnoreCase("reset");
        IUser user;
        
        if (context.isLength(1)) {
            if (context.isConsole()) context.notEnoughArgs(2, 1);

            user = context.asUser();
            if (reset) {
                user.pluginMessage(NICK_RESET_SELF, user.getDisplayName(), user.getName());
                user.setDisplayName(user.getName());
                return;
            }
            context.pluginMessage(NICK_SELF_MESSAGE, user.getDisplayName(), context.argAt(0));
            user.setDisplayName(context.argAt(0));
            return;
        }

        if (!context.isUserAt(1)) context.userNotFound(1);
        user = context.userAt(1);
        
        if (reset) {
            user.pluginMessage(NICK_RESET_OTHER_RECEIVER, user.getDisplayName(), user.getName(), context.getName(), context.getDisplayName());
            context.pluginMessage(NICK_RESET_OTHER_SENDER, user.getDisplayName(), user.getName(), user.getName(), user.getDisplayName());
            user.setDisplayName(user.getName());
            return;
        }
    
        user.pluginMessage(NICK_OTHER_RECEIVER, user.getDisplayName(), context.argAt(0), context.getName(), context.getDisplayName());
        context.pluginMessage(NICK_OTHER_SENDER, user.getDisplayName(), context.argAt(0), user.getName(), user.getDisplayName());
        user.setDisplayName(context.argAt(0));
    }
    
    /*private void infoBoard(BedrockCommandContext context) {
        boolean run = true;
        Message msg = Message.INFOBOARD_ENABLED;
        if (context.getUser().hasInfobard()) {
            run = false;
            msg = Message.INFOBOARD_DISABLED;
        }
        context.getUser().runInfobard(run);
        context.pluginMessage(msg);
        
    }*/
}
