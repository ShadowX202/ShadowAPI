package de.ShadowX202.shadowAPI;

import org.bukkit.plugin.java.JavaPlugin;

public class Manager {
    private static JavaPlugin plugin;

    public static void setPlugin(JavaPlugin plugin) {
        Manager.plugin = plugin;
    }

    public static JavaPlugin getPlugin() {
        return plugin;
    }
}
