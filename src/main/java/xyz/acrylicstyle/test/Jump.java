package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.test.utils.AtomicInt;
import xyz.acrylicstyle.test.utils.VideoListener;
import xyz.acrylicstyle.tomeito_api.TomeitoAPI;
import xyz.acrylicstyle.tomeito_api.sounds.Sound;

public class Jump extends JavaPlugin {
    public static VideoListener vl;
    public static int currentAmplifier = 0;
    public static AtomicInt count = new AtomicInt(60);

    @Override
    public void onEnable() {
        vl = new VideoListener(this, "毎分ジャンプの高さが上がる");
        vl.on("started", o -> new BukkitRunnable() {
            @Override
            public void run() {
                if (!VideoListener.isStarted) return;
                Bukkit.getOnlinePlayers().forEach(p -> {
                    if (count.get() != 0 && count.get() < 57) p.sendActionBar(Integer.toString(count.get()));
                    if (count.get() == 0) p.sendActionBar(ChatColor.GOLD + "ジャンプの高さが上がりました！");
                });
                if (count.get() == 0) {
                    TomeitoAPI.broadcastSound(Sound.BLOCK_NOTE_PLING, 1);
                    currentAmplifier++;
                    count.reset();
                }
                count.decrementAndGet();
            }
        }.runTaskTimer(this, 20, 20));
        new BukkitRunnable() {
            @Override
            public void run() {
                if (!VideoListener.isStarted) return;
                Bukkit.getOnlinePlayers().forEach(p -> p.addPotionEffect(new PotionEffect(PotionEffectType.JUMP, 1000000, currentAmplifier)));
            }
        }.runTaskTimer(this, 20, 20);
    }

    //@EventHandler
    //public void onPlayerJump(PlayerJumpEvent e) {
    //    //
    //}
}
