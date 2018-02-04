package com.njdaeger.bedrock;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;

import java.io.File;

public class MessageFile extends YmlConfig {
    
    private Language language;
    
    public MessageFile(IBedrock plugin) {
        super("lang" + File.separator + plugin.getLanguage().getFileName(), plugin);
        this.language = plugin.getLanguage();
    }
    
    public String translate(Message message) {
        return getString(message.key());
    }
    
    public Language getLanguage() {
        return language;
    }
    
    public enum Language {
    
        EN_US("messages-en-us");
    
        private String fileName;
        
        Language(String fileName) {
            this.fileName = fileName;
        }
    
        public String getFileName() {
            return fileName;
        }
    
        @Override
        public String toString() {
            return name().toLowerCase();
        }
        
    }
}
