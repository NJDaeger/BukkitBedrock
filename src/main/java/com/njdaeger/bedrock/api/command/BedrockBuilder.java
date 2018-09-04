package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractCommandBuilder;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.Message;
import com.njdaeger.bedrock.api.Permission;

import java.util.HashSet;
import java.util.Set;

public class BedrockBuilder extends AbstractCommandBuilder<CommandContext, TabContext, BedrockBuilder> {
    
    private final IBedrock bedrock;
    
    /**
     * Creates a new CommandBuilder
     *
     * @param plugin The plugin the command is registered to
     * @param name   The name of the command
     */
    public BedrockBuilder(IBedrock plugin, String name) {
        super(name);
        this.bedrock = plugin;
    }
    
    public static BedrockBuilder builder(String name) {
        return new BedrockBuilder(Bedrock.getBedrock(), name);
    }
    
    public BedrockBuilder permission(Permission... permissions) {
        Set<String> perm = new HashSet<>();
        for (Permission permission : permissions) {
            perm.add(permission.toString());
        }
        permissions(perm.toArray(new String[0]));
        return this;
    }
    
    public BedrockBuilder permission(Permission permission) {
        permissions(permission.toString());
        return this;
    }
    
    public BedrockBuilder description(Message message) {
        description(bedrock.translate(message));
        return this;
    }
    
    public BedrockBuilder usage(Message message) {
        usage(bedrock.translate(message));
        return this;
    }
    
    @Override
    public Command build() {
        return (Command)command;
    }
}
