package com.njdaeger.bedrock.commands;

import com.coalesce.core.SenderType;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;

import static com.njdaeger.bedrock.api.Message.*;
import static com.njdaeger.bedrock.api.Bedrock.translate;

public final class ChatCommands {

    private final IBedrock bedrock;
    
    public ChatCommands() {
        this.bedrock = Bedrock.getBedrock();
        bedrock.getChannels().forEach(c ->
            bedrock.registerCommand(BedrockCommand.builder(c.getName())
            .description(translate(CUSTOM_CHANNEL_COMMAND_DESC, c.getName()))
            .usage(translate(CUSTOM_CHANNEL_COMMAND_USAGE, c.getName()))
            .permission(c.getPermission())
            .executor(this::customChannel)
            .senders(SenderType.PLAYER, SenderType.CONSOLE)
            .build()
        ));
    }
    
    private void customChannel(BedrockCommandContext context) {
        
        /*
        
        /[channelname] [message]                        Sends a message to this specific channel even if its not currently selected. (Still needs channel perm)
        /[channelname]                                  Gives information about this channel
        
         */
        
    }
    
    private void channel(BedrockCommandContext context) {
        /*
        
        Create a new channel, delete a channel, list of channels
        
        
        /channel new <name> <prefix> [permission]       Creates a new channel saved by the server and owned by the player
        /channel temp <name> <prefix>                   Creates a new temp channel which is removed when the owner exits
        /channel delete <name>                          Deletes a channel. Channel must be made by a user or the user must have permission
        /channel join <name>                            Puts a channel back in this users channel selection list
        /channel leave <name>                           Removes a channel from the channel selection list
        /channel kick <user> [channel]                  Kicks a user from a channel. If none is specified, the user is kicked from the senders current channel
        /channel add <user> [channel]                   Adds a user to a channel. If no channel is specified, the user is added to the senders current channel.
        /channel select <name>                          Sets the given channel to the currently selected channel.
        
         */
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
     *      prefix: "" (String)
     *      permission: "" (String nullable)
     *      closeOnExit:
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
