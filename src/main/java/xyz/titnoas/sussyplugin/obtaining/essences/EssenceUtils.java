package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.plugin.PluginManager;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.customenhants.CustomEnchant;
import xyz.titnoas.sussyplugin.customenhants.Soulbound;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EssenceUtils {

	public static void RegisterEssences() {
		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(143, 227, 143), "CREEPER_ESSENCE",
				"Creeper Essence", EntityType.CREEPER, true, false, true));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(74, 111, 40), "ZOMBIE_ESSENCE",
				"Zombie Essence", EntityType.ZOMBIE, true, false, true));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(137, 50, 183), "SHULKER_ESSENCE",
				"Shulker Essence", EntityType.SHULKER, true, false, true));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(222, 122, 250), "ENDERMAN_ESSENCE",
				"Ender Essence", EntityType.ENDERMAN, true, false, true));



		PluginManager manager = Bukkit.getPluginManager();
		NamespacedKey soulboundRecipe2Key = new NamespacedKey(SussyPlugin.sussyPlugin, "SoulboundBookFromEssenceLevel2");
		ShapedRecipe soulboundRecipe2 = new ShapedRecipe(soulboundRecipe2Key, ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(Soulbound.class), 2));

		soulboundRecipe2.shape("EOE", "OFO", "EOE");
		soulboundRecipe2.setIngredient('E', EssenceUtils.GetEssenceByKey("SHULKER_ESSENCE").getRecipeChoice(3));
		soulboundRecipe2.setIngredient('O', Material.DIAMOND);
		soulboundRecipe2.setIngredient('F', EssenceUtils.GetEssenceByKey("ENDERMAN_ESSENCE").getRecipeChoice(3));
		Bukkit.addRecipe(soulboundRecipe2);

		NamespacedKey soulboundRecipe1Key = new NamespacedKey(SussyPlugin.sussyPlugin, "SoulboundBookFromEssenceLevel1");
		ShapedRecipe soulboundRecipe1 = new ShapedRecipe(soulboundRecipe1Key, ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(Soulbound.class), 1));

		soulboundRecipe1.shape("E E", "   ", "E E");
		soulboundRecipe1.setIngredient('E', EssenceUtils.GetEssenceByKey("SHULKER_ESSENCE").getRecipeChoice(3));

		Bukkit.addRecipe(soulboundRecipe1);

		for (Essence essence : GetAllEssences())
		{
			for (int i = 2; i <= 10; i++)
			{
				NamespacedKey upkey = new NamespacedKey(SussyPlugin.sussyPlugin, "Essence" + essence.key + i);
				NamespacedKey downkey = new NamespacedKey(SussyPlugin.sussyPlugin, "Essence" + essence.key + i + "d");
				ShapedRecipe recipe = new ShapedRecipe(upkey, essence.CreateItem(1, i));
				recipe.shape("EEE", "EEE", "EEE");

				RecipeChoice choice = new RecipeChoice.ExactChoice(essence.CreateItem(1, i - 1));
				recipe.setIngredient('E', choice);
				Bukkit.addRecipe(recipe);

				RecipeChoice choiceDown = new RecipeChoice.ExactChoice(essence.CreateItem(1, i));
				ShapelessRecipe down = new ShapelessRecipe(downkey, essence.CreateItem(9, i - 1));
				down.addIngredient(choiceDown);
				Bukkit.addRecipe(down);
			}
		}
	}

	public static <E> E getWeightedRandom(Map<E, Double> weights, Random random) {
		E result = null;
		double bestValue = Double.MAX_VALUE;

		for (E element : weights.keySet()) {
			double value = -Math.log(random.nextDouble()) / weights.get(element);

			if (value < bestValue) {
				bestValue = value;
				result = element;
			}
		}

		return result;
	}

	public static DroppableEssence GetDroppableEssence(Essence essence) {
		if (essence instanceof DroppableEssence e)
			return e;

		return null;
	}

	public static boolean IsEssence(ItemStack stack) {
		return ItemUtils.ItemHasEnchantOfType(Essence.class, stack);
	}

	public static List<Essence> GetAllEssences() {

		List<Essence> essences = new ArrayList<>();

		for (CustomEnchant enc : ItemUtils.customEnchants) {
			if (enc instanceof Essence essence)
				essences.add(essence);
		}
		return essences;
	}

	public static Essence GetEssenceTypeFromItem(ItemStack stack) {

		for (CustomEnchant enchant : ItemUtils.GetItemCustomEnchants(stack)) {

			if (enchant instanceof Essence essence)
				return essence;
		}
		return null;
	}

	public static <T extends Essence> Essence GetEssenceOfType(Class<T> type){
		return (Essence) ItemUtils.GetCustomEnchantOfType(type);
	}

	public static List<DroppableEssence> FindDroppableByEntity(Entity type) {

		List<DroppableEssence> ret = new ArrayList<>();

		for (CustomEnchant enchant : ItemUtils.customEnchants) {
			if (enchant instanceof DroppableEssence essence && essence.isValidEntity(type))
				ret.add(essence);
		}
		return ret;
	}

	public static Essence GetEssenceByKey(String key) {
		return (Essence) ItemUtils.GetEnchantByKey(key);
	}

}
