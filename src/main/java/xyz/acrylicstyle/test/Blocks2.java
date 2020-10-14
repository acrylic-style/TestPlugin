package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.GameMode;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerAttemptPickupItemEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import util.CollectionList;
import util.CollectionSet;
import xyz.acrylicstyle.test.utils.VideoListener;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class Blocks2 extends JavaPlugin implements Listener {

    public static CollectionList<UUID> list = new CollectionList<>();
    public static final List<Material> ignoredBlocks = new ArrayList<>();
    public static final List<UUID> invulnerable = new ArrayList<>();
    public static CollectionSet<Material> allowedMaterials = new CollectionSet<>();

    static {
        allowedMaterials.add(Material.STONE);
        allowedMaterials.add(Material.DIRT);
        allowedMaterials.add(Material.GRASS_BLOCK);
        allowedMaterials.add(Material.GRASS_PATH);
        allowedMaterials.add(Material.GRASS);
        allowedMaterials.add(Material.TALL_GRASS);
        allowedMaterials.add(Material.TALL_SEAGRASS);

        ignoredBlocks.add(Material.SUNFLOWER);
        ignoredBlocks.add(Material.CORNFLOWER);
        ignoredBlocks.add(Material.AZURE_BLUET);
        ignoredBlocks.add(Material.DANDELION);
        ignoredBlocks.add(Material.POPPY);
        ignoredBlocks.add(Material.LILAC);
        ignoredBlocks.add(Material.AIR);
        ignoredBlocks.add(Material.CAVE_AIR);
        ignoredBlocks.add(Material.VOID_AIR);
        ignoredBlocks.add(Material.TORCH);
        ignoredBlocks.add(Material.REDSTONE_TORCH);
        ignoredBlocks.add(Material.WALL_TORCH);
        ignoredBlocks.add(Material.REDSTONE_WALL_TORCH);
        ignoredBlocks.add(Material.TALL_GRASS);
    }

    @Override
    public void onEnable() {
        new BukkitRunnable() {
            @SuppressWarnings("RedundantCollectionOperation")
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(player -> {
                    if (!allowedMaterials.contains(player.getInventory().getItemInMainHand().getType())) {
                        allowedMaterials.add(player.getInventory().getItemInMainHand().getType());
                    }
                });
            }
        }.runTaskTimer(this, 20, 20);
        Bukkit.getPluginManager().registerEvents(this, this);
        new VideoListener(this, "土+石+(持ったブロックを追加)しか踏めない");
    }

    @EventHandler
    public void onPlayerMove(PlayerMoveEvent e) {
        if (!VideoListener.isStarted) return;
        if (invulnerable.contains(e.getPlayer().getUniqueId())) return;
        if (e.getPlayer().getGameMode() != GameMode.SURVIVAL) return;
        if (list.contains(e.getPlayer().getUniqueId())) return;
        list.add(e.getPlayer().getUniqueId());
        Block block = e.getFrom().subtract(0, 0.5D, 0).getBlock();
        if (!ignoredBlocks.contains(block.getType()) && !allowedMaterials.contains(block.getType())) {
            e.getPlayer().damage(1000);
        }
        new BukkitRunnable() {
            @Override
            public void run() {
                list.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLater(this, 5);
    }

    @EventHandler
    public void onPlayerAttemptPickupItem(PlayerAttemptPickupItemEvent e) {
        Material material = e.getItem().getItemStack().getType();
        if (!allowedMaterials.contains(material)) {
            String name = material.name().replaceAll("_", " ").toLowerCase();
            name = name.substring(0,1).toUpperCase() + name.substring(1).toLowerCase();
            Bukkit.broadcastMessage(ChatColor.YELLOW + e.getPlayer().getName() + ChatColor.GREEN + "が" + ChatColor.GOLD + name + ChatColor.GREEN + "を拾いました。");
            allowedMaterials.put(material);
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        e.getPlayer().sendMessage(ChatColor.YELLOW + "10秒間無敵になりました。");
        invulnerable.add(e.getPlayer().getUniqueId());
        new BukkitRunnable() {
            @Override
            public void run() {
                invulnerable.remove(e.getPlayer().getUniqueId());
            }
        }.runTaskLater(this, 20*10);
    }
}
