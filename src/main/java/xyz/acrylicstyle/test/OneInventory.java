package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerSwapHandItemsEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import xyz.acrylicstyle.shared.BaseMojangAPI;

public class OneInventory extends JavaPlugin implements Listener {
    public static final String NAME = "yululi";
    public static final ItemStack ITEM = new ItemStack(Material.PLAYER_HEAD);

    static {
        SkullMeta meta = (SkullMeta) ITEM.getItemMeta();
        meta.setDisplayName(ChatColor.RED + NAME + "'s Curse");
        meta.setOwningPlayer(Bukkit.getOfflinePlayer(BaseMojangAPI.getUniqueId(NAME)));
        ITEM.setItemMeta(meta);
    }

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        new BukkitRunnable() {
            @Override
            public void run() {
                Bukkit.getOnlinePlayers().forEach(p -> {
                    for (int i = 1; i < p.getInventory().getSize(); i++) {
                        if (i < 36 || i > 39)p.getInventory().setItem(i, ITEM);
                    }
                });
            }
        }.runTaskTimer(this, 20L, 20L);
    }

    @EventHandler
    public void onBlockPlace(BlockPlaceEvent e) {
        if (e.getItemInHand().isSimilar(ITEM)
                || e.getBlockPlaced().getType() == Material.PLAYER_HEAD
                || e.getBlockPlaced().getType() == Material.PLAYER_WALL_HEAD) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDropItem(PlayerDropItemEvent e) {
        if (e.getItemDrop().getItemStack().isSimilar(ITEM)) e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerSwapHandItems(PlayerSwapHandItemsEvent e) {
        e.setCancelled(true);
    }

    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent e) {
        e.getDrops().removeIf(is -> is.isSimilar(ITEM));
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent e) {
        if (e.getSlotType() == InventoryType.SlotType.ARMOR) return;
        if (e.getClickedInventory() == null) return;
        if (e.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (e.getSlot() != 0) {
                e.setCancelled(true);
            }
        }
    }
}
