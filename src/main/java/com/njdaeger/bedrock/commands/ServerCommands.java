package com.njdaeger.bedrock.commands;

import com.njdaeger.bci.base.BCIException;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.command.BedrockBuilder;
import com.njdaeger.bedrock.api.command.CommandContext;
import com.njdaeger.bedrock.api.command.TabContext;

public class ServerCommands {

    public ServerCommands() {
        BedrockBuilder.builder("bedrock")
                      .description("Soon")
                      .executor(this::bedrock)
                      .completer(this::bedrockTab)
                      .usage("Soon")
                      .maxArgs(1)
                      .minArgs(1)
                      .build()
                      .register();
    }

    private void bedrock(CommandContext context) throws BCIException {

        if (context.subCommandAt(0, "reload", true, c -> Bedrock.reload())) return;

    }

    private void bedrockTab(TabContext context) {
        context.completionAt(0, "reload");
    }

}
