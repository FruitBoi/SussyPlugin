package xyz.titnoas.sussyplugin.utilshit;

import org.bukkit.Color;
import org.bukkit.NamespacedKey;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeModifier;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;

public class CustomEffectType extends PotionEffectType {

    public static NamespacedKey customKey;

    public CustomEffectType() {
        super(32, customKey);
    }

    @Override
    public double getDurationModifier() {
        return 0;
    }

    @Override
    public @NotNull String getName() {
        return "cock";
    }

    @Override
    public boolean isInstant() {
        return false;
    }

    @Override
    public @NotNull Color getColor() {
        return Color.fromRGB(0x516273);
    }

    @Override
    public @NotNull Map<Attribute, AttributeModifier> getEffectAttributes() {
        return new HashMap<>();
    }

    @Override
    public double getAttributeModifierAmount(@NotNull Attribute attribute, int i) {
        return 0;
    }

    @Override
    public @NotNull Category getEffectCategory() {
        return Category.HARMFUL;
    }

    @Override
    public @NotNull String translationKey() {
        return getName();
    }
}
