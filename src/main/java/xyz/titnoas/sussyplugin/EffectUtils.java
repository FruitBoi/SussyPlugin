// boy do i love copying and pasting

package xyz.titnoas.sussyplugin;

import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;
import xyz.titnoas.sussyplugin.customeffects.CustomEffect;
import xyz.titnoas.sussyplugin.customeffects.TestEffect;
import xyz.titnoas.sussyplugin.customenhants.*;

import java.util.ArrayList;
import java.util.List;

public class EffectUtils {
    public static List<CustomEffect> customEffects = new ArrayList<CustomEffect>();

    static void Init() {
        customEffects.clear();

        customEffects.add(new TestEffect());
    }

    static void applyCustomEffectToPlayer(Player player, CustomEffect effect, float duration) {

        PersistentDataContainer pdc = player.getPersistentDataContainer();
        NamespacedKey key = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYEFFECT" + effect.key);

        pdc.set(key, PersistentDataType.FLOAT, duration);
    }
}
