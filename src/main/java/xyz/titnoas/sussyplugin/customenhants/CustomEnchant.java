package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.List;

public abstract class CustomEnchant {

	public String key = null;

	public String localizedName = null;

	public List<Material> allowedItems = null;

	public boolean allowBookCombine = true;

	public int maxLevel = 3;

	public boolean allowGrindstone = true;

	public void onItemEnchanted(ItemStack stack){
		ItemUtils.AddEnchantGlow(stack);
	}

	public CustomEnchant(){
	}

	public boolean itemHasCustomEnchant(ItemStack stack){
		return GetItemEnchantLevel(stack) >= 0 && (allowedItems == null || allowedItems.size() == 0 || allowedItems.contains(stack.getType()));
	}

	public boolean itemHasCustomEnchantIgnoreType(ItemStack stack){
		return GetItemEnchantLevel(stack) >= 0;
	}

	public int GetItemEnchantLevel(ItemStack stack){

		if(stack == null)
			return -1;

		ItemMeta meta = stack.getItemMeta();

		if(meta == null)
			return -1;

		PersistentDataContainer dataContainer = meta.getPersistentDataContainer();

		NamespacedKey namespacedKey = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYITEM" + key);

		if(!dataContainer.has(namespacedKey, PersistentDataType.INTEGER))
			return -1;

		return dataContainer.get(namespacedKey, PersistentDataType.INTEGER);
	}
}
