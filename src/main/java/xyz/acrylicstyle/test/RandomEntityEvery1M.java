package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.ICollectionList;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.util.Objects;

@SuppressWarnings("DuplicatedCode")
public class RandomEntityEvery1M extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Plugin");
        new BukkitRunnable() {
            @Override
            public void run() {
                ICollectionList.asList(Objects.requireNonNull(Bukkit.getWorld("world")).getEntities()).filter(t ->
                        t.getType() != EntityType.PLAYER
                                && t.getType() != EntityType.LEASH_HITCH
                                && t.getType().isSpawnable()
                                && t.getType() != EntityType.PAINTING
                                && t.getType() != EntityType.ITEM_FRAME
                                && t.getType() != EntityType.DROPPED_ITEM
                                && t.getType() != EntityType.ENDER_DRAGON
                                && t.getType() != EntityType.WITHER
                                && t.getType() != EntityType.ELDER_GUARDIAN
                ).foreach((entity, i, a) -> {
                    Location location = entity.getLocation();
                    entity.remove();
                    Objects.requireNonNull(location.getWorld())
                            .spawnEntity(
                                    location,
                                    Objects.requireNonNull(ICollectionList.asList(EntityType.values()).filter(t ->
                                            t != EntityType.PLAYER
                                                    && t != EntityType.LEASH_HITCH
                                                    && t.isSpawnable()
                                                    && t != EntityType.PAINTING
                                                    && t != EntityType.ITEM_FRAME
                                                    && t != EntityType.DROPPED_ITEM
                                                    && t != EntityType.ENDER_DRAGON
                                                    && t != EntityType.WITHER
                                                    && t != EntityType.ELDER_GUARDIAN
                                    ).shuffle().first())
                            );
                });
            }
        }.runTaskTimer(this, 20*60, 20*60);
    }
}
