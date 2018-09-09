package com.njdaeger.bedrock.api.lang;

import com.njdaeger.bcm.Configuration;
import com.njdaeger.bcm.base.ConfigType;
import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.IBedrock;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class MessageFile extends Configuration {

    private final String langfile;
    private final Map<Message, String> messages;

    public MessageFile(IBedrock plugin, String langfile) {
        super(plugin, ConfigType.YML, "lang" + File.separator + langfile);
        this.messages = new HashMap<>();
        this.langfile = langfile;
    }

    /**
     * Reloads the messages map
     */
    public void reloadMessages() {
        messages.clear();
        List<String> missing = new ArrayList<>();

        for (Message message : Message.values()) {
            if (contains(message.getKey(), true)) {
                messages.put(message, message.format());
            } else missing.add(message.getKey());
        }

        missing.forEach(k -> Bedrock.warn("Missing key \"" + k + "\" in " + langfile + ".yml"));
    }

    /**
     * Gets the current language selected
     * @return The current language
     */
    public String getLang() {
        return langfile;
    }

    /**
     * Get a translated message
     * @param message The message to get
     * @return The message if it exists, or null if it was not found
     */
    public String get(Message message) {
        return messages.get(message);
    }

}
