package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Creeper;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.CollectionList;
import util.MathUtils;
import xyz.acrylicstyle.test.utils.AtomicInt;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.UUID;

public class CreeperRain extends JavaPlugin implements Listener {
    public static VideoListener vl;
    public static CollectionList<UUID> uuids = new CollectionList<>();
    public static AtomicInt count = new AtomicInt(30);

    @Override
    public void onEnable() {
        vl = new VideoListener(this, "30秒ごとにクリーパーが降ってくる");
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!VideoListener.isStarted) return;
                Bukkit.getOnlinePlayers().forEach(player -> {
                    player.sendActionBar(Integer.toString(count.get()));
                    if (count.get() == 0) {
                        for (int i = 0; i < 10; i++) {
                            int randomX = MathUtils.randomNumber(10);
                            int randomZ = MathUtils.randomNumber(10);
                            Location randomLoc = player.getLocation().clone().add(randomX - 5, 25, randomZ - 5);
                            Creeper creeper = randomLoc.getWorld().spawn(randomLoc, Creeper.class);
                            uuids.add(creeper.getUniqueId());
                        }
                    }
                });
                if (count.get() == 0) count.reset();
                count.decrementAndGet();
            }
        }.runTaskTimer(this, 20, 20);
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent e) {
        if (e.getCause() == EntityDamageEvent.DamageCause.FALL && uuids.contains(e.getEntity().getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler
    public void onEntityDeath(EntityDeathEvent e) {
        uuids.remove(e.getEntity().getUniqueId());
    }
}
