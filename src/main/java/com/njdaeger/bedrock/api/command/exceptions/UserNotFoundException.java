package com.njdaeger.bedrock.api.command.exceptions;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Message;

public class UserNotFoundException extends BedrockException {
    
    public UserNotFoundException(String user) {
        super(Bedrock.translate(Message.ERROR_USER_NOT_FOUND, user));
    }
}
