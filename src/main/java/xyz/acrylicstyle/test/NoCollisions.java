package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.scoreboard.Team;

public class NoCollisions extends JavaPlugin implements Listener {
    private Team noCollisions = null;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        Scoreboard scoreboard = Bukkit.getScoreboardManager().getMainScoreboard();
        noCollisions = scoreboard.getTeam("noCollisions") != null ? scoreboard.getTeam("noCollisions") : scoreboard.registerNewTeam("noCollisions");
        noCollisions.setOption(Team.Option.COLLISION_RULE, Team.OptionStatus.NEVER);
        // Log.info("Enabled Test plugin. Enjoy testing.");
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        noCollisions.addEntry(e.getPlayer().getName());
    }
}
