package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.tomeito_api.utils.Log;

/**
 * The player instantly dies when player tries to attack other entities without using bow(arrow).
 */
public class TheBow extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled plugin");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (!e.getPlayer().getInventory().contains(Material.BOW)) {
            ItemStack bow = new ItemStack(Material.BOW);
            ItemMeta meta = bow.getItemMeta();
            assert meta != null;
            meta.setUnbreakable(true);
            meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
            bow.setItemMeta(meta);
            e.getPlayer().getInventory().addItem(bow);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        ((Player) e.getDamager()).damage(1000);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent e) {
        Projectile projectile = e.getEntity();
        if (!(projectile.getShooter() instanceof Player)) return;
        Player shooter = (Player) projectile.getShooter();
        if (!(projectile instanceof Arrow)) {
            shooter.damage(1000);
        }
    }
}
