package xyz.titnoas.sussyplugin;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.titnoas.sussyplugin.customenhants.*;
import xyz.titnoas.sussyplugin.utilshit.Glow;
import xyz.titnoas.sussyplugin.utilshit.RomanNumber;


import java.awt.*;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ItemUtils {

	public static List<CustomEnchant> customEnchants = new ArrayList<CustomEnchant>();

	static void Init() {
		customEnchants.clear();

		customEnchants.add(new TestEnchant());
		customEnchants.add(new UpEnchant());
		customEnchants.add(new SplashysIdea());
		customEnchants.add(new LinearUpEnchant());
		customEnchants.add(new ButterfingersEnchant());
		customEnchants.add(new Archery());
		customEnchants.add(new Soulbound());
		customEnchants.add(new LastStand());
		customEnchants.add(new DanceParty());
	}

	public static List<CustomEnchant> GetItemCustomEnchants(ItemStack item) {

		List<CustomEnchant> shit = new ArrayList<CustomEnchant>();

		for (CustomEnchant enchant : customEnchants) {

			if (enchant.itemHasCustomEnchantIgnoreType(item))
				shit.add(enchant);

		}
		return shit;
	}

	public static void RemoveAllCustomEnchantsFromItem(ItemStack item, boolean overrideNoGrindstone) {

		List<CustomEnchant> toRemove = GetItemCustomEnchants(item);
		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();

		for (CustomEnchant remove : toRemove) {

			NamespacedKey key = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYITEM" + remove.key);
			if (container.has(key) && (overrideNoGrindstone || remove.allowGrindstone)) {
				container.remove(key);
				meta.setLore(RemoveStringFromLore(meta.getLore(), remove.localizedName));
			}
		}
		item.setItemMeta(meta);
	}

	public static void RemoveEnchantFromItem(ItemStack item, CustomEnchant enchant) {

		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer container = meta.getPersistentDataContainer();

		NamespacedKey key = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYITEM" + enchant.key);
		if (container.has(key)) {
			container.remove(key);
			meta.setLore(RemoveStringFromLore(meta.getLore(), enchant.localizedName));
		}

		item.setItemMeta(meta);
	}

	public static boolean ItemHasAnyCustomEnchantsOrEnchants(ItemStack item) {

		List<CustomEnchant> applied = GetItemCustomEnchants(item);

		List<Enchantment> notGlow = new ArrayList<>();
		if(!item.getEnchantments().isEmpty()) {
			for (Enchantment ench : item.getEnchantments().keySet()) {
				if (!ench.getKey().toString().equals("sussyplugin:glowenchant")) {
					notGlow.add(ench);
				}
			}
		}

		return !applied.isEmpty() || !notGlow.isEmpty();
	}

	public static void UpdateEnchantLevel(ItemStack item, CustomEnchant enchant, int level) {
		RemoveEnchantFromItem(item, enchant);
		ApplyCustomEnchantToItem(item, enchant, level, true);
	}

	public static List<String> RemoveStringFromLore(List<String> lore, String remove) {

		List<String> newLore = new ArrayList<>();

		for (int i = 0; i < lore.size(); i++) {
			if (!lore.get(i).contains(remove))
				newLore.add(lore.get(i));
		}

		return newLore;
	}

	public static void RemoveGlow(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();

		for (Enchantment ench : meta.getEnchants().keySet())
			if (ench.getKey().getKey().equals(Glow.glowKey.getKey())) {
				meta.removeEnchant(ench);
			}
		stack.setItemMeta(meta);
	}

	public static void ApplyCustomEnchantToItem(ItemStack item, CustomEnchant enchant, int level, boolean setLore) {

		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

		NamespacedKey key = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYITEM" + enchant.key);

		persistentDataContainer.set(key, PersistentDataType.INTEGER, level);

		if (setLore) {
			List<String> lore = null;

			if (meta.hasLore())
				lore = meta.getLore();
			else
				lore = new ArrayList<>();

			List<String> newLore = new ArrayList<>();

			for (int i = 0; i < lore.size(); i++) {
				if (!lore.get(i).contains(enchant.localizedName))
					newLore.add(lore.get(i));
			}

			String roman = String.valueOf(level);

			if (level <= 100)
				roman = RomanNumber.toRoman(level);

			newLore.add(ChatColor.RESET + "" + ChatColor.GRAY + enchant.localizedName + " " + roman);

			meta.setLore(newLore);
		}
		item.setItemMeta(meta);
		enchant.onItemEnchanted(item);
	}

	public static ItemStack CreateCustomEnchantBook(CustomEnchant enc, int level) {

		ItemStack item = new ItemStack(Material.ENCHANTED_BOOK);
		item.setAmount(1);

		ItemMeta meta = item.getItemMeta();
		PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

		NamespacedKey key = new NamespacedKey(SussyPlugin.sussyPlugin, "SUSSYITEM" + enc.key);

		persistentDataContainer.set(key, PersistentDataType.INTEGER, level);

		String roman = String.valueOf(level);
		if (level <= 100)
			roman = RomanNumber.toRoman(level);


		meta.setLore(List.of(ChatColor.RESET + "" + ChatColor.GRAY + enc.localizedName + " " + roman));

		item.setItemMeta(meta);

		return item;
	}

	public static CustomEnchant GetEnchantByKey(String key) {
		for (CustomEnchant enchant : customEnchants) {
			if (enchant.key.equals(key))
				return enchant;
		}
		return null;
	}

	public static void AddEnchantGlow(ItemStack stack) {
		ItemMeta meta = stack.getItemMeta();

		boolean f = false;
		for (Enchantment ench : meta.getEnchants().keySet())
			if (ench instanceof Glow)
				f = true;

		if (f)
			return;

		Glow glow = new Glow();
		meta.addEnchant(glow, 1, true);
		stack.setItemMeta(meta);
	}

}
