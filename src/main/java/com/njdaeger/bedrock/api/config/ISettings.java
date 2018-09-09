package com.njdaeger.bedrock.api.config;

import com.njdaeger.bcm.base.IConfig;

public interface ISettings extends IConfig {

    /**
     * Reloads the plugin settings
     */
    void reloadSettings();

    /**
     * Get the current language file in use for this plugin
     * @return The name of the current language file in use. (without extension)
     */
    String getLang();

    /**
     * Check if the plugin is in debug mode
     * @return True if the plugin is in debug mode.
     */
    boolean isDebugMode();

    /**
     * Get the custom afk away message format.
     * @return The format if set or enabled. Null otherwise.
     */
    String getAfkAwayFormat();

    /**
     * Check whether the afk away message is enabled or not.
     * @return True if the away message is enabled, false otherwise.
     */
    boolean hasAfkAwayMessage();

    /**
     * Get the custom afk back message format
     * @return The format if set or enabled. Null otherwise.
     */
    String getAfkBackMessage();

    /**
     * Check whether the afk back message is enabled or not
     * @return True if the away message is enabled, false otherwise.
     */
    boolean hasAfkBackMessage();

    /**
     * Get the custom afk away more info message format
     * @return THe format if set or enabled. Null otherwise
     */
    String getAfkAwayMoreInfoMessage();

    /**
     * Check whether the afk away more info is enabled or not
     * @return True if the away message is enabled, false otherwise.
     */
    boolean hasAfkAwayMoreInfoMessage();

    /**
     * Check if giving more info for afk messages is enabled or not
     * @return True if enabled, false otherwise.
     */
    boolean isAfkMoreInfoEnabled();

    /**
     * Check whether if there is gamemode specific permissions
     * @return True if gamemode specific, false otherwise.
     */
    boolean hasGamemodeSpecificPermissions();

    
}
