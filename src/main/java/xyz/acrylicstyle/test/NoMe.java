package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerPreLoginEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.UUID;

public class NoMe extends JavaPlugin implements Listener {
    public static final UUID uuid = UUID.fromString("1865ab8c-700b-478b-9b52-a8c58739df1a");

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onAsyncPlayerPreLogin(AsyncPlayerPreLoginEvent e) {
        if (e.getUniqueId().equals(uuid)) e.disallow(AsyncPlayerPreLoginEvent.Result.KICK_BANNED, "You are banned from this server!");
    }
}
