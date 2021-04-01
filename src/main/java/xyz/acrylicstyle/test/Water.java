package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import util.CollectionSet;
import xyz.acrylicstyle.paper.DamageSource;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.UUID;

public class Water extends JavaPlugin implements Listener {
    public static VideoListener vl;
    private static final CollectionSet<UUID> cooltime = new CollectionSet<>();

    @Override
    public void onEnable() {
        vl = new VideoListener(this, "水から出たら即死");
        Bukkit.getPluginManager().registerEvents(this, this);
    }
    
    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!VideoListener.isStarted || cooltime.contains(e.getPlayer().getUniqueId())) return;
        if (!e.getPlayer().isInWater()) {
            try {
                e.getPlayer().damage(DamageSource.WITHER, 1000);
            } catch (NoSuchMethodError ex) {
                e.getPlayer().damage(1000);
            }
        }
    }
    
    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.getPlayer().sendMessage(ChatColor.YELLOW + "15秒間の無敵時間が付与されました。");
        cooltime.add(e.getPlayer().getUniqueId());
        Bukkit.getScheduler().runTaskLater(this, () -> cooltime.remove(e.getPlayer().getUniqueId()), 300L);
    }
}
