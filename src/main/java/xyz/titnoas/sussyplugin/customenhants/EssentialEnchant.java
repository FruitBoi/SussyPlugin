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
		this.maxLevel = 100;
	}

	@EventHandler
	public void onMobKill(EntityDamageByEntityEvent ev) {
		if (ev.isCancelled())
			return;

		if (!(ev.getDamager() instanceof Player player))
			return;

		if (!this.itemHasCustomEnchant(player.getItemInHand())) {
			Bukkit.broadcastMessage("no enchanto itemo");
			return;
		}

		int level = this.GetItemEnchantLevel(player.getItemInHand());
		Bukkit.broadcastMessage("level: " + level);
		Entity damaged = ev.getEntity();

		EntityType type = damaged.getType();

		var essences = EssenceUtils.FindDroppableByType(type);

		if (essences.isEmpty()) {
			Bukkit.broadcastMessage("no essences found for type");
			return;
		}

		for (DroppableEssence essence : essences) {
			if (!(essence instanceof Essence e)) {
				Bukkit.broadcastMessage("skipped");
				continue;
			}


			float chance = level * 0.05f;
			if (!Utils.doChance(chance)) {
				continue;
			}

			Map<Integer, Double> mapping = new HashMap();

			for(int i = 0; i <= level; i++){
				double weight = 2 / (Math.pow(2, i));
				mapping.put(i, weight);
			}

			Integer weightedLevel = EssenceUtils.getWeightedRandom(mapping, Utils.getRandom());



			ItemStack essenceStack = e.CreateItem(next + 1, weightedLevel);
			Bukkit.broadcastMessage("dropping");
			damaged.getWorld().dropItem(damaged.getLocation(), essenceStack);
		}

	}

}
