package xyz.acrylicstyle.test;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import util.ReflectionHelper;
import xyz.acrylicstyle.authlib.GameProfile;
import xyz.acrylicstyle.authlib.properties.Property;
import xyz.acrylicstyle.authlib.properties.PropertyMap;
import xyz.acrylicstyle.craftbukkit.v1_8_R3.entity.CraftPlayer;
import xyz.acrylicstyle.minecraft.*;
import xyz.acrylicstyle.tomeito_api.command.PlayerCommandExecutor;

import java.util.Objects;

public class NMSTest extends JavaPlugin {
    @Override
    public void onEnable() {
        Objects.requireNonNull(Bukkit.getPluginCommand("add")).setExecutor(new PlayerCommandExecutor() {
            @Override
            public void onCommand(Player player, String[] args) {
                Player p = Bukkit.getPlayer(args[0]);
                PlayerConnection playerConnection = new CraftPlayer(p).getHandle().playerConnection;
                try {
                    playerConnection.sendPacket(new PacketPlayOutEntityDestroy(ReflectionHelper.getFieldWithoutException(Entity.CLASS, new CraftPlayer(player).getHandle().getHandle(), "id")));
                    EntityPlayer ep = new CraftPlayer(player).getHandle();
                    GameProfile profile = ep.getProfile();
                    PropertyMap propertyMap = profile.getProperties();
                    propertyMap.get("textures").clear();
                    propertyMap.put("textures", new Property("textures", Constants.HUNTER_SKIN_VALUE, Constants.HUNTER_SKIN_SIGNATURE));
                    profile.setProperties(propertyMap);
                    EntityPlayer entityPlayer = new EntityPlayer(ep.server, MinecraftServer.getServer().getWorldServer(0), profile, ep.playerInteractManager);
                    playerConnection.sendPacket(new PacketPlayOutNamedEntitySpawn(entityPlayer));
                } catch (ReflectiveOperationException e) { throw new RuntimeException(e); }
            }
        });
    }

    public static class Constants {
        private Constants() {}

        public static final String HUNTER_SKIN_VALUE = "eyJ0aW1lc3RhbXAiOjE1ODcxMDMyMzg0NDAsInByb2ZpbGVJZCI6IjZmMGU5Y2ZjNTNiYjRkMjE5NzU1OTE2YzBkNjZiODBmIiwicHJvZmlsZU5hbWUiOiJUSElOS0lOR2NoIiwic2lnbmF0dXJlUmVxdWlyZWQiOnRydWUsInRleHR1cmVzIjp7IlNLSU4iOnsidXJsIjoiaHR0cDovL3RleHR1cmVzLm1pbmVjcmFmdC5uZXQvdGV4dHVyZS9jNTI1ZDBiYjFmNjVkY2UzN2FiNmE2NGU2MDU1MzY5NjhkM2ZhZTkyOWYzMzQwOWE1YzRjY2ZkNzg5ZTg3NDQ5In19fQ==";
        public static final String HUNTER_SKIN_SIGNATURE = "aP6a27ojUE8N+qBajuwAaVPtAdj5TL7Q9oPiRwjPjXesF+Dozs+Ro5wW+xeb+qb79nLAaHgX7m90zEg5mCG1XyLnVJWMZ7H11QTPy3bcKTgISiW2tsP8rQu8sWV+0Z6QlKcF4XN73/IC8XlNhgm45OfRzr7ifXrVcLwrE/+5oAUGECbx+1Dpbgw0gud/e3O+B1d6V1K1AcJOq7CNLYpC2Qn5q9krHJwIeQfBW6TCJDexNzsKsXBzcqo1Cq+HW8C+W9oVd7bgtgyILt5OTYOCr8pP66avr0MvLEgaQ4llnaivRx7VRPkBB1gQTMPCaOot7LgSSJEQwIDvLgWWnXizTdE4M61bPJIDaOesyc1loXOjICry9GO9g9huI5CeJHhfdOYl6hUOCwZ78hrnDqrm777io7RCgYN9x4o76WfuNabz69dDOaJIcGLFcYe42f1KAydRJY6wvHhTI5qRmGcEjAzo8jJ4w3A+4JajS/h1LSnMFibZwZbFcOL6urZDVLqUQ/cf3Vh5/H+gU8RXb9Qz+nI2ccOZH3xbxrk9zBbKL1rVsSWgEgZuEIElSdWxGppUfMQMX67oaT3MGg86z0ZMZ7hQPy21qFOU71WzV0OOhwk8oMj0AW5HxuzEODz1ay7B3JtIfLKHdISw/IMuAHazl/RgE1qXG+Fn7xvvwMdHglo=";
    }
}
