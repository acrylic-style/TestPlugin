package xyz.acrylicstyle.test;

import com.google.common.annotations.Beta;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.tomeito_api.events.block.DispenserTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.block.PlayerTNTPrimeEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerJumpEvent;
import xyz.acrylicstyle.tomeito_api.events.player.PlayerPreDeathEvent;
import xyz.acrylicstyle.tomeito_api.events.server.UnknownCommandEvent;
import xyz.acrylicstyle.tomeito_api.utils.Log;

public class TomeitoLibTest extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onPlayerJump(PlayerJumpEvent e) {
        Log.info(e.getPlayer().getName() + " tried to jump");
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerTNTPrime(PlayerTNTPrimeEvent e) {
        Log.info("Tried to ignite TNT at " + e.getBlock().getLocation() + " by " + e.getPrimerEntity().getName());
        e.setCancelled(true);
    }

    @EventHandler
    public void onDispenserTNTPrime(DispenserTNTPrimeEvent e) {
        Log.info("Tried to ignite TNT at dispenser, at " + e.getBlock().getLocation());
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerPreDeath(PlayerPreDeathEvent e) {
        Log.info("Player " + e.getPlayer().getName() + " was about to death");
        e.setCancelled(true);
    }

    @SuppressWarnings("UnstableApiUsage")
    @Beta
    @EventHandler
    public void onUnknownCommand(UnknownCommandEvent e) {
        e.setMessage("We don't know that command");
    }
}
