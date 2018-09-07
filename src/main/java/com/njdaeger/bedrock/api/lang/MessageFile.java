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

    public void reloadMap() {
        messages.clear();
        List<String> missing = new ArrayList<>();

        for (Message message : Message.values()) {
            if (contains(message.getKey(), true)) {
                messages.put(message, message.format());
            } else missing.add(message.getKey());
        }

        missing.forEach(k -> Bedrock.warn("Missing key \"" + k + "\" in " + langfile + ".yml"));
    }

    public String getLang() {
        return langfile;
    }

    public String get(Message message) {
        return messages.get(message);
    }

}
