package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.BCICommand;
import com.njdaeger.bedrock.BedrockPlugin;
import org.bukkit.plugin.java.JavaPlugin;

public class Command extends BCICommand<CommandContext, TabContext> {
    
    public Command(String name) {
        super(name);
    }
    
    @Override
    public void register() {
        JavaPlugin.getPlugin(BedrockPlugin.class).registerCommand(this);
    }
}
