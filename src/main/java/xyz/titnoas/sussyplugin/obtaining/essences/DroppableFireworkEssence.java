package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.Color;
import org.bukkit.entity.EntityType;

public class DroppableFireworkEssence extends FireworkStarEssence implements DroppableEssence {

	public DroppableFireworkEssence(Color color, String key, String name, boolean glow, boolean lore, boolean showDisplayName) {
		super(color, key, name, glow, lore, showDisplayName);
	}

	@Override
	public EntityType getEntityType() {
		return EntityType.CREEPER;
	}

}
