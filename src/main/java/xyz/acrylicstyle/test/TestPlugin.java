package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import util.CollectionList;
import util.ReflectionHelper;
import xyz.acrylicstyle.tomeito_core.utils.Log;

public class TestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(false);
        CollectionList<Block> blocks = new CollectionList<>(e.blockList());
        blocks = blocks.filter(block -> block.getType() != Material.GLASS);
        ReflectionHelper.setFieldWithoutException(EntityExplodeEvent.class, e, "blocks", /*(List<Block>)*/ blocks);
        Log.info("Following blocks were affected by explosion:");
        e.blockList().forEach(block -> Log.info(block.toString()));
    }
}
