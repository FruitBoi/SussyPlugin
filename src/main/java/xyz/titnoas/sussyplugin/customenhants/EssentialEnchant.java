package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.obtaining.essences.DroppableEssence;
import xyz.titnoas.sussyplugin.obtaining.essences.Essence;
import xyz.titnoas.sussyplugin.obtaining.essences.EssenceUtils;
import xyz.titnoas.sussyplugin.utilshit.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class EssentialEnchant extends CustomEnchant implements Listener {

	public EssentialEnchant() {
		this.allowedItems = List.of(Material.NETHERITE_SWORD,
				Material.DIAMOND_SWORD,
				Material.GOLDEN_SWORD,
				Material.IRON_SWORD,
				Material.STONE_SWORD,
				Material.WOODEN_SWORD,
				Material.NETHERITE_AXE,
				Material.DIAMOND_AXE,
				Material.GOLDEN_AXE,
				Material.IRON_AXE,
				Material.STONE_AXE,
				Material.WOODEN_AXE);


		this.key = "ESSENTIAL";
		this.localizedName = "Essential";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
		this.maxLevel = 3;
	}

	@EventHandler
	public void onMobKill(EntityDamageByEntityEvent ev) {
		if (ev.isCancelled())
			return;

		if (!(ev.getDamager() instanceof Player player))
			return;

		if (!this.itemHasCustomEnchant(player.getItemInHand())) {
			return;
		}

		int level = this.GetItemEnchantLevel(player.getItemInHand());
		Entity damaged = ev.getEntity();

		EntityType type = damaged.getType();

		var essences = EssenceUtils.FindDroppableByEntity(ev.getEntity());

		if (essences.isEmpty()) {
			return;
		}

		for (DroppableEssence essence : essences) {
			if (!(essence instanceof Essence e)) {
				continue;
			}


			float chance = level * 0.05f * essence.chanceMultiplier();
			if (!Utils.doChance(chance)) {
				continue;
			}

			Map<Integer, Double> mapping = new HashMap();

			for(int i = 1; i <= level; i++){
				double weight = 2 / (Math.pow(2, i));
				mapping.put(i, weight);
			}

			Integer weightedLevel = EssenceUtils.getWeightedRandom(mapping, Utils.getRandom());

			Integer amount = 1;

			Map<Integer, Double> amountMapping = new HashMap<>();

			if(level > 1){
				for(int i = 1; i <= weightedLevel; i++){
					double weight = 20 / (Math.pow(2, i) * Math.pow(3, weightedLevel));
					amountMapping.put(i, weight);
				}

				amount = EssenceUtils.getWeightedRandom(amountMapping, Utils.getRandom());
			}

			ItemStack essenceStack = e.CreateItem(amount, weightedLevel);
			damaged.getWorld().dropItem(damaged.getLocation(), essenceStack);
		}

	}

}
