package com.njdaeger.bedrock.api.command.exceptions;

public class UnknownGamemodeException extends BedrockException {
    
    public UnknownGamemodeException(String given) {
        super("Cannot find gamemode " + given);
    }
}
