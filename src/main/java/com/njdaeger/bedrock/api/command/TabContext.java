package com.njdaeger.bedrock.api.command;

import com.njdaeger.bci.base.AbstractTabContext;

public class TabContext extends AbstractTabContext<CommandContext, TabContext> {
    
    public TabContext(CommandContext commandContext) {
        super(commandContext);
    }
}
