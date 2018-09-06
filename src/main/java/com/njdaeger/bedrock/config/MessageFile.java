package com.njdaeger.bedrock.config;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.Message;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFile extends Configuration {
    
    private Language language;
    private Map<Message, String> messages;
    
    public MessageFile(IBedrock plugin) {
        super(plugin, ConfigType.YML,"lang" + File.separator + plugin.getLanguage().getFileName());
        this.language = plugin.getLanguage();
        this.messages = new HashMap<>();
        List<String> missing = new ArrayList<>();
        
        for (Message message : Message.values()) {
            if (contains(message.getKey(), true)) {
                messages.put(message, getString(message.getKey()));
            } else missing.add(message.getKey());
        }
        
        missing.forEach(k -> Bedrock.warn("Could not find key \"" + k + "\" in " + language.getFileName() + ".yml"));
        
    }
    
    public String translate(Message message) {
        return messages.get(message);
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
