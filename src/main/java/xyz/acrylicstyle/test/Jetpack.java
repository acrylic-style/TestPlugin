package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;

public class Jetpack extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (player.isSneaking()) player.getWorld().createExplosion(player.getLocation().clone(), 1);
                });
            }
        }.runTaskTimer(this, 2, 2);
    }
}
