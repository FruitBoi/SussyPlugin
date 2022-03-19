package xyz.titnoas.sussyplugin.spawners;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;

public class CustomSpawnerMetadata {

	public EntityType spawnedType;
	public int spawnCount;
	public int maxSpawnDelay;
	public int delay;
	public int maxNearbyEntities;
	public int minSpawnDelay;
	public int requiredPlayerRange;
	public int spawnRange;


	public void applyTo(CreatureSpawner spawner){
		spawner.setSpawnedType(spawnedType);
		spawner.setSpawnCount(spawnCount);
		spawner.setMaxSpawnDelay(maxSpawnDelay);
		spawner.setDelay(delay);
		spawner.setMaxNearbyEntities(maxNearbyEntities);
		spawner.setMinSpawnDelay(minSpawnDelay);
		spawner.setRequiredPlayerRange(requiredPlayerRange);
		spawner.setSpawnRange(spawnRange);
	}

	public static CustomSpawnerMetadata from(CreatureSpawner spawner){

		CustomSpawnerMetadata meta = new CustomSpawnerMetadata();
		meta.spawnedType = spawner.getSpawnedType();
		meta.spawnCount = spawner.getSpawnCount();
		meta.maxSpawnDelay = spawner.getMaxSpawnDelay();
		meta.delay = spawner.getDelay();
		meta.maxNearbyEntities = spawner.getMaxNearbyEntities();
		meta.minSpawnDelay = spawner.getMinSpawnDelay();
		meta.requiredPlayerRange = spawner.getRequiredPlayerRange();
		meta.spawnRange = spawner.getSpawnRange();

		return meta;
	}
}
