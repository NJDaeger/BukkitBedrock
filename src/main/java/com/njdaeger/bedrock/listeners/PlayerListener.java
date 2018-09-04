package com.njdaeger.bedrock.listeners;

import com.njdaeger.bedrock.api.Bedrock;
import com.njdaeger.bedrock.api.Message;
import com.njdaeger.bedrock.api.IBedrock;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.api.user.IUser;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerTeleportEvent;

import static com.njdaeger.bedrock.api.Bedrock.translate;

public class PlayerListener implements Listener {
    
    private final IBedrock bedrock;
    
    public PlayerListener(IBedrock bedrock) {
        this.bedrock = bedrock;
    }
    
    @EventHandler
    public void onMove(PlayerMoveEvent event) {
        IUser user = bedrock.getUser(event.getPlayer());
        if (user.isAfk()) {
            Location al = user.getAfkLocation();
            Location cl = user.getLocation();
            if (al == null) {
                return;
            }
            if (isWithin(al.getBlockX(), cl.getBlockX()) || isWithin(al.getBlockY(), cl.getBlockY()) || isWithin(al.getBlockZ(), cl.getBlockZ())) {
                user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
            }
        }
        
    }
    
    private boolean isWithin(int base, int check) {
        int minBound = base - 2;
        int maxBound = base + 2;
        return check < minBound || check > maxBound;
    }
    
    @EventHandler
    public void onCommand(PlayerCommandPreprocessEvent event) {
        IUser user = bedrock.getUser(event.getPlayer());
        
        if (user.isAfk()) {
            user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
        }
        
    }
    
    @EventHandler
    public void onChat(AsyncPlayerChatEvent event) {
        
        IUser user = bedrock.getUser(event.getPlayer());
        
        if (user.getSelectedChannel() == null || user.getSelectedChannel().getDisplay() == Display.NONE) {
            String formatted = bedrock.translate(bedrock.getSettings().getRawMessageFormat(),
                    event.getMessage(),
                    user.getName(),
                    user.getDisplayName(),
                    user.getBase().getHealth(),
                    user.getBase().getFoodLevel(),
                    user.getWorld().getName());
            event.setFormat(formatted);
        } else {
            event.setCancelled(true);
            user.getSelectedChannel().userMessage(user, event.getMessage());
        }
        
        if (user.isAfk()) {
            user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
        }
        
    }
    
    @EventHandler
    public void onBlockPlace(BlockPlaceEvent event) {
        IUser user = bedrock.getUser(event.getPlayer());
    
        if (user.isAfk()) {
            user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
        }
    }
    
    @EventHandler
    public void onBlockBreak(BlockBreakEvent event) {
        IUser user = bedrock.getUser(event.getPlayer());
    
        if (user.isAfk()) {
            user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
        }
    }
    
    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        IUser user = bedrock.getUser(event.getPlayer());
    
        if (user.isAfk()) {
            user.setAfk(false, translate(Message.AFK_BACK_MESSAGE, user.getDisplayName()));
        }
    }
    
    @EventHandler
    public void onTeleport(PlayerTeleportEvent event) {
        IUser user = Bedrock.getUser(event.getPlayer());
        
        user.setLastLocation(event.getFrom());
    }
    
}
