package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.test.utils.VideoListener;
import xyz.acrylicstyle.tomeito_api.utils.Log;

/**
 * Player takes its damage back when player tries to attack other entities without using sword.
 */
public class NotSelfHarm extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled plugin");
        new VideoListener(this, "木の剣以外で殴れない");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getInventory().getItemInMainHand();
        if (item.getType() == Material.WOODEN_SWORD) {
            damager.playSound(damager.getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            damager.getInventory().setItemInMainHand(null);
            return;
        }
        e.setCancelled(true);
        damager.setFallDistance(e.getEntity().getFallDistance());
        damager.setFireTicks(e.getEntity().getFireTicks());
        damager.damage(500);
        damager.setVelocity(e.getEntity().getVelocity().multiply(-1));
    }
}
