package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.tomeito_core.utils.Log;

public class TestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ArmorStand armorStand = (ArmorStand) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().add(0, 0, 0), EntityType.ARMOR_STAND);
        armorStand.setCustomName(ChatColor.GREEN + "1234567890123456");
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        e.getPlayer().setPassenger(armorStand);
    }
}
