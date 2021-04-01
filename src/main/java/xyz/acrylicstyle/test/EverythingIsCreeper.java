package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

// done
public class EverythingIsCreeper extends JavaPlugin implements Listener {
    public List<UUID> ignited = new ArrayList<>();

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new VideoListener(this, "mobに近づいたら爆発する");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!VideoListener.isStarted) return;
        e.getTo().getNearbyEntities(1.3, 1.3, 1.3).forEach(entity -> {
            if (!ignited.contains(entity.getUniqueId())) {
                if (entity.getType() != EntityType.PLAYER && entity.getType().isAlive() && entity.getType().isSpawnable()) {
                    ignited.add(entity.getUniqueId());
                    entity.getWorld().createExplosion(entity.getLocation(), 6);
                }
            }
        });
    }
}
