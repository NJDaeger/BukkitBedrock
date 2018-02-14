package com.njdaeger.bedrock;

import com.coalesce.core.config.YmlConfig;
import com.njdaeger.bedrock.api.IBedrock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFile extends YmlConfig {
    
    private Language language;
    private Map<Message, String> messages;
    
    public MessageFile(IBedrock plugin) {
        super("lang" + File.separator + plugin.getLanguage().getFileName(), plugin);
        this.language = plugin.getLanguage();
        this.messages = new HashMap<>();
        List<String> missing = new ArrayList<>();
        
        for (Message message : Message.values()) {
            if (contains(message.key(), true)) {
                messages.put(message, getString(message.key()));
            } else missing.add(message.key());
        }
        
        missing.forEach(k -> System.out.println("Could not find key \"" + k + "\" in " + language.getFileName() + ".yml"));
        
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
