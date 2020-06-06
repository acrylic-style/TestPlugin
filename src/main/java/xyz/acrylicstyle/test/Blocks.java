package xyz.acrylicstyle.test;

import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.ICollectionList;
import xyz.acrylicstyle.test.utils.AtomicInt;
import xyz.acrylicstyle.test.utils.BlockType;

import java.util.Objects;

public class Blocks extends JavaPlugin implements Listener {
    public static BlockType type = BlockType.STONE;
    public static AtomicInt time = new AtomicInt(20);

    @Override
    public void onEnable() {
        new BukkitRunnable() {
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
                            if (time.get() <= 3) player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 0.8F, 1);
                            player.sendActionBar("" + ChatColor.GREEN + time.get() + "...");
                        }
                    }
                    time.decrementAndGet();
                });
            }
        }.runTaskTimer(this, 20, 20);
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (!type.getTypes().contains(e.getBlock().getType())) {
            e.setCancelled(true);
            e.getPlayer().damage(1000);
        }
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent e) {
        //
    }
}
