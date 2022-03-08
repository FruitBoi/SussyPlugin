package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.customenhants.CustomEnchant;

public class EssenceUtils {

	public static void RegisterEssences(){


	}

	public static Essence GetEssenceByKey(String key){
		return (Essence) ItemUtils.GetEnchantByKey(key);
	}

	public static <T extends CustomEnchant> boolean ItemHasEnchantOfType(ItemStack item){

		var enchants = ItemUtils.GetItemCustomEnchants(item);

		if(enchants.isEmpty())
			return false;

		for(CustomEnchant enchant : enchants){

			if(enchant )
			return true;
		}
		return false;
	}

}
