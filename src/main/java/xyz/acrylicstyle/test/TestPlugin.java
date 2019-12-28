package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityExplodeEvent;
import org.bukkit.plugin.java.JavaPlugin;
import util.ReflectionHelper;
import xyz.acrylicstyle.tomeito_core.utils.Log;

import java.util.ArrayList;
import java.util.List;

public class TestPlugin extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onEntityExplode(EntityExplodeEvent e) {
        e.setCancelled(false);
        ReflectionHelper.setFieldWithoutException(EntityExplodeEvent.class, e, "blocks", (List<Block>) new ArrayList<Block>());
        Log.info("Following blocks were affected by explosion:");
        e.blockList().forEach(block -> Log.info(block.toString()));
    }
}
