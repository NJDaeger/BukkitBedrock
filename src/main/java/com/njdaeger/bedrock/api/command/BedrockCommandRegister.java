package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.CommandRegister;
import com.coalesce.core.command.base.ProcessedCommand;
import com.coalesce.core.wrappers.CoSender;
import com.njdaeger.bedrock.api.IBedrock;
import org.bukkit.command.CommandSender;

import java.util.List;

public class BedrockCommandRegister extends CommandRegister<BedrockCommandContext, BedrockTabContext> {
    
    private final IBedrock bedrock;
    
    public BedrockCommandRegister(ProcessedCommand<BedrockCommandContext, BedrockTabContext> command, IBedrock plugin) {
        super(command, plugin);
        this.bedrock = plugin;
    }
    
    @Override
    public boolean execute(CommandSender sender, String alias, String[] args) {
        
        return command.run(new BedrockCommandContext(new CoSender(plugin, sender), args, bedrock));
    }
    
    @Override
    public List<String> tabComplete(CommandSender sender, String alias, String[] args) throws IllegalArgumentException {
        return command.complete(new BedrockTabContext(new BedrockCommandContext(new CoSender(plugin, sender), args, bedrock), command, args));
    }
}
