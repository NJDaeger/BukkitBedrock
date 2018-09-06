package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractCommandWrapper;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;

import java.util.List;

public class CommandWrapper extends AbstractCommandWrapper<CommandContext, TabContext> {
    
    public CommandWrapper(Plugin plugin, Command command) {
        super(plugin, command);
    }
    
    @Override
    public boolean execute(CommandSender commandSender, String alias, String[] args) {
        return command.execute(new CommandContext(plugin, command, commandSender, args, alias));
    }
    
    @Override
    public List<String> tabComplete(CommandSender commandSender, String alias, String[] args) throws IllegalArgumentException {
        return command.complete(new TabContext(new CommandContext(plugin, command, commandSender, args, alias)));
    }
}
