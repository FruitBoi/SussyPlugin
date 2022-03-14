package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;

public interface DroppableEssence {

	boolean isValidEntity(Entity ent);

	float chanceMultiplier();
}
