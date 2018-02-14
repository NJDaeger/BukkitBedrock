package com.njdaeger.bedrock.commands;

import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockBuilder;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.command.BedrockCommandContext;
import com.njdaeger.bedrock.api.config.IHome;
import com.njdaeger.bedrock.api.user.IUser;

import static com.njdaeger.bedrock.Permission.*;
import static com.coalesce.core.SenderType.*;
import static com.njdaeger.bedrock.Message.*;

public final class HomeCommands {
    
    public HomeCommands(IBedrock bedrock) {
        BedrockCommand home = BedrockCommand.builder(bedrock, "home")
                .permission(COMMAND_HOME, COMMAND_HOME_OTHER)
                .executor(this::home)
                .aliases("gohome")
                .description(HOME_DESC)
                .usage(HOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
    
        BedrockCommand setHome = BedrockCommand.builder(bedrock, "sethome")
                .permission(COMMAND_SETHOME, COMMAND_SETHOME_OTHER)
                .executor(this::home)
                .aliases("newhome")
                .description(SETHOME_DESC)
                .usage(SETHOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
        
        BedrockCommand delHome = BedrockCommand.builder(bedrock, "delhome")
                .permission(COMMAND_DELHOME, COMMAND_DELHOME_OTHER)
                .executor(this::home)
                .aliases("removehome")
                .description(DELHOME_DESC)
                .usage(DELHOME_USAGE)
                .minArgs(1)
                .maxArgs(2)
                .senders(PLAYER, CONSOLE)
                .build();
    
        BedrockCommand listHomes = BedrockCommand.builder(bedrock, "listhomes")
                .permission(COMMAND_LISTHOMES, COMMAND_LISTHOMES_OTHER)
                .executor(this::home)
                .aliases("homes")
                .description(LISTHOMES_DESC)
                .usage(LISTHOMES_USAGE)
                .maxArgs(1)
                .senders(PLAYER, CONSOLE)
                .build();
        
        bedrock.registerCommand(home, setHome, delHome, listHomes);
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
        
        if (context.isLength(1)) {
            if (!context.hasPermission(COMMAND_HOME)) {
                context.noPermission(COMMAND_HOME);
                return;
            }
            home = context.getUser().getHome(context.argAt(0));
            
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), context.argAt(0));
                return;
            }
            context.getUser().sendHome(home);
            return;
        }
        
        if (context.argAt(0).startsWith("u:")) {
            
            String name = context.argAt(0).split(":")[1];
            user = context.getUser(name);
            
            if (user == null) {
                context.userNotFound(name);
                return;
            }
            
            if (context.argAt(1).contains(":")) {
                
                IUser otherUser;
                String[] second = context.argAt(1).split(":");
                
                if (second[0].equalsIgnoreCase("u:")) {
                    home = user.getHome(second[1]);
    
                    if (home == null) {
                        context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), second[1]);
                        return;
                    }
                    
                    user.sendHome(home);
                    return;
                    
                }
                
                otherUser = context.getUser(second[0]);
                
                if (otherUser == null) {
                    context.userNotFound(second[0]);
                    return;
                }
                
                home = otherUser.getHome(second[1]);
                user.sendHome(home);
                return;
            }
            
            home = context.getUser().getHome(context.argAt(1));
    
            if (home == null) {
                context.pluginMessage(ERROR_HOME_NOT_FOUND, context.getDisplayName(), context.argAt(0));
                return;
            }
            
            home.sendHere(context.getUser());
            
        }
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
            user1.sendHome(home);
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
        user1.sendHome(home);
    }
    
    private void setHome(BedrockCommandContext context) {
    
    }
    
    private void delHome(BedrockCommandContext context) {
    
    }
    
    private void listHomes(BedrockCommandContext context) {
    
    }
    
}
