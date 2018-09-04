package com.njdaeger.bedrock.commands;

public final class HomeCommands {
/*
    public HomeCommands() {
        BedrockCommand home = BedrockCommand.builder("home")
                .permission(COMMAND_HOME, COMMAND_HOME_OTHER)
                .completer(this::homeTab)
                .executor(this::home)
                .aliases("gohome")
                .description(HOME_DESC)
                .usage(HOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
    
        BedrockCommand setHome = BedrockCommand.builder( "sethome")
                .permission(COMMAND_SETHOME, COMMAND_SETHOME_OTHER)
                .completer(this::setHomeTab)
                .executor(this::setHome)
                .aliases("newhome")
                .description(SETHOME_DESC)
                .usage(SETHOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
        
        BedrockCommand delHome = BedrockCommand.builder("delhome")
                .permission(COMMAND_DELHOME, COMMAND_DELHOME_OTHER)
                .completer(this::delhomeTab)
                .executor(this::delHome)
                .aliases("removehome")
                .description(DELHOME_DESC)
                .usage(DELHOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
    
        BedrockCommand listHomes = BedrockCommand.builder("listhomes")
                .permission(COMMAND_LISTHOMES, COMMAND_LISTHOMES_OTHER)
                .completer(this::listHomesTab)
                .executor(this::listHomes)
                .aliases("homes")
                .description(LISTHOMES_DESC)
                .usage(LISTHOMES_USAGE)
                .maxArgs(1)
                .senders(PLAYER, CONSOLE)
                .build();
        
        Bedrock.registerCommand(home, setHome, delHome, listHomes);
    }
    //home <home>               send me to my home          bedrock.homes.home
    //home <home> [user]        send other to my home       bedrock.homes.home.other.to-me
    //home [u:user] <home>      send me to other home       bedrock.homes.home.other.me-to-other
    //home [u:user] <u:home>    send other to own home      bedrock.homes.home.other.to-own
    //home [u:user] <user:home> send other to another home  bedrock.homes.home.other.to-other
    private void home(BedrockCommandContext context) {
    
        if (context.subCommand(CONSOLE, this::homeConsole)) return;
        
        IUser user;
        IHome home;
        
        //home <home> send me to my own home
        if (context.isLength(1)) {
            //Permission Check
            if (!context.hasPermission(COMMAND_HOME)) {
                context.noPermission(COMMAND_HOME);
                return;
            }
    
            if (!context.getUser().hasHomes()) {
                context.pluginMessage(ERROR_NO_HOMES, context.getUser().getDisplayName());
                return;
            }
            
            //Get the home from the sender becuase the command only has one arg
            home = context.getUser().getHome(context.argAt(0));
    
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), context.argAt(0));
                return;
            }
            context.pluginMessage(HOME_SENDER_TO_OWN, home.getName());
            context.getUser().sendHome(home);
            return;
        }
        
        //Okay, the command has more than one argument, if the first
        //argument doesnt have a u: in front of it, it is automatically
        //assumed to be a home name
        if (!context.argAt(0).startsWith("u:")) {
            //Permission Check
            if (!context.hasPermission(COMMAND_HOME_OTHERTOME)) {
                context.noPermission(COMMAND_HOME_OTHERTOME);
                return;
            }
            
            if (!context.getUser().hasHomes()) {
                context.pluginMessage(ERROR_NO_HOMES, context.getUser().getDisplayName());
                return;
            }
            
            //Get the home from the sender becuase the sender is trying to send the user specified in the second arg to it.
            home = context.getUser().getHome(context.argAt(0));
    
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), context.argAt(0));
                return;
            }
            
            //The user from the second arg
            user = context.getUser(context.argAt(1));
    
            if (user == null) {
                context.userNotFound(context.argAt(1));
                return;
            }
            user.pluginMessage(HOME_RECEIVER_SEND_OTHER, context.getDisplayName(), home.getName());
            context.pluginMessage(HOME_SENDER_SEND_OTHER, user.getDisplayName(), home.getName());
            user.sendHome(home);
            return;
        }
    
        //So we now know the first arg is specifying a user, we need to cut off the u: prefix.
        String name = context.argAt(0).split(":")[1];
        //Try to get the user from this split argument
        user = context.getUser(name);
    
        if (user == null) {
            context.userNotFound(name);
            return;
        }
    
        //If the second arg contains a colon we need to check if the first split is a user or is the u prefix
        if (context.argAt(1).contains(":")) {
        
            IUser otherUser;
            String[] second = context.argAt(1).split(":");
        
            //If the first split is the u prefix then we know the sender is trying to send the user specified in arg 1 to one of their own homes
            if (second[0].equalsIgnoreCase("u")) {
                
                //Permission check
                if (!context.hasPermission(COMMAND_HOME_OTHERTOOWN)) {
                    context.noPermission(COMMAND_HOME_OTHERTOOWN);
                    return;
                }
    
                if (!user.hasHomes()) {
                    context.pluginMessage(ERROR_NO_HOMES, user.getDisplayName());
                    return;
                }
                
                //Get the home from the user specified in the first argument
                home = user.getHome(second[1]);
            
                if (home == null) {
                    context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), second[1]);
                    return;
                }
                user.pluginMessage(HOME_RECEIVER_SEND_OTHER, context.getDisplayName(), home.getName());
                context.pluginMessage(HOME_SENDER_SEND_OTHER, user.getDisplayName(), home.getName());
                user.sendHome(home);
                return;
            
            }
        
            //Now we know the sender is trying to send another user to another users home.
            //Permission check.
            if (!context.hasPermission(COMMAND_HOME_OTHERTOOTHER)) {
                context.noPermission(COMMAND_HOME_OTHERTOOTHER);
                return;
            }
            
            //Get the user from the second argument
            otherUser = context.getUser(second[0]);
            
            if (otherUser == null) {
                context.userNotFound(second[0]);
                return;
            }
    
            if (!otherUser.hasHomes()) {
                context.pluginMessage(ERROR_NO_HOMES, otherUser.getDisplayName());
                return;
            }
            
            //Get the home from the user in the second argument
            home = otherUser.getHome(second[1]);
    
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, otherUser.getDisplayName(), second[1]);
                return;
            }
            user.pluginMessage(HOME_RECEIVER_OTHER_TO_OTHER, context.getDisplayName(), home.getOwner().getDisplayName(), home.getName());
            context.pluginMessage(HOME_SENDER_OTHER_TO_OTHER, user.getDisplayName(), home.getOwner().getDisplayName(), home.getName());
            user.sendHome(home);
            return;
        }
    
        //At this point we know the command is trying to send the sender to another users home
        //Permission check
        if (!context.hasPermission(COMMAND_HOME_METOOTHER)) {
            context.noPermission(COMMAND_HOME_METOOTHER);
            return;
        }
    
        if (!context.getUser(name).hasHomes()) {
            context.pluginMessage(ERROR_NO_HOMES, context.getUser(name).getDisplayName());
            return;
        }
        
        //Get the home from the user specified in the first arg
        home = context.getUser(name).getHome(context.argAt(1));
    
        if (home == null) {
            context.pluginMessage(ERROR_HOME_NOT_FOUND, user.getDisplayName(), context.argAt(0));
            return;
        }
        context.pluginMessage(HOME_SENDER_TO_OTHER, home.getOwner().getDisplayName(), home.getName());
        home.sendHere(context.getUser());
        
    }
    
    private void homeConsole(BedrockCommandContext context) {
        if (context.isLength(1)) {
            context.notEnoughArgs(2, 1);
            return;
        }
        if (!context.argAt(0).startsWith("u:")) {
            context.pluginMessage(ERROR_USER_NOT_SPECIFIED);
            return;
        }
        if (!context.argAt(1).contains(":")) {
            context.pluginMessage(ERROR_NO_HOMES);
            return;
        }
        
        String[] arg = context.argAt(1).split(":");
        IHome home;
        IUser user1;
        IUser user2;
        
        user1 = context.getUser(context.argAt(0).split(":")[1]);
        
        if (user1 == null) {
            context.userNotFound(context.argAt(0).split(":")[1]);
            return;
        }
        
        if (arg[0].equalsIgnoreCase("u")) {
            home = user1.getHome(arg[1]);
            
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, arg[1], user1.getDisplayName());
                return;
            }
            user1.pluginMessage(HOME_RECEIVER_SEND_OTHER, context.getDisplayName(), home.getName());
            context.pluginMessage(HOME_SENDER_SEND_OTHER, user1.getDisplayName(), home.getName());
            user1.sendHome(home);
            return;
        }
        user2 = context.getUser(arg[0]);
        if (user2 == null) {
            context.userNotFound(arg[0]);
            return;
        }
        home = user2.getHome(arg[1]);
        if (home == null) {
            context.pluginMessage(ERROR_HOME_NOT_FOUND, arg[1], user1.getDisplayName());
            return;
        }
        user1.pluginMessage(HOME_RECEIVER_OTHER_TO_OTHER, context.getDisplayName(), home.getOwner().getDisplayName(), home.getName());
        context.pluginMessage(HOME_SENDER_OTHER_TO_OTHER, user1.getDisplayName(), home.getOwner().getDisplayName(), home.getName());
        user1.sendHome(home);
    }
    
    //home <home>               send me to my home          bedrock.homes.home
    //home <home> [user]        send other to my home       bedrock.homes.home.other.to-me
    //home [u:user] <home>      send me to other home       bedrock.homes.home.other.me-to-other
    //home [u:user] <u:home>    send other to own home      bedrock.homes.home.other.to-own
    //home [u:user] <user:home> send other to another home  bedrock.homes.home.other.to-other
    private void homeTab(BedrockTabContext context) {
    
        if (context.length(0)) {
            List<String> players = Bukkit.getOnlinePlayers().stream().map(Player::getName).map("u:"::concat).collect(Collectors.toList());
    
            if (!context.isConsole()) {
                players.addAll(context.getUser().getHomes().stream().map(IHome::getName).collect(Collectors.toList()));
            }
            context.completionAt(0, players.toArray(new String[0]));
            return;
        }
        
        if (!context.getPrevious().contains(":")) {
            if (context.isConsole()) return;
            
            context.playerCompletion(1);
            return;
        }
    
        List<String> completions = new ArrayList<>();
        IUser user = context.getUser(context.getPrevious().split(":")[1]);

        if (user == null) return;

        if (!context.getCurrent().contains(":")) {
            
            completions.add("u");
            completions.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            completions.addAll(user.getHomes().stream().map(IHome::getName).collect(Collectors.toList()));
            
        } else {
            
            if (context.getCurrent().startsWith("u:")) {
                completions.addAll(user.getHomes().stream().map(IHome::getName).map("u:"::concat).collect(Collectors.toList()));
                
            } else {
                
                IUser user1 = context.getUser(context.getCurrent().split(":")[0]);
                if (user1 == null) return;
                completions.addAll(user.getHomes().stream().map(IHome::getName).map(user.getName()::concat).collect(Collectors.toList()));
            }
        }
        
        context.completionAt(1, completions.toArray(new String[0]));
        
    }
    
    private void setHome(BedrockCommandContext context) {
        if (context.isLength(1)) {
            if (context.isConsole()) {
                context.notEnoughArgs(2, 1);
                return;
            }
            if (!context.getUser().createHome(context.argAt(0))) context.pluginMessage(ERROR_HOME_EXISTS, context.argAt(0));
            else context.pluginMessage(SETHOME_SENDER, context.argAt(0));
        }
        else {
            
            if (!context.hasPermission(COMMAND_SETHOME_OTHER)) {
                context.noPermission(COMMAND_SETHOME_OTHER);
                return;
            }
            
            IUser user = context.getUser(1);
            
            if (user == null) {
                context.userNotFound(context.argAt(1));
                return;
            }
            if (!user.createHome(context.argAt(0))) context.pluginMessage(ERROR_HOME_EXISTS, context.argAt(0));
            else {
                user.pluginMessage(SETHOME_OTHER_CREATED_RECEIVER, context.getDisplayName(), context.argAt(0));
                context.pluginMessage(SETHOME_OTHER_CREATED_SENDER, context.argAt(0), user.getDisplayName());
            }
        }
    }
    
    private void setHomeTab(BedrockTabContext context) {
        if (context.length(1)) context.playerCompletion(1);
    }
    
    private void delHome(BedrockCommandContext context) {
        IHome home;
        if (context.isLength(1)) {
            if (context.isConsole()) {
                context.notEnoughArgs(2, 1);
                return;
            }
            home = context.getUser().getHome(context.argAt(0));
            
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, context.argAt(0), context.getDisplayName());
                return;
            }
            if (!context.getUser().deleteHome(home.getName())) {
                context.pluginMessage(ERROR_HOME_NOT_DELETED, home.getName());
                return;
            }
            else {
                context.pluginMessage(DELHOME_SENDER, home.getName());
                return;
            }
        }
        
        if (!context.hasPermission(COMMAND_DELHOME_OTHER)) {
            context.noPermission(COMMAND_DELHOME_OTHER);
            return;
        }
        
        IUser user = context.getUser(0);
        
        if (user == null) {
            context.userNotFound(context.argAt(0));
            return;
        }
        
        home = user.getHome(context.argAt(1));
    
        if (home == null) {
            context.pluginMessage(ERROR_HOME_NOT_FOUND, context.argAt(0), user.getDisplayName());
            return;
        }
        
        if (user.deleteHome(home.getName())) context.pluginMessage(ERROR_HOME_NOT_DELETED, home.getName());
        else {
            user.pluginMessage(DELHOME_OTHER_RECEIVER, context.getDisplayName(), home.getName());
            context.pluginMessage(DELHOME_OTHER_SENDER, home.getName(), user.getDisplayName());
        }
        
    }
    
    private void delhomeTab(BedrockTabContext context) {
        if (context.isConsole()) {
            context.playerCompletion(0);
            return;
        } else {
            IUser user = context.getUser();
            List<String> completion = user.getHomes().stream().map(IHome::getName).collect(Collectors.toList());
            completion.addAll(Bukkit.getOnlinePlayers().stream().map(Player::getName).collect(Collectors.toList()));
            context.completionAt(0, completion.toArray(new String[0]));
        }
        
        if (context.length(1)) {
            IUser user = context.getUser(context.argAt(0));
            if (user == null) return;
            context.completionAt(1, user.getHomes().stream().map(IHome::getName).toArray(String[]::new));
        }
        
    }
    
    private void listHomes(BedrockCommandContext context) {
        IUser user;
        
        if (context.isLength(0)) {
            if (context.isConsole()) {
                context.notEnoughArgs(1, 0);
                return;
            }
            user = context.getUser();
        }
        else {
            if (!context.hasPermission(COMMAND_LISTHOMES_OTHER)) {
                context.noPermission(COMMAND_LISTHOMES_OTHER);
                return;
            }
            user = context.getUser(0);
        }
        
        if (user == null) {
            context.userNotFound(context.argAt(0));
            return;
        }
        
        if (!user.hasHomes()) {
            context.pluginMessage(ERROR_NO_HOMES, user.getDisplayName());
            return;
        }
        
        context.pluginMessage(LISTHOMES_MESSAGE_FORMAT, user.getDisplayName(), user.listHomes());
    }
    
    private void listHomesTab(BedrockTabContext context) {
        context.playerCompletion(0);
    }
    */
}
