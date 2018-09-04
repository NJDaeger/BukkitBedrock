package com.njdaeger.bedrock.api.command.exceptions;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Message;

public class NotEnoughArgsException extends BedrockException {
    
    public NotEnoughArgsException(int min, int given) {
        super(Bedrock.translate(Message.ERROR_NOT_ENOUGH_ARGS, min, given));
    }
}
