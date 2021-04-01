package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.MathUtils;
import xyz.acrylicstyle.test.utils.VideoListener;

public class AnvilRain extends JavaPlugin {
    public static VideoListener vl;

    @Override
    public void onEnable() {
        vl = new VideoListener(this, "毎秒金床が降ってくる");
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!VideoListener.isStarted) return;
                Bukkit.getOnlinePlayers().forEach(player -> {
                    for (int i = 0; i < 3; i++) {
                        int randomX = MathUtils.randomNumber(10);
                        int randomZ = MathUtils.randomNumber(10);
                        Location randomLoc = player.getLocation().clone().add(randomX-5, 25, randomZ-5);
                        randomLoc.getBlock().setType(Material.ANVIL);
                    }
                });
            }
        }.runTaskTimer(this, 20, 20);
    }
}
