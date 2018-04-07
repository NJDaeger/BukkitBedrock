package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;

import static com.njdaeger.bedrock.api.Message.*;
import static com.njdaeger.bedrock.api.Bedrock.translate;

public final class ChatCommands {

    private final IBedrock bedrock;
    
    public ChatCommands() {
        this.bedrock = Bedrock.getBedrock();
        bedrock.getChannels().stream().map(IChannel::getName).forEach(System.out::println);
        bedrock.getChannels().forEach(c ->
            bedrock.registerCommand(BedrockCommand.builder(c.getName())
            .description(translate(CUSTOM_CHANNEL_COMMAND_DESC, c.getName()))
            .usage(translate(CUSTOM_CHANNEL_COMMAND_USAGE, c.getName()))
            .permission(c.getPermission() == null ? "" : c.getPermission())
            .executor(this::customChannel)
            .senders(SenderType.PLAYER, SenderType.CONSOLE)
            .build()
        ));
        
        bedrock.registerCommand(BedrockCommand.builder("channel")
            .executor(this::channel)
            .description("soon")
            .usage("soon")
            .minArgs(1)
            .build());
        
    }
    
    private void customChannel(BedrockCommandContext context) {
        if (context.hasArgs()) bedrock.getChannel(context.getAlias()).sendMessage(context.joinArgs());
        else {
            //size
            //name
            //display
            //close
            //prefix
            //users
        }
        /*
       
        /[channelname] [message]                        Sends a message to this specific channel even if its not currently selected. (Still needs channel perm)
        /[channelname]                                  Gives information about this channel
        
         */
        
    }
    
    private void channel(BedrockCommandContext context) {
        
        //String subcommand
    
        if (context.subCommandAt(0, "new", true, this::newChannel)) return;
        if (context.subCommandAt(0, "temp", true, this::tempChannel)) return;
        if (context.subCommandAt(0, "delete", true, this::deleteChannel)) return;
        
        switch (context.argAt(0).toLowerCase()) {
        case "join":
            context.getUser().addChannel(bedrock.getChannel(context.argAt(1)));
            break;
        case "leave":
            context.getUser().leaveChannel(bedrock.getChannel(context.argAt(1)));
            break;
        case "kick":
        case "add":
        case "select":
        case "display":
            context.getUser().runChannelDisplay(!context.getUser().hasChannelDisplay());
        default:
            //We dont know what arg was put in
        }
        
        /*
        
        Create a new channel, delete a channel, list of channels
        
        
        /channel new <name> <prefix> <display> [permission]     Creates a new channel saved by the server
        /channel temp <name> <prefix> <display> [permission]    Creates a new temp channel which is removed when empty
        /channel delete <name>                                  Deletes a channel. Channel must be made by a user or the user must have permission
        /channel join <name>                                    Puts a channel back in this users channel selection list
        /channel leave <name>                                   Removes a channel from the channel selection list
        /channel kick <user> [channel]                          Kicks a user from a channel. If none is specified, the user is kicked from the senders current channel
        /channel add <user> [channel]                           Adds a user to a channel. If no channel is specified, the user is added to the senders current channel.
        /channel select <name>                                  Sets the given channel to the currently selected channel.
        /channel display                                        Toggles the channel scoreboard display
        
         */
    }
    
    private void newChannel(BedrockCommandContext context) {
        if (context.length() < 3) {
            context.notEnoughArgs(3, context.length());
            return;
        }
        String name = context.argAt(1);
        String prefix = context.argAt(2);
        String display = context.argAt(3);
        String permission = context.argAt(4);
    
        if (bedrock.hasChannel(name)) {
            //Channel exists
            return;
        }
    
        if (!Display.contains(display)) {
            //Wtf is this display
            return;
        }
        bedrock.createChannel(name, prefix, Display.valueOf(display), permission);
        bedrock.registerCommand(BedrockCommand.builder(name)
                .description(translate(CUSTOM_CHANNEL_COMMAND_DESC, name))
                .usage(translate(CUSTOM_CHANNEL_COMMAND_USAGE, name))
                .permission(permission == null ? "" : permission)
                .executor(this::customChannel)
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .build()
        );
    }
    
    private void tempChannel(BedrockCommandContext context) {
        if (context.length() < 3) {
            context.notEnoughArgs(3, context.length());
            return;
        }
        String name = context.argAt(1);
        String prefix = context.argAt(2);
        String display = context.argAt(3);
        String permission = context.argAt(4);
    
        if (bedrock.hasChannel(name)) {
            //Channel exists
            return;
        }
    
        if (!Display.contains(display)) {
            //Wtf is this display
            return;
        }
        bedrock.createChannel(name, prefix, Display.valueOf(display), permission, Close.CHANNEL_EMPTY);
        bedrock.registerCommand(BedrockCommand.builder(name)
                .description(translate(CUSTOM_CHANNEL_COMMAND_DESC, name))
                .usage(translate(CUSTOM_CHANNEL_COMMAND_USAGE, name))
                .permission(permission == null ? "" : permission)
                .executor(this::customChannel)
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .build()
        );
    }
    
    private void deleteChannel(BedrockCommandContext context) {
        if (context.length() < 2) {
            context.notEnoughArgs(2, context.length());
            return;
        }
        if (context.length() > 2) {
            context.tooManyArgs(2, context.length());
            return;
        }
        String name = context.argAt(1);
        
        if (!bedrock.hasChannel(name)) {
            //Cant find dis channel
            return;
        }
        bedrock.removeChannel(name);
    }
    
    /*
     * Chat channel system
     *
     * Channels will be stored in the channels.yml
     *
     *
     * channelname:
     *      commandName: "" (String)
     *      owner: "" (UUID nullable)
     *      prefix: "" (String nullable)
     *      display: PREFIX, NAME, NONE   (What to display in chat before the users name. PREFIX being the channel prefix if it exists, NAME being the channel name, and NONE being nothing.)
     *      permission: "" (String nullable)
     *      closeOnExit: false (Whether to close the channel when the owner leaves, the channel must have an owner in order for this to work)
     *
     * Messages: CHANNEL_MESSAGE_FORMAT - "&7[&4{0}&7]&r{1}&7: &r{2}"
     *
     * Commands:
     *
     *      /[channelname] <message> //Send a message to this channel if the sender has permission to be in this channel
     *      /[channelname] //Will join or leave the channel.
     *
     *
     */
    

}
