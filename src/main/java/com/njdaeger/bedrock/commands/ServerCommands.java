package com.njdaeger.bedrock.commands;

import com.njdaeger.bci.base.BCIException;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.command.BedrockBuilder;
import com.njdaeger.bedrock.api.command.CommandContext;
import com.njdaeger.bedrock.api.command.TabContext;
import com.njdaeger.bedrock.api.settings.ISettings;

public class ServerCommands {

    private final ISettings settings;

    public ServerCommands(IBedrock bedrock) {

        this.settings = bedrock.getSettings();

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

        if (context.subCommandAt(0, "reload", true, this::reload)) return;

    }

    private void reload(CommandContext context) {
        Bedrock.reload();
        context.pluginMessage("&7Reload complete.");
    }

    private void bedrockTab(TabContext context) {
        context.completionAt(0, "reload");
    }

}
