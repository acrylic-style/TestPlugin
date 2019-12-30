package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffectType;
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
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER) {
            e.setCancelled(true);
            return;
        }
        if (!(e.getEntity() instanceof Player) || !(e.getDamager() instanceof Player)) return;
        e.setCancelled(true);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getEntityType() == EntityType.VILLAGER) {
            e.setCancelled(true);
            return;
        }
        if (e.getEntityType() != EntityType.PLAYER) return;
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && ((Player) e.getEntity()).hasPotionEffect(PotionEffectType.WATER_BREATHING)) {
            e.setDamage(e.getDamage()*2);
        } else if (e.getCause() == EntityDamageEvent.DamageCause.VOID) {
            e.setDamage(100);
        }
    }
}
