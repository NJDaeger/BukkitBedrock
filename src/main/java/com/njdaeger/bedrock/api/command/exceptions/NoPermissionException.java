package com.njdaeger.bedrock.api.command.exceptions;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.lang.Message;
import com.njdaeger.bedrock.api.Permission;

import java.util.Arrays;

public class NoPermissionException extends BedrockException {
    
    public NoPermissionException(String... permissions) {
        super(Bedrock.translate(Message.ERROR_NO_PERMISSION, permissions));
    }
    
    public NoPermissionException(Permission... permission) {
        super(Bedrock.translate(Message.ERROR_NO_PERMISSION, Arrays.toString(permission)));
    }
}
