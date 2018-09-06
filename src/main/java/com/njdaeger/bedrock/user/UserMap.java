package com.njdaeger.bedrock.user;

import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class UserMap implements Iterable<IUser> {
    
    private final Map<String, IUser> users;
    private final IBedrock bedrock;
    
    public UserMap(IBedrock bedrock) {
        this.users = new HashMap<>();
        this.bedrock = bedrock;
    }
    
    /**
     * Adds a user to the user map
     * @param player The player to add
     */
    public void addUser(Player player) {
        users.put(player.getName(), new User(bedrock, player).login());
    }
    
    /**
     * Removes the user from the user map
     * @param player The player to remove
     */
    public void removeUser(Player player) {
        removeUser(player.getName());
    }
    
    /**
     * Removes the user from the user map
     * @param name The username of the user to remove
     */
    public void removeUser(String name) {
        users.get(name).logout();
        users.remove(name);
    }
    
    /**
     * Gets a user by their exact username.
     * @param name The exact username of the user
     * @return The user if they exist, null otherwise.
     */
    public IUser getUserExact(String name) {
        return users.get(name);
    }
    
    /**
     * Gets a user via their displayname or username
     * @param displayname The displayname or username of the user
     * @return The user or null if they dont exist
     */
    public IUser getUser(String displayname) {
        displayname = ChatColor.stripColor(displayname);
        if (hasUserExact(displayname)) return getUserExact(displayname);
        for (IUser user : this) {
            if (ChatColor.stripColor(user.getDisplayName()).equals(displayname)) return user;
        }
        return null;
    }
    
    /**
     * Checks if the server has this exact user
     * @param name The exact username of this user
     * @return True if the user exists, false otherwise
     */
    public boolean hasUserExact(String name) {
        return getUserExact(name) != null;
    }
    
    /**
     * Checks if the server has this user.
     * @param displayname Can be their displayname or username
     * @return True if the user exists, false otherwise.
     */
    public boolean hasUser(String displayname) {
        return getUser(displayname) != null;
    }
    
    /**
     * Gets a list of all the current users
     * @return A list of all the current users.
     */
    public List<IUser> getUsers() {
        return new ArrayList<>(users.values());
    }
    
    @Override
    public Iterator<IUser> iterator() {
        return users.values().iterator();
    }
}
