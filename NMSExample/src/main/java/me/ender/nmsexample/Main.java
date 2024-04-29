package me.ender.nmsexample;

import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    @Override
    public void onEnable() {
        new MovementListener(this);

    }

    @Override
    public void onDisable() {
        // Plugin shutdown logic
    }
}
