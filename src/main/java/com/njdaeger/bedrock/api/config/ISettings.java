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

    /**
     * Check whether speed specific permissions are enabled.
     * @return True if speed specific permissions, false otherwise.
     */
    boolean hasSpeedSpecificPermissions();

    /**
     * Whether the max speed bypass permission is enabled or not
     * @return True if enabled, false otherwise
     */
    boolean hasMaxSpeedBypass();

    /**
     * Get the max walk speed
     * @return The max walk speed
     */
    float getMaxWalkSpeed();

    /**
     * Get the max fly speed
     * @return The max fly speed
     */
    float getMaxFlySpeed();

    /**
     * Whether to allow negative speed or not.
     * @return True if enabled, false otherwise.
     */
    boolean allowNegativeSpeed();

    /**
     * Whether to require a permission to use negative speed
     * @return True if enabled, false otherwise.
     */
    boolean hasNegativeSpeedPermission();

    /**
     * Whether the min speed bypass permission is enabled or not
     * @return True if enabled, false otherwise
     */
    boolean hasMinSpeedBypass();

    /**
     * Gets the minimum walk speed allowed to be set
     * @return The minimum walk speed
     */
    float getMinWalkSpeed();

    /**
     * Gets the minimum fly speed allowed to be set
     * @return The minimum fly speed
     */
    float getMinFlySpeed();
}
