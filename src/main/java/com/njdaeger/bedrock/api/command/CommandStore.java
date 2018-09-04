package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractCommandStore;
import com.njdaeger.bci.base.BCICommand;
import org.bukkit.plugin.Plugin;

public class CommandStore extends AbstractCommandStore<CommandContext, TabContext, CommandWrapper> {
    
    public CommandStore(Plugin plugin) {
        super(plugin);
    }
    
    @Override
    public void registerCommand(BCICommand<CommandContext, TabContext> bciCommand) {
        registerCommand(new CommandWrapper(plugin, bciCommand));
    }
}
