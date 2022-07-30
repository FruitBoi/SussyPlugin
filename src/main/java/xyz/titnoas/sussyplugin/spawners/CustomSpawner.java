package xyz.titnoas.sussyplugin.spawners;

import net.kyori.adventure.text.Component;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.craftbukkit.v1_18_R2.inventory.CraftItemStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.SpawnerSpawnEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;

public class CustomSpawner implements Listener {

	public static NamespacedKey customSpawnerMetaKey;

	public static void Init(){

	}

	@EventHandler
	public void onMobSpawner(SpawnerSpawnEvent ev){

	}


	public static CustomSpawnerMetadata fromSpawnerItem(ItemStack item){

		ItemMeta itemMeta = item.getItemMeta();

		var pdc = itemMeta.getPersistentDataContainer();

		if(!pdc.has(customSpawnerMetaKey))
			return null;

		CustomSpawnerMetadataPersistentType persistentType = new CustomSpawnerMetadataPersistentType();

		return pdc.get(customSpawnerMetaKey, persistentType);
	}


	public static ItemStack toSpawnerItem(CreatureSpawner spawner){
		CustomSpawnerMetadata meta = CustomSpawnerMetadata.from(spawner);
		return toSpawnerItem(meta);
	}

	public static ItemStack toSpawnerItem(CustomSpawnerMetadata meta){

		ItemStack item = new ItemStack(Material.SPAWNER);

		ItemMeta itemmeta = item.getItemMeta();

		PersistentDataContainer container = itemmeta.getPersistentDataContainer();

		CustomSpawnerMetadataPersistentType spawnerMetadataPersistentType = new CustomSpawnerMetadataPersistentType();

		container.set(customSpawnerMetaKey, spawnerMetadataPersistentType, meta);
		//itemmeta.displayName(Component.text(spawner.getSpawnedType().));
		item.setItemMeta(itemmeta);

		return item;
	}

}
