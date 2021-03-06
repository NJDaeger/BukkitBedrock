package com.njdaeger.bedrock.api.command.exceptions;

import com.njdaeger.bci.base.BCIException;
import com.njdaeger.btu.ActionBar;
import com.njdaeger.btu.Text;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class BedrockException extends BCIException {
    
    public BedrockException(String message) {
        super(message);
    }
    
    @Override
    public void showError(CommandSender sender) {
        if (sender instanceof Player) {
            Player player = (Player)sender;
            player.playSound(player.getLocation(), Sound.UI_TOAST_IN, SoundCategory.MASTER, 1, 1);
            ActionBar.of(Text.of(getMessage())).sendTo(player);
        }
        else super.showError(sender);
    }
}
