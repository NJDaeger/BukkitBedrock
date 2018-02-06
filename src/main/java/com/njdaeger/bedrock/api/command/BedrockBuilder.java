package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.CommandBuilder;
import com.njdaeger.bedrock.api.IBedrock;

public class BedrockBuilder extends CommandBuilder<BedrockCommandContext, BedrockTabContext, BedrockCommand> {
    /**
     * Creates a new CommandBuilder
     *
     * @param plugin The plugin the command is registered to
     * @param name   The name of the command
     */
    public BedrockBuilder(IBedrock plugin, String name) {
        super(plugin, name, new BedrockCommand(plugin, name));
    }
    
    @Override
    public BedrockCommand build() {
        return command;
    }
}
