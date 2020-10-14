package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Sound;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.ICollectionList;
import xyz.acrylicstyle.test.utils.AtomicInt;
import xyz.acrylicstyle.test.utils.BlockType;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.Objects;

public class Blocks extends JavaPlugin implements Listener {
    public static BlockType type = BlockType.WOOD;
    public static AtomicInt time = new AtomicInt(20);
    public static VideoListener listener;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        listener = new VideoListener(this, "破壊できるブロックが20秒ごとに変わる");
        listener.on("started", o -> new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (time.get() <= 10) {
                        if (time.get() == 0) {
                            type = Objects.requireNonNull(ICollectionList.asList(BlockType.values()).shuffle().first());
                            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 2);
                            player.sendActionBar(ChatColor.GOLD + "破壊できるブロックが" + ChatColor.GREEN + type.getName() + ChatColor.GOLD + "に変更されました！");
                            time.reset();
                        } else {
                            if (time.get() <= 3)
                                player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 1);
                            player.sendActionBar("" + ChatColor.GREEN + time.get() + "...");
                        }
                    } else {
                        player.sendActionBar(ChatColor.YELLOW + "破壊できるブロック: " + ChatColor.GREEN + type.getName());
                    }
                });
                time.decrementAndGet();
            }
        }.runTaskTimer(this, 20, 20));
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!VideoListener.isStarted) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (type.getTypes() == null || !type.getTypes().contains(e.getBlock().getType())) {
            e.setCancelled(true);
            e.getPlayer().getWorld().createExplosion(null, e.getPlayer().getLocation(), 8, true);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        if (!VideoListener.isStarted) return;
        if (e.getDamager().getType() != EntityType.PLAYER) return;
        if (((Player) e.getDamager()).getGameMode() != GameMode.SURVIVAL) return;
        if (e.getCause() == EntityDamageEvent.DamageCause.ENTITY_EXPLOSION || e.getCause() == EntityDamageEvent.DamageCause.BLOCK_EXPLOSION) return; // we do not want loop
        if (type.getTypes() != null) {
            e.setCancelled(true);
            e.getDamager().getWorld().createExplosion(null, e.getDamager().getLocation(), 8, true);
        }
    }
}
