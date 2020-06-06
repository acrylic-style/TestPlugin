package xyz.acrylicstyle.test;

import net.minecraft.server.v1_15_R1.ContainerPlayer;
import net.minecraft.server.v1_15_R1.EntityHuman;
import net.minecraft.server.v1_15_R1.EntityPlayer;
import net.minecraft.server.v1_15_R1.FoodMetaData;
import net.minecraft.server.v1_15_R1.PlayerInventory;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftHumanEntity;
import org.bukkit.craftbukkit.v1_15_R1.entity.CraftPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityRegainHealthEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerRespawnEvent;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.jetbrains.annotations.Nullable;
import util.CollectionList;
import util.reflect.Ref;
import util.reflect.RefField;
import xyz.acrylicstyle.tomeito_api.utils.Log;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public class SharedInventory extends JavaPlugin implements Listener {
    private static Player firstJoined = null;

    @Override
    public void onEnable() {
        Bukkit.getPluginManager().registerEvents(this, this);
        runTask();
    }

    public void runTask() {
        new BukkitRunnable() {
            @Override
            public void run() {
                getPlayers().forEach(Player::updateInventory);
            }
        }.runTaskTimer(this, 2, 2);
    }

    public static CollectionList<Player> getPlayers() {
        return new CollectionList<>(Bukkit.getOnlinePlayers()).filter(p -> !p.equals(getPlayer())).map(player -> (Player) player);
    }

    @Nullable
    public static Player getPlayer() {
        return firstJoined;
    }

    @EventHandler
    public void onEntityRegainHealth(EntityRegainHealthEvent e) {
        if (e.getEntity() instanceof Player) {
            Bukkit.getOnlinePlayers().forEach(player -> {
                if (player.getUniqueId().equals(e.getEntity().getUniqueId())) player.setHealth(player.getHealth() + e.getAmount());
            });
        }
    }

    @EventHandler
    public void onPlayerRespawn(PlayerRespawnEvent e) {
        Bukkit.getOnlinePlayers().forEach(p -> {
            p.spigot().respawn();
            set(p);
        });
        new BukkitRunnable() {
            @Override
            public void run() {
                SharedInventory.this.runTask();
            }
        }.runTaskLater(this, 2);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        if (firstJoined == null) firstJoined = e.getPlayer();
        if (e.getPlayer().equals(getPlayer())) return;
        set(e.getPlayer());
    }

    public void set(Player player) {
        if (player.equals(getPlayer())) return;
        EntityPlayer handle = ((CraftPlayer) player).getHandle();
        EntityPlayer handle2 = ((CraftPlayer) getPlayer()).getHandle();
        Log.info("Replacing foodData");
        FoodMetaData foodData = (FoodMetaData) Ref.getDeclaredField(EntityHuman.class, "foodData").accessible(true).get(handle2);
        Ref.getDeclaredField(EntityHuman.class, "foodData").accessible(true).set(handle, foodData);
        Log.info("Replacing inventory");
        PlayerInventory inventory = handle2.inventory;
        RefField<EntityHuman> field = Ref.getField(EntityHuman.class, "inventory").accessible(true);
        Ref.getDeclaredField(Field.class, "modifiers").accessible(true).setInt(field.getField(), field.getModifiers() & ~Modifier.FINAL);
        field.set(handle, inventory);
        Ref.getDeclaredField(CraftHumanEntity.class, "inventory")
                .accessible(true)
                .set(
                        (CraftPlayer) player,
                        Ref.getDeclaredField(CraftHumanEntity.class, "inventory").accessible(true).get(((CraftPlayer) getPlayer()))
                );
        Log.info("Replacing defaultContainer");
        ContainerPlayer container = handle2.defaultContainer;
        RefField<EntityHuman> field2 = Ref.getField(EntityHuman.class, "defaultContainer").accessible(true);
        Ref.getDeclaredField(Field.class, "modifiers").accessible(true).setInt(field2.getField(), field2.getModifiers() & ~Modifier.FINAL);
        field2.set(handle, container);
        handle.activeContainer = container;
    }
}
