package com.njdaeger.bedrock.api;

import org.bukkit.GameMode;

public enum Gamemode {
    
    SURVIVAL(GameMode.SURVIVAL, "Survival", "0", "s", "surv", "smp"),
    SPECTATOR(GameMode.SPECTATOR, "Spectator", "3", "sp", "spect"),
    CREATIVE(GameMode.CREATIVE, "Creative", "1", "c", "cr", "cmp"),
    ADVENTURE(GameMode.ADVENTURE, "Adventure", "2", "a", "adv", "amp");
    
    private GameMode bukkitMode;
    private String[] aliases;
    private String nicename;
    
    Gamemode(GameMode bukkitMode, String... aliases) {
        this.aliases = aliases;
        this.nicename = aliases[0];
        this.bukkitMode = bukkitMode;
    }
    
    public String getNicename() {
        return nicename;
    }
    
    public String[] getAliases() {
        return aliases;
    }
    
    public GameMode getBukkitMode() {
        return bukkitMode;
    }
    
    public static Gamemode resolveGamemode(String alias) {
        int last = -1;
        int iter = 0;
        Gamemode best = Gamemode.SURVIVAL;
        for (Gamemode gm : Gamemode.values()) {
            if (alias.equalsIgnoreCase(gm.toString()))
                return gm;
    
            for (String al : gm.getAliases()) {
                if (alias.equalsIgnoreCase(al))
                    return gm;
            }
        }
        for (Gamemode gm : Gamemode.values()) {
            iter++;
            int current = distance(gm.toString().toLowerCase(), alias);
            if (last == -1) {
                last = current;
                best = gm;
            }
            else {
                if (last > current) {
                    last = current;
                    best = gm;
                }
                if (iter >= 4) {
                    return best;
                }
            }
        }
        return best;
    }
    
    private static int distance(String base, String check) {
        
        int a = base.length();
        int b = check.length();
        
        int[][] dist = new int[a + 1][b +1];
        
        for (int i = 0; i <= a; i++) {
            dist[i][0] = i;
        }
        for (int i = 1; i <= b; i++) {
            dist[0][i] = i;
        }
        for (int i = 1; i <= a; i++)
            for (int j = 1; j <= b; j++)
                dist[i][j] = Math.min(Math.min(dist[i - 1][j] + 1, dist[i][j - 1] + 1), dist[i - 1][j - 1] + ((base.charAt(i - 1) == check.charAt(j - 1)) ? 0 : 1));
        
        return dist[a][b];
        
    }
}
