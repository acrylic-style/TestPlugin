package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.CollectionSet;
import xyz.acrylicstyle.paper.DamageSource;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.UUID;

public class NoSun extends JavaPlugin implements Listener {
    public static VideoListener vl;
    private static final CollectionSet<UUID> cooltime = new CollectionSet<>();

    @Override
    public void onEnable() {
        vl = new VideoListener(this, "Minecraft: Vampire Edition");
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                World world = Bukkit.getWorld("world");
                assert world != null;
                long time = world.getTime();
                if (time == 13000) {
                    Bukkit.broadcastMessage("夜になりました。");
                }
                if (time == 6000) {
                    Bukkit.broadcastMessage("昼になりました。");
                }
                if (time == 23000) {
                    Bukkit.broadcastMessage("朝になりました。");
                }
            }
        }.runTaskTimer(this, 1, 1);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (!VideoListener.isStarted || cooltime.contains(p.getUniqueId())) return;
                    long time = p.getWorld().getTime();
                    if (time > 13000 && time < 23000) return;
                    time = ((time + 1000) % 24000L) - 7000L;
                    float damage = Math.abs(Math.abs(time) - 7000L) / 1000F;
                    if (p.getWorld().getHighestBlockAt(p.getLocation()).getY() > p.getLocation().getBlockY()) return;
                    try {
                        p.damage(DamageSource.WITHER, damage);
                    } catch (NoSuchMethodError ex) {
                        p.damage(damage);
                    }
                });
            }
        }.runTaskTimer(this, 5L, 5L);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        cooltime.add(e.getEntity().getUniqueId());
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.getPlayer().sendMessage(ChatColor.YELLOW + "5秒間の無敵時間が付与されました。");
        Bukkit.getScheduler().runTaskLater(this, () -> cooltime.remove(e.getPlayer().getUniqueId()), 100L);
    }
}
