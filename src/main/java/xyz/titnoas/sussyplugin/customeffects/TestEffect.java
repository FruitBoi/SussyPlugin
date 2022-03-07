package xyz.titnoas.sussyplugin.customeffects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.Listener;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.List;

public class TestEffect extends CustomEffect implements Listener {
    public TestEffect() {
        this.key = "TESTEFFECT";
        this.localizedName = "Test Effect";
        this.duration = 10.0f;
        Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
    }
}
