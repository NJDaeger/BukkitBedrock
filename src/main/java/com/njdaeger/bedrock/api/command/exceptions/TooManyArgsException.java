package com.njdaeger.bedrock.api.command.exceptions;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Message;

public class TooManyArgsException extends BedrockException {
    
    public TooManyArgsException(int max, int given) {
        super(Bedrock.translate(Message.ERROR_TOO_MANY_ARGS, max, given));
    }
}
