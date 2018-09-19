package com.njdaeger.bedrock.api.settings;

import java.util.regex.Pattern;

public interface ISettings {

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

    /**
     * Get the pattern required to match in order to be a valid nickname
     * @return The required nickname pattern
     */
    Pattern getNickCharacterWhitelist();

    /**
     * Whether to use the 'whitelist' as a blacklist for characters not to use
     * @return True if inverse whitelist is on and if a pattern is specified, false otherwise.
     */
    boolean hasNickInversedWhitelist();

    /**
     * Whether to allow colors in nicknames or not
     * @return True whether nickname colors are allowed
     */
    boolean allowNickColor();

    /**
     * Check whether a permission is needed to use nickname colors
     * @return  True if a permission is required and nick colors are allowed. False otherwise.
     */
    boolean useNickColorPermission();

    /**
     * Get the max length allowed for a nickname
     * @return The max length allowed for a nickname.
     */
    int getMaxNickLength();

    /**
     * Whether two of the same nickname are allowed
     * @return True if duplicates are allowed
     */
    boolean allowNickDuplicates();

    /**
     * Whether to require a permission to use duplicates
     * @return True if a permission is required for duplicates and duplicates are enabled. False otherwise.
     */
    boolean useNickDuplicatePermission();

    /**
     * How much location history to record when a user is online
     * @return The max number of locations to store when a user is online.
     */
    int getHistoryRecordLimit();

    /**
     * Whether to save the location history when a user exits
     * @return True if location history will be saved when a user exits, false otherwise.
     */
    boolean saveHistoryLog();

    /**
     * How much of the users location history to save when they leave
     * @return The max number of locations to save before being cut off.
     */
    int getMaxSaveHistory();

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

}
