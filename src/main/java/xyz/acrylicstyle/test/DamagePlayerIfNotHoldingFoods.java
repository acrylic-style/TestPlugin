package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;

public class DamagePlayerIfNotHoldingFoods extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        // Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        new BukkitRunnable() {
            @Override
            public void run() {
                if (Bukkit.getPlayerExact(e.getPlayer().getName()) == null) {
                    this.cancel();
                    return;
                }
                if (!e.getPlayer().getInventory().getItemInMainHand().getType().isEdible() || (!e.getPlayer().getInventory().getItemInOffHand().getType().isEdible() && !e.getPlayer().getInventory().getItemInOffHand().getType().isAir())) {
                    e.getPlayer().addPotionEffect(new PotionEffect(PotionEffectType.POISON, 100000, 0, false, false));
                } else e.getPlayer().removePotionEffect(PotionEffectType.POISON);
            }
        }.runTaskTimer(this, 20, 2);
    }
}
