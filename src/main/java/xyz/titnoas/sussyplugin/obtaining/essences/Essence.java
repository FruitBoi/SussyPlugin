package xyz.titnoas.sussyplugin.obtaining.essences;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.RecipeChoice;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.customenhants.CustomEnchant;
import xyz.titnoas.sussyplugin.utilshit.RomanNumber;

import java.util.List;

public class Essence extends CustomEnchant {

	protected Color color;
	protected Material itemType;
	protected boolean setLore;
	protected boolean setGlow;
	protected boolean displayName;

	public Essence(Color color, String key, String name, boolean glow, boolean lore, boolean displayName){
		this.key = key;

		this.localizedName = name;
		this.allowGrindstone = false;
		this.allowBookCombine = false;
		this.maxLevel = 9999;

		this.itemType = Material.FIREWORK_STAR;
		this.allowedItems = List.of(itemType);
		this.color = color;
		this.setLore = lore;
		this.setGlow = glow;
		this.displayName = displayName;

	}

	@Override
	public void onItemEnchanted(ItemStack item){

		if(setGlow)
			ItemUtils.AddEnchantGlow(item);
	}

	public RecipeChoice getRecipeChoice(int level){
		return new RecipeChoice.ExactChoice(CreateItem(1, level));
	}

	public ItemStack CreateItem(int amount, int level){

		if(level < 1)
			level = 1;

		ItemStack stack = new ItemStack(itemType);
		stack.setAmount(amount);

		ItemUtils.ApplyCustomEnchantToItem(stack, this, level, setLore);

		if(displayName) {
			ItemMeta meta = stack.getItemMeta();

			String levelNum = "";
			if(level > 100)
				levelNum = Integer.toString(level);
			else
				levelNum = RomanNumber.toRoman(level);


			meta.setDisplayName(ChatColor.RESET + "" + ChatColor.of(new java.awt.Color(color.asRGB())) + "" + ChatColor.BOLD + localizedName + " " + levelNum);
			stack.setItemMeta(meta);
		}

		ItemMeta meta = stack.getItemMeta();
		meta.setLore(List.of());
		stack.setItemMeta(meta);
		return stack;
	}
}
