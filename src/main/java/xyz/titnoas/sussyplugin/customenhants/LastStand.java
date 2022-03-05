package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.*;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityResurrectEvent;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class LastStand extends CustomEnchant implements Listener {

	private static Random random = new Random();


	public LastStand(){
		this.allowedItems = List.of(Material.LEATHER_HELMET, Material.LEATHER_CHESTPLATE, Material.LEATHER_LEGGINGS, Material.LEATHER_BOOTS,
				Material.CHAINMAIL_HELMET, Material.CHAINMAIL_CHESTPLATE, Material.CHAINMAIL_LEGGINGS, Material.CHAINMAIL_BOOTS,
				Material.IRON_HELMET, Material.IRON_CHESTPLATE, Material.IRON_LEGGINGS, Material.IRON_BOOTS,
				Material.GOLDEN_HELMET, Material.GOLDEN_CHESTPLATE, Material.GOLDEN_LEGGINGS, Material.GOLDEN_BOOTS,
				Material.DIAMOND_HELMET, Material.DIAMOND_CHESTPLATE, Material.DIAMOND_LEGGINGS, Material.DIAMOND_BOOTS,
				Material.NETHERITE_HELMET, Material.NETHERITE_CHESTPLATE, Material.NETHERITE_LEGGINGS, Material.NETHERITE_BOOTS);
		this.key = "LASTSTAND";
		this.localizedName = "Last Stand";
		this.maxLevel = 1;
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@EventHandler
	public void onTotem(EntityResurrectEvent ev){
		ev.setCancelled(true);
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ev){

		Player player = ev.getPlayer();

		PlayerInventory playerInv = player.getInventory();

		ItemStack[] armor = playerInv.getArmorContents();

		if(armor == null)
			return;

		List<ItemStack> lastStandArmor = new ArrayList<>();

		for(ItemStack stack : armor){

			if(stack == null || stack.getType() == Material.AIR)
				continue;

			if(this.itemHasCustomEnchant(stack))
				lastStandArmor.add(stack);
		}

		if(lastStandArmor.isEmpty())
			return;

		float chance = random.nextFloat();

		int index = random.nextInt(lastStandArmor.size());

		float attackDropChance = (25f / 100f) * (lastStandArmor.size());

		if(!(chance <= attackDropChance))
			return;

		if(ev.isCancelled())
			return;

		ev.setCancelled(true);

		player.setHealth(5);
		PotionEffect eff = new PotionEffect(PotionEffectType.INCREASE_DAMAGE, 60, 2, false, false, true);
		PotionEffect regen = new PotionEffect(PotionEffectType.REGENERATION, 80, 3, false, false, true);
		player.addPotionEffect(eff);
		player.addPotionEffect(regen);

		player.sendMessage(ChatColor.RESET + "" + ChatColor.RED + ":quitincredible:");

		ItemStack remove = lastStandArmor.get(index);

		player.playEffect(EntityEffect.TOTEM_RESURRECT);


		ItemUtils.RemoveEnchantFromItem(remove, this);
		if(!ItemUtils.ItemHasAnyCustomEnchantsOrEnchants(remove))
			ItemUtils.RemoveGlow(remove);

	}
}
