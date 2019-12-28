package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import util.Collection;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.util.UUID;

public class TestPlugin extends JavaPlugin implements Listener {
    Collection<UUID, ArmorStand> armorStands = new Collection<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        ArmorStand armorStand = (ArmorStand) e.getPlayer().getWorld().spawnEntity(e.getPlayer().getLocation().add(0, 0, 0), EntityType.ARMOR_STAND);
        armorStand.setCustomName(ChatColor.LIGHT_PURPLE + e.getPlayer().getName());
        armorStand.setCustomNameVisible(true);
        armorStand.setVisible(false);
        armorStand.setSmall(true);
        armorStands.add(e.getPlayer().getUniqueId(), armorStand);
        e.getPlayer().setPassenger(armorStand);
    }

    @EventHandler
    public void onPlayerQuit(PlayerQuitEvent e) {
        armorStands.remove(e.getPlayer().getUniqueId()).remove();
    }
}
