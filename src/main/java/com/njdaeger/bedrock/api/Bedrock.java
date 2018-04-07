package com.njdaeger.bedrock.api;

import com.coalesce.core.Coalesce;
import com.njdaeger.bedrock.api.chat.Close;
import com.njdaeger.bedrock.api.chat.Display;
import com.njdaeger.bedrock.config.ChannelConfig;
import com.njdaeger.bedrock.config.MessageFile;
import com.njdaeger.bedrock.api.chat.IChannel;
import com.njdaeger.bedrock.api.command.BedrockCommand;
import com.njdaeger.bedrock.api.config.ISettings;
import com.njdaeger.bedrock.api.user.IUser;
import com.sun.management.OperatingSystemMXBean;
import org.bukkit.entity.Player;

import java.lang.management.ManagementFactory;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.text.DecimalFormat;
import java.util.List;

public final class Bedrock {
    
    private static IBedrock bedrock;
    
    private Bedrock() {}
    
    public static void setBedrock(IBedrock bedrock) {
        if (Bedrock.bedrock == null) {
            Bedrock.bedrock = bedrock;
        } else throw new UnsupportedOperationException("Cannot set the IBedrock class again.");
    }
    
    public static IBedrock getBedrock() {
        return bedrock;
    }
    
    public static IUser getUser(String name) {
        return bedrock.getUser(name);
    }
    
    public static IUser getUser(Player player) {
        return bedrock.getUser(player);
    }
    
    public static List<IUser> getUsers() {
        return bedrock.getUsers();
    }
    
    public static String translate(Message message, Object... placeholders) {
        return bedrock.translate(message, placeholders);
    }
    
    public static String pluginTranslate(Message message, Object... placeholders) {
        return bedrock.pluginTranslate(message, placeholders);
    }
    
    public static MessageFile getMessageFile() {
        return bedrock.getMessageFile();
    }
    
    public static ISettings getConf() {
        return bedrock.getSettings();
    }
    
    public static void debug(String message) {
        if (getConf().debug()) getBedrock().getCoLogger().debug(message);
    }
    
    public static void warn(String message) {
        getBedrock().getCoLogger().warn(message);
    }
    
    public static void registerCommand(BedrockCommand... commands) {
        bedrock.registerCommand(commands);
    }
    
    public static double getCPULoad() {
        OperatingSystemMXBean os = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        DecimalFormat format = new DecimalFormat("##.##");
        return Double.valueOf(format.format(os.getProcessCpuLoad() * 100));
    }
    
    public static int getCPUCores() {
        OperatingSystemMXBean os = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        return os.getAvailableProcessors();
    }
    
    public static String getOS() {
        OperatingSystemMXBean os = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        return os.getName();
    }
    
    public static String getArchitecture() {
        OperatingSystemMXBean os = (OperatingSystemMXBean)ManagementFactory.getOperatingSystemMXBean();
        return os.getArch();
    }
    
    public static double getMemUsage() {
        DecimalFormat format = new DecimalFormat("##.##");
        return Double.valueOf(format.format(((double)getAllocatedMemory()/(double)getMaxMemory())*100));
    }
    
    public static long getFreeMemory() {
        return Runtime.getRuntime().freeMemory() / 1024 / 1024;
    }
    
    public static long getMaxMemory() {
        return Runtime.getRuntime().maxMemory() / 1024 / 1024;
    }
    
    public static long getAllocatedMemory() {
        return getMaxMemory() - getFreeMemory();
    }
    
    public static double getTPS() {
        DecimalFormat format = new DecimalFormat("##.##");
        Object server;
        Field tpsField;
        double[] tps;
        try {
            server = Coalesce.getNMSClass("MinecraftServer").getMethod("getServer").invoke(null);
            tpsField = server.getClass().getField("recentTps");
            tps = ((double[]) tpsField.get(server));
            return Double.valueOf(format.format(tps[1]));
        } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException | NoSuchMethodException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
    public static ISettings getSettings() {
        return bedrock.getSettings();
    }
    
    public static List<IChannel> getChannels() {
        return bedrock.getChannels();
    }
    
    public static IChannel getChannel(String name) {
        return bedrock.getChannel(name);
    }
    
    public static boolean hasChannel(String name) {
        return bedrock.hasChannel(name);
    }
    
    public static boolean createChannel(String name, String prefix, Display display, String permission, Close whenToClose) {
        return bedrock.createChannel(name, prefix, display, permission, whenToClose);
    }
    
    public static boolean createChannel(String name, String prefix, Display display, String permission) {
        return bedrock.createChannel(name, prefix, display, permission);
    }
    
    public static boolean createChannel(String name, String prefix, Display display) {
        return bedrock.createChannel(name, prefix, display);
    }
    
    public static boolean createChannel(IChannel channel) {
        return bedrock.createChannel(channel);
    }
    
    public static ChannelConfig getChannelConfig() {
        return bedrock.getChannelConfig();
    }
    
    public static void closeChannel(String name) {
        bedrock.closeChannel(name);
    }
    
    public static void closeChannel(IChannel channel) {
        bedrock.closeChannel(channel);
    }
    
}
