package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.Color;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public class DroppableFireworkEssence extends FireworkStarEssence implements DroppableEssence {

	private final EntityType mob;

	public DroppableFireworkEssence(Color color, String key, String name, EntityType dropMob, boolean glow, boolean lore, boolean showDisplayName) {
		super(color, key, name, glow, lore, showDisplayName);
		this.mob = dropMob;
	}

	@Override
	public boolean isValidEntity(Entity ent) {
		return ent.getType() == mob;
	}

}
