// boy do i love copying and pasting

package xyz.titnoas.sussyplugin;

import org.bukkit.inventory.ItemStack;
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
}
