package com.njdaeger.bedrock.commands;

import static com.njdaeger.bedrock.api.Bedrock.translate;

public final class ChatCommands {
/*
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
            .permission(
                    COMMAND_CHAN_DELETE,
                    COMMAND_CHAN_NEW,
                    COMMAND_CHAN_TEMP,
                    COMMAND_CHAN_LEAVE,
                    COMMAND_CHAN_JOIN,
                    COMMAND_CHAN_SELECT,
                    COMMAND_CHAN_DISPLAY,
                    COMMAND_CHAN_KICK_CURRENT,
                    COMMAND_CHAN_KICK_OTHER,
                    COMMAND_CHAN_ADD_CURRENT,
                    COMMAND_CHAN_ADD_OTHER,
                    COMMAND_CHAN_JOIN_ANY)
            .executor(this::channel)
            .completer(this::channelTab)
            .description(CHANNEL_DESC)
            .usage(CHANNEL_USAGE)
            .minArgs(1)
            .maxArgs(5)
            .build());
        
    }
    
    private void customChannel(BedrockCommandContext context) {
        if (context.hasArgs()) {
            if (context.isConsole()) {
                context.pluginMessage("Not Supported yet");
            } else bedrock.getChannel(context.getAlias()).userMessage(context.getUser(), context.joinArgs());
        }
        else {
            IChannel channel = bedrock.getChannel(context.getAlias());
            context.pluginMessage(CHANNEL_INFO_TITLE, channel.getName());
            context.pluginMessage(CHANNEL_SIZE, channel.getUsers().size());
            context.pluginMessage(CHANNEL_NAME, channel.getName());
            context.pluginMessage(CHANNEL_DISPLAY, channel.getDisplay().name());
            context.pluginMessage(CHANNEL_CLOSE, channel.getClose().name());
            context.pluginMessage(CHANNEL_PREFIX, channel.getPrefix());
            
            StringBuilder b = new StringBuilder();
            channel.getUsers().forEach(u -> b.append(translate(CHANNEL_USERS_FORMAT, u.getDisplayName())));

            context.pluginMessage(CHANNEL_USERS, b.toString().trim());
        }
    }
    
    private void channelTab(BedrockTabContext context) {
        context.completionAt(0, "new", "temp", "delete", "join", "leave", "kick", "add", "select", "display");
        if (context.getLength() >= 1) {
            if (context.subCompletionAt(0, "new", true, this::createTab)) return;
            if (context.subCompletionAt(0, "temp", true, this::createTab)) return;
            if (context.subCompletionAt(0, "delete", true, this::channelsTab)) return;
            if (context.subCompletionAt(0, "join", true, this::channelsTab)) return;
            if (context.subCompletionAt(0, "leave", true, this::channelsTab)) return;
            if (context.subCompletionAt(0, "kick", true, this::kickOrAddUserTab)) return;
            if (context.subCompletionAt(0, "add", true, this::kickOrAddUserTab)) return;
            context.subCompletionAt(0, "select", true, this::selectTab);
        }
    }
    
    private void createTab(BedrockTabContext context) {
        context.completionAt(3, "PREFIX", "NAME", "NONE");
    }
    
    private void channelsTab(BedrockTabContext context) {
        context.completionAt(1, bedrock.getChannels().stream().map(IChannel::getName).toArray(String[]::new));
    }
    
    private void kickOrAddUserTab(BedrockTabContext context) {
        context.playerCompletion(1);
        context.completionAt(2, bedrock.getChannels().stream().map(IChannel::getName).toArray(String[]::new));
    }
    
    private void selectTab(BedrockTabContext context) {
        if (context.isConsole()) return;
        context.completionAt(1, context.getUser().getChannels().stream().map(IChannel::getName).toArray(String[]::new));
    }
    
    private void channel(BedrockCommandContext context) {
        
        if (context.subCommandAt(0, "new", true, this::createChannel)) return;
        if (context.subCommandAt(0, "temp", true, this::createChannel)) return;
        if (context.subCommandAt(0, "delete", true, this::deleteChannel)) return;
        if (context.subCommandAt(0, "join", true, this::joinChannel)) return;
        if (context.subCommandAt(0, "leave", true, this::leaveChannel)) return;
        if (context.subCommandAt(0, "kick", true, this::kickOrAddUser)) return;
        if (context.subCommandAt(0, "add", true, this::kickOrAddUser)) return;
        if (context.subCommandAt(0, "select", true, this::selectChannel)) return;
        if (context.subCommandAt(0, "display", true, this::display)) return;
        
        context.pluginMessage(ERROR_UNKNOWN_ARGUMENT, context.argAt(0));
        
    }
    
    private void display(BedrockCommandContext context) {
        if (!context.hasPermission(COMMAND_CHAN_DISPLAY)) {
            context.noPermission(COMMAND_CHAN_DISPLAY);
            return;
        }
        if (context.length() > 1) {
            context.tooManyArgs(1, context.length());
            return;
        }
        if (context.isConsole()) {
            context.notCorrectSender(SenderType.PLAYER);
            return;
        }
        boolean hasDisplay = context.getUser().hasChannelDisplay();
        if (hasDisplay) context.pluginMessage(CHANNEL_DISPLAY_DISABLE); //Disabling
        else context.pluginMessage(CHANNEL_DISPLAY_ENABLE); //Enabling
        context.getUser().runChannelDisplay(!hasDisplay);
    }
    
    private void kickOrAddUser(BedrockCommandContext context) {
        IUser user = context.getUser(1);
        IChannel channel;
        
        if (context.length() > 3) {
            context.tooManyArgs(3, context.length());
            return;
        }
        
        if (context.isLength(3)) {
            if (!bedrock.hasChannel(context.argAt(2))) {
                context.pluginMessage(ERROR_CHANNEL_NOT_FOUND, context.argAt(2));
                return;
            } else channel = bedrock.getChannel(context.argAt(2));
        } else {
            //The args length is 2 and the console wont ever have a selected channel.
            if (context.isConsole()) {
                context.notEnoughArgs(3, context.length());
                return;
            }
            else channel = context.getUser().getSelectedChannel();
        }
    
        if (user == null) {
            context.userNotFound(context.argAt(1));
            return;
        }
        
        if (context.argAt(0).equalsIgnoreCase("kick")) {
            
            //If its length is 3, then we know they're specifying a channel
            if (context.isLength(3) && !context.hasPermission(COMMAND_CHAN_KICK_OTHER)) {
                context.noPermission(COMMAND_CHAN_KICK_OTHER);
                return;
            }
            
            //If its length is 2, then we know they're trying to use their current selected channel.
            if (context.isLength(2) && !context.hasPermission(COMMAND_CHAN_KICK_CURRENT)) {
                context.noPermission(COMMAND_CHAN_KICK_CURRENT);
                return;
            }
            
            //Check if the user does not have the channel in their channel list.
            if (!user.hasChannel(channel)) {
                context.pluginMessage(ERROR_USER_NOT_IN_CHANNEL, user.getDisplayName(), channel.getName());
                return;
            }
    
            channel.kickUser(user);
            user.pluginMessage(CHANNEL_KICK_RECEIVER, channel.getName());
            context.pluginMessage(CHANNEL_KICK_SENDER, user.getDisplayName(), channel.getName());
        }
        else {
    
            //If its length is 3, then we know they're specifying a channel
            if (context.isLength(3) && !context.hasPermission(COMMAND_CHAN_ADD_OTHER)) {
                context.noPermission(COMMAND_CHAN_ADD_OTHER);
                return;
            }
    
            //If its length is 2, then we know they're trying to use their current selected channel.
            if (context.isLength(2) && !context.hasPermission(COMMAND_CHAN_ADD_CURRENT)) {
                context.noPermission(COMMAND_CHAN_ADD_CURRENT);
                return;
            }
            
            //Check if the user had the channel in their channel list. If not we can add them.
            if (user.hasChannel(channel)) {
                context.pluginMessage(ERROR_USER_ALREADY_IN_CHANNEL, user.getDisplayName(), channel.getName());
                return;
            }
            channel.addUser(user);
            user.pluginMessage(CHANNEL_ADD_RECEIVER, context.getDisplayName(), channel.getName());
            context.pluginMessage(CHANNEL_ADD_SENDER, user.getDisplayName(), channel.getName());
        }
    }
    
    private void selectChannel(BedrockCommandContext context) {
        
        if (!context.hasPermission(COMMAND_CHAN_SELECT)) {
            context.noPermission(COMMAND_CHAN_SELECT);
            return;
        }
    
        if (context.length() > 2) {
            context.tooManyArgs(2, context.length());
            return;
        }
        
        if (context.isConsole()) {
            context.notCorrectSender(SenderType.PLAYER);
            return;
        }
        if (!bedrock.hasChannel(context.argAt(1))) {
            context.pluginMessage(ERROR_CHANNEL_NOT_FOUND, context.argAt(1));
            return;
        }
        IChannel channel = bedrock.getChannel(context.argAt(1));
        IUser user = context.getUser();
        
        if (!user.hasChannel(channel)) {
            context.pluginMessage(ERROR_NOT_IN_CHANNEL, channel.getName());
            return;
        }
        
        if (user.getSelectedChannel().equals(channel)) {
            context.pluginMessage(ERROR_CHANNEL_SELECTED, channel.getName());
            return;
        }
        
        user.setSelectedChannel(channel);
        context.pluginMessage(CHANNEL_SELECT, channel.getName());
        
    }
    
    private void joinChannel(BedrockCommandContext context) {
        
        if (!context.hasPermission(COMMAND_CHAN_JOIN)) {
            context.noPermission(COMMAND_CHAN_JOIN);
            return;
        }
    
        if (context.length() > 2) {
            context.tooManyArgs(2, context.length());
            return;
        }
        
        String name = context.argAt(1);
        
        if (context.isConsole()) {
            context.notCorrectSender(SenderType.PLAYER);
            return;
        }
    
        IUser user = context.getUser();
        
        if (!bedrock.hasChannel(name)) {
            context.pluginMessage(ERROR_CHANNEL_NOT_FOUND, name);
            return;
        }
        
        if (user.hasChannel(name)) {
            context.pluginMessage(ERROR_ALREADY_IN_CHANNEL, name);
            return;
        }
        
        IChannel channel = bedrock.getChannel(name);
        
        if (channel.hasPermission() && !user.hasPermission(COMMAND_CHAN_JOIN_ANY) && !user.hasPermission(channel.getPermission())) {
            context.noPermission(channel.getPermission());
            return;
        }
        user.addChannel(channel);
        context.pluginMessage(CHANNEL_JOIN, name);
        
    }
    
    private void leaveChannel(BedrockCommandContext context) {
        
        if (!context.hasPermission(COMMAND_CHAN_LEAVE)) {
            context.noPermission(COMMAND_CHAN_LEAVE);
            return;
        }
        
        if (context.length() > 2) {
            context.tooManyArgs(2, context.length());
            return;
        }
        
        String name = context.argAt(1);
        
        if (context.isConsole()) {
            context.notCorrectSender(SenderType.PLAYER);
            return;
        }
        
        IUser user = context.getUser();
        
        if (!bedrock.hasChannel(name)) {
            context.pluginMessage(ERROR_CHANNEL_NOT_FOUND, name);
            return;
        }
    
        if (!user.hasChannel(name)) {
            context.pluginMessage(ERROR_NOT_IN_CHANNEL, name);
            return;
        }
        
        user.leaveChannel(bedrock.getChannel(name));
        context.pluginMessage(CHANNEL_LEAVE, name);
    }
    
    private void createChannel(BedrockCommandContext context) {
    
        boolean arg = context.argAt(0).equalsIgnoreCase("temp");
        
        if (arg) {
            if (!context.hasPermission(COMMAND_CHAN_TEMP)) {
                context.noPermission(COMMAND_CHAN_TEMP);
                return;
            }
        } else {
            if (!context.hasPermission(COMMAND_CHAN_NEW)) {
                context.noPermission(COMMAND_CHAN_NEW);
                return;
            }
        }
        
        if (context.length() < 3) {
            context.notEnoughArgs(3, context.length());
            return;
        }
        String name = context.argAt(1);
        String prefix = context.argAt(2);
        String display = context.argAt(3);
        String permission = context.argAt(4);
    
        if (bedrock.hasChannel(name)) {
            context.pluginMessage(ERROR_CHANNEL_EXISTS, name);
            return;
        }
    
        if (!Display.contains(display)) {
            context.pluginMessage(ERROR_UNKNOWN_DISPLAY, display);
            return;
        }
        
        Message message = arg ? CHANNEL_TEMP : CHANNEL_CREATE;
        Close close = arg ? Close.CHANNEL_EMPTY : Close.NEVER;
    
        bedrock.createChannel(name, prefix, Display.valueOf(display), permission, close);
        bedrock.registerCommand(BedrockCommand.builder(name)
                .description(translate(CUSTOM_CHANNEL_COMMAND_DESC, name))
                .usage(translate(CUSTOM_CHANNEL_COMMAND_USAGE, name))
                .permission(permission == null ? "" : permission)
                .executor(this::customChannel)
                .senders(SenderType.PLAYER, SenderType.CONSOLE)
                .build()
        );
        context.pluginMessage(message, name);
        
    }
    
    private void deleteChannel(BedrockCommandContext context) {
        
        if (!context.hasPermission(COMMAND_CHAN_DELETE)) {
            context.noPermission(COMMAND_CHAN_DELETE);
            return;
        }
        
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
            context.pluginMessage(ERROR_CHANNEL_NOT_FOUND, name);
            return;
        }
        
        IChannel channel = bedrock.getChannel(name);
        
        if (channel.hasPermission() && !context.hasPermission(channel.getPermission())) {
            context.noPermission(channel.getPermission());
            return;
        }
        
        bedrock.removeChannel(name);
        context.pluginMessage(CHANNEL_DELETE, name);
    }*/
}
