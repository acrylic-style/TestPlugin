package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.CreatureSpawnEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.plugin.java.JavaPlugin;
import util.ICollectionList;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.util.Objects;

public class RandomEntity extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Plugin");
    }

    @EventHandler
    public void onChunkLoad(ChunkLoadEvent e) {
        Log.debug("Loaded chunk: " + e.getChunk());
        Log.debug("Found " + e.getChunk().getEntities().length + " entities");
        if (e.getChunk().getEntities().length > 3) return;
        ICollectionList.asList(e.getChunk().getEntities()).foreach((entity, i, a) -> {
            Log.debug("Processing entity " + i + " of " + a.size());
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

    @EventHandler
    public void onCreatureSpawn(CreatureSpawnEvent e) {
        if (e.getEntityType() == EntityType.SQUID
                || e.getEntityType() == EntityType.COD) return;
        if (e.getSpawnReason() == CreatureSpawnEvent.SpawnReason.NATURAL) {
            e.setCancelled(true);
            Location location = e.getEntity().getLocation().clone();
            if (location.getChunk().getEntities().length > 3) return;
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
    }
}
