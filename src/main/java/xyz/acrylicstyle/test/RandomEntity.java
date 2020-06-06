package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.ICollectionList;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.util.Objects;

public class RandomEntity extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
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
                    if (i % 5 != 0) entity.remove();
                    summon(location);
                });
            }
        }.runTaskTimer(this, 20*60, 20*60);
        Log.info("Enabled Plugin");
    }

    static void summon(Location location) {
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
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        Log.debug("Loaded chunk: " + e.getChunk());
        Log.debug("Found " + e.getChunk().getEntities().length + " entities");
        if (e.getChunk().getEntities().length > 3) return;
        ICollectionList.asList(e.getChunk().getEntities()).foreach((entity, i, a) -> {
            Log.debug("Processing entity " + i + " of " + a.size());
            Location location = entity.getLocation();
            if (i % 3 != 0) entity.remove();
            summon(location);
            summon(location);
        });
    }

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.SQUID
                || e.getEntityType() == EntityType.COD) return;
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            e.setCancelled(true);
            Location location = e.getEntity().getLocation().clone();
            if (location.getChunk().getEntities().length > 3) return;
            summon(location);
            summon(location);
        }
    }
}
