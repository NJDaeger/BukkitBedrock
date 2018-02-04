package com.njdaeger.bedrock.api;

import com.njdaeger.bedrock.MessageFile;

public interface IConfig extends com.coalesce.core.config.base.IConfig {
    
    MessageFile.Language getLanguage();
    
}
