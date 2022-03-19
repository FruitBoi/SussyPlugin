package xyz.titnoas.sussyplugin.spawners;

import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.EntityType;
import org.bukkit.persistence.PersistentDataAdapterContext;
import org.bukkit.persistence.PersistentDataType;
import org.jetbrains.annotations.NotNull;

import java.nio.ByteBuffer;

public class CustomSpawnerMetadataPersistentType implements PersistentDataType<byte[], CustomSpawnerMetadata> {


	@Override
	public @NotNull Class<byte[]> getPrimitiveType() {
		return byte[].class;
	}

	@Override
	public @NotNull Class<CustomSpawnerMetadata> getComplexType() {
		return CustomSpawnerMetadata.class;
	}

	@Override
	public byte @NotNull [] toPrimitive(@NotNull CustomSpawnerMetadata creatureSpawner, @NotNull PersistentDataAdapterContext context) {

		ByteBuffer buff = ByteBuffer.wrap(new byte[32]);
		buff.putInt(creatureSpawner.spawnedType.ordinal());
		buff.putInt(creatureSpawner.spawnCount);
		buff.putInt(creatureSpawner.maxSpawnDelay);
		buff.putInt(creatureSpawner.delay);
		buff.putInt(creatureSpawner.maxNearbyEntities);
		buff.putInt(creatureSpawner.minSpawnDelay);
		buff.putInt(creatureSpawner.spawnRange);

		return buff.array();
	}

	@Override
	public @NotNull CustomSpawnerMetadata fromPrimitive(byte @NotNull [] bytes, @NotNull PersistentDataAdapterContext context) {
		ByteBuffer buff = ByteBuffer.wrap(bytes);

		CustomSpawnerMetadata meta = new CustomSpawnerMetadata();

		meta.spawnedType = EntityType.values()[buff.getInt()];
		meta.spawnCount = buff.getInt();
		meta.maxSpawnDelay = buff.getInt();
		meta.delay = buff.getInt();
		meta.maxNearbyEntities = buff.getInt();
		meta.minSpawnDelay = buff.getInt();
		meta.requiredPlayerRange = buff.getInt();
		meta.spawnRange = buff.getInt();

		return meta;
	}
}
