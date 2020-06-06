package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.CollectionSet;
import xyz.acrylicstyle.test.utils.VideoListener;

// do not use this
public class Blocks2 extends JavaPlugin implements Listener {
    public static CollectionSet<Material> allowedMaterials = new CollectionSet<>();
    static {
        allowedMaterials.add(Material.STONE);
        allowedMaterials.add(Material.DIRT);
        allowedMaterials.add(Material.GRASS_BLOCK);
        allowedMaterials.add(Material.GRASS_PATH);
        allowedMaterials.add(Material.GRASS);
        allowedMaterials.add(Material.TALL_GRASS);
        allowedMaterials.add(Material.TALL_SEAGRASS);
    }

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @SuppressWarnings("RedundantCollectionOperation")
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (!allowedMaterials.contains(player.getInventory().getItemInMainHand().getType())) {
                        allowedMaterials.add(player.getInventory().getItemInMainHand().getType());
                    }
                });
            }
        }.runTaskTimer(this, 20, 20);
        Bukkit.getPluginManager().registerEvents(this, this);
        new VideoListener(this, "土+石+(持ったブロックを追加)しか踏めない");
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (!allowedMaterials.contains(e.getBlock().getType())) {
            e.setCancelled(true);
            e.getPlayer().damage(1000);
        }
    }
}
