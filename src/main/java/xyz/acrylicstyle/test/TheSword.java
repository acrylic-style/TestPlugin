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
import xyz.acrylicstyle.tomeito_api.utils.Log;

/**
 * The player dies instantly when player tries to attack other entities without using a sword.
 */
// done
public class TheSword extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled plugin");
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        Player damager = (Player) e.getDamager();
        ItemStack item = damager.getInventory().getItemInMainHand();
        if (item.getType() == Material.WOODEN_SWORD) {
            damager.getInventory().setItemInMainHand(null);
            damager.getWorld().playSound(damager.getLocation(), Sound.ITEM_SHIELD_BREAK, 1F, 1F);
            return;
        }
        e.setCancelled(true);
        damager.damage(1000);
    }
}
