package com.njdaeger.bedrock.api.command;

import com.coalesce.core.command.base.CommandBuilder;
import com.njdaeger.bedrock.Permission;
import com.njdaeger.bedrock.api.IBedrock;

import java.util.HashSet;
import java.util.Set;

public class BedrockBuilder extends CommandBuilder<BedrockCommandContext, BedrockTabContext, BedrockBuilder, BedrockCommand> {
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
    
    public BedrockBuilder permission(Permission... permissions) {
        Set<String> perm = new HashSet<>();
        for (Permission permission : permissions) {
            perm.add(permission.toString());
        }
        permission(perm.toArray(new String[0]));
        return this;
    }
    
    public BedrockBuilder permission(Permission permission) {
        permission(permission.toString());
        return this;
    }
    
}
