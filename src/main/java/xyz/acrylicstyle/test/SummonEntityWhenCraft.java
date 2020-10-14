package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.CraftItemEvent;
import org.bukkit.plugin.java.JavaPlugin;

// done
public class SummonEntityWhenCraft extends JavaPlugin implements Listener {
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onCraftItem(CraftItemEvent e) {
        Location location = e.getWhoClicked().getLocation();
        World world = location.getWorld();
        assert world != null;
        world.spawnEntity(location, EntityType.CREEPER);
        world.spawnEntity(location, EntityType.SKELETON);
        // world.spawnEntity(location, ICollectionList.asList(EntityType.values()).filter(t -> t != EntityType.PLAYER && t.isSpawnable()).shuffle().first());
    }
}