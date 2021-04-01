package xyz.acrylicstyle.test;

import org.bukkit.plugin.java.JavaPlugin;
import xyz.acrylicstyle.test.utils.VideoListener;

// empty thing
public class VL extends JavaPlugin {
    @Override
    public void onEnable() {
        new VideoListener(this, "");
    }
}
