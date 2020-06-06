package xyz.acrylicstyle.test.utils;

import org.bukkit.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Objects;

public class VideoListener implements Listener {
    public static boolean isStarted = false;
    public String objective;
    private BukkitRunnable runnable = null;

    public VideoListener(@NotNull JavaPlugin plugin) {
        this(plugin, "???");
    }

    public VideoListener(@NotNull JavaPlugin plugin, String what) {
        this.objective = what;
        Objects.requireNonNull(Bukkit.getPluginCommand("start")).setExecutor((sender, command, label, args) -> {
            VideoListener.isStarted = true;
            Bukkit.broadcastMessage(ChatColor.YELLOW + "ゲームが始まりました！");
            Bukkit.broadcastMessage(ChatColor.YELLOW + "縛り内容: " + ChatColor.GREEN + objective);
            new ArrayList<>(Bukkit.getOnlinePlayers()).forEach(p -> p.playSound(p.getLocation(), Sound.ENTITY_PLAYER_LEVELUP, 100, 1));
            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!runnable.isCancelled()) runnable.cancel();
                }
            }.runTaskLater(plugin, 20*10);
            return true;
        });
        Bukkit.getPluginManager().registerEvents(this, plugin);
        (runnable = new BukkitRunnable() {
            @Override
            public void run() {
                String status;
                if (VideoListener.isStarted) {
                    status = "プレイ中";
                } else {
                    status = "待機中";
                }
                new ArrayList<>(Bukkit.getOnlinePlayers()).forEach(p -> p.sendActionBar("" + ChatColor.GOLD + ChatColor.BOLD + "縛り内容: " + ChatColor.LIGHT_PURPLE + ChatColor.BOLD + objective + " " + ChatColor.GRAY + status));
            }
        }).runTaskTimer(plugin, 0, 20);
    }

    @EventHandler
    public void onBlockBreak(BlockBreakEvent e) {
        if (!isStarted) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (e.getBlock().getType() == Material.DIAMOND_ORE) {
            isStarted = false;
            Bukkit.getOnlinePlayers().forEach(player -> {
                player.playSound(player.getLocation(), Sound.UI_TOAST_CHALLENGE_COMPLETE, 1, 1);
                player.sendTitle(ChatColor.GOLD + "Congratulations!", ChatColor.YELLOW + "クリアおめでとう！", 10, 80, 10);
                player.sendMessage(ChatColor.YELLOW + e.getPlayer().getName() + "がダイヤモンドを採掘したので、チャレンジがクリアされました！");
            });
        }
    }
}
