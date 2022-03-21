package xyz.titnoas.sussyplugin.obtaining.essences;

import com.destroystokyo.paper.Namespaced;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Item;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;
import org.bukkit.plugin.PluginManager;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.customenhants.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class EssenceUtils {

	public static void RegisterEssences() {
		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(143, 227, 143), "CREEPER_ESSENCE",
				"Creeper Essence", EntityType.CREEPER, true, false, true, 1.0f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(74, 111, 40), "ZOMBIE_ESSENCE",
				"Zombie Essence", EntityType.ZOMBIE, true, false, true, 1.0f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(137, 50, 183), "SHULKER_ESSENCE",
				"Shulker Essence", EntityType.SHULKER, true, false, true, 1.0f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(222, 122, 250), "ENDERMAN_ESSENCE",
				"Ender Essence", EntityType.ENDERMAN, true, false, true, 1.0f));

		//no worky :(??
		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(205, 184, 136), "SHULKER_PROJ_ESSENCE",
				"Sky Essence", EntityType.SHULKER_BULLET, true, false, true, 1.0f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(79,72,72), "WITHER_SKELETON_ESSENCE",
				"Wither Skeleton Essence", EntityType.WITHER_SKELETON, true, false, true, 1.0f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(51,48,48), "WITHER_ESSENCE",
				"Wither Essence", EntityType.WITHER, true, false, true, 0.5f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(61,45,51), "BAT_ESSENCE",
				"Bat Essence", EntityType.BAT, true, false, true, 10f));

		ItemUtils.customEnchants.add(new DroppableFireworkEssence(Color.fromRGB(148,139,142), "SKELETON_ESSENCE",
				"Skeleton Essence", EntityType.SKELETON, true, false, true, 1.0f));


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



		//P = Shulker Proj essence
		//D = glowstone
		//E = diamond


		//Skyward 1 recipe

		//I figured since the levitation effect of the projectiles of the shulkers, it would be a good canidate for the skyward enchant.
		NamespacedKey skywardIKey = new NamespacedKey(SussyPlugin.sussyPlugin, "Skyward1BookRecipe");
		ItemStack skywardIBook = ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(UpEnchant.class), 1);

		ShapedRecipe skywardI = new ShapedRecipe(skywardIKey, skywardIBook);

		skywardI.shape("DPD", "PDP", "DPD");
		skywardI.setIngredient('D', Material.GLOWSTONE);
		skywardI.setIngredient('P', EssenceUtils.GetEssenceByKey("BAT_ESSENCE").getRecipeChoice(2));
		Bukkit.addRecipe(skywardI);


		//last stand 1 book recipe
		//T = Totem
		//W = Wither essence
		//S = Wither Skeleton Essence
		//If anyone has ideas on how to make this recipe better let me know. I think I did a pretty good job though.
		NamespacedKey lastStandIKey = new NamespacedKey(SussyPlugin.sussyPlugin, "LastStandBookRecipe");
		ItemStack lastStandIBook = ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(LastStand.class), 1);

		ShapedRecipe lastStandIBookRecipe = new ShapedRecipe(lastStandIKey, lastStandIBook);

		lastStandIBookRecipe.shape("WSW", "STS", "WSW");
		lastStandIBookRecipe.setIngredient('T', Material.TOTEM_OF_UNDYING);
		lastStandIBookRecipe.setIngredient('W', EssenceUtils.GetEssenceByKey("WITHER_ESSENCE").getRecipeChoice(3));
		lastStandIBookRecipe.setIngredient('S', EssenceUtils.GetEssenceByKey("WITHER_SKELETON_ESSENCE").getRecipeChoice(2));
		Bukkit.addRecipe(lastStandIBookRecipe);

		//Archery 1 book - combine up to level 10 which is 100% correction factor
		//According to my (probably wrong math) it would take 512 books to combine and get level 10.
		//I think personally that's a fair grind for how op that shit is, I also
		//made the recipe relatively cheap to account for people trying to get many many books to combine.
		//A = arrow;
		//S = skeleton essence
		NamespacedKey archeryIKey = new NamespacedKey(SussyPlugin.sussyPlugin, "ArcheryIBook");
		ItemStack archeryIBook = ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(Archery.class), 1);
		ShapedRecipe archeryIRecipe = new ShapedRecipe(archeryIKey, archeryIBook);

		archeryIRecipe.shape(" A ", "ASA", " A ");
		archeryIRecipe.setIngredient('A', Material.ARROW);
		archeryIRecipe.setIngredient('S', EssenceUtils.GetEssenceByKey("SKELETON_ESSENCE").getRecipeChoice(3));
		Bukkit.addRecipe(archeryIRecipe);




		//Gentle enchant recipe.

		//E - Sky Essence 2
		//F - Feather
		//S - Silk Touch I Book

		NamespacedKey gentleIBook = new NamespacedKey(SussyPlugin.sussyPlugin, "GentleIBook");

		ItemStack gentle = ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(Gentle.class), 1);
		ShapedRecipe gentleIRecipe = new ShapedRecipe(gentleIBook, gentle);

		gentleIRecipe.shape("EFE", "FSF", "EFE");
		gentleIRecipe.setIngredient('E', EssenceUtils.GetEssenceByKey("SHULKER_PROJ_ESSENCE").getRecipeChoice(2));
		gentleIRecipe.setIngredient('F', Material.FEATHER);
		ItemStack silkTouchBook = new ItemStack(Material.ENCHANTED_BOOK);

		EnchantmentStorageMeta meta = (EnchantmentStorageMeta)silkTouchBook.getItemMeta();
		meta.addStoredEnchant(Enchantment.SILK_TOUCH, 1, true);
		silkTouchBook.setItemMeta(meta);

		RecipeChoice.ExactChoice choice = new RecipeChoice.ExactChoice(silkTouchBook);

		gentleIRecipe.setIngredient('S', choice);
		Bukkit.addRecipe(gentleIRecipe);


		//Capturing Enchant Book recipe I

		NamespacedKey capturingEnchantIKey = new NamespacedKey(SussyPlugin.sussyPlugin, "CapturingIBook");
		ItemStack capturing = ItemUtils.CreateCustomEnchantBook(ItemUtils.GetCustomEnchantOfType(Capturing.class), 1);

		ShapedRecipe capturingIRecipe = new ShapedRecipe(capturingEnchantIKey, capturing);
		//Zombie Essence 3 - Z
		//Skeleton Essence 3 - S
		//Creeper Essence 3 - C
		//Bone Block - B
		//Spawner - P
		capturingIRecipe.shape("ZSC", "BPB", "CSZ");
		capturingIRecipe.setIngredient('Z', EssenceUtils.GetEssenceByKey("ZOMBIE_ESSENCE").getRecipeChoice(3));
		capturingIRecipe.setIngredient('S', EssenceUtils.GetEssenceByKey("SKELETON_ESSENCE").getRecipeChoice(3));
		capturingIRecipe.setIngredient('C', EssenceUtils.GetEssenceByKey("CREEPER_ESSENCE").getRecipeChoice(3));
		capturingIRecipe.setIngredient('B', Material.BONE_BLOCK);
		capturingIRecipe.setIngredient('P', new RecipeChoice.MaterialChoice(Material.SPAWNER));

		Bukkit.addRecipe(capturingIRecipe);
		//I can't think of a recipe for butterfingers. That will come later.
		//I also think that "Get Fucked" is a little OP currently, and we'll need some really difficult crafting recipe and to
		//tweak the implementation of it to make it less OP. Since currently it completely disables your opponent's ability to
		//use their inventory

		for (Essence essence : GetAllEssences())
		{
			for (int i = 2; i <= 10; i++)
			{
				NamespacedKey upkey = new NamespacedKey(SussyPlugin.sussyPlugin, "Essence" + essence.key + i);
				NamespacedKey downkey = new NamespacedKey(SussyPlugin.sussyPlugin, "Essence" + essence.key + i + "d");
				ShapelessRecipe up = new ShapelessRecipe(upkey, essence.CreateItem(1, i));

				up.addIngredient(4, essence.CreateItem(1, i - 1));
				Bukkit.addRecipe(up);

				ShapelessRecipe down = new ShapelessRecipe(downkey, essence.CreateItem(4, i - 1));
				RecipeChoice choiceDown = new RecipeChoice.ExactChoice(essence.CreateItem(1, i));
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
