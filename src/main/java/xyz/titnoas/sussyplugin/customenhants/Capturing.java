package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Damageable;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.utilshit.Utils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Capturing extends CustomEnchant implements Listener {

	private static final Map<EntityType, Material> allowedDroppableTypes = Map.
			of(		EntityType.CREEPER, Material.CREEPER_SPAWN_EGG,
					EntityType.TURTLE, Material.TURTLE_SPAWN_EGG);

	public Capturing(){
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

		this.key = "CAPTURING";
		this.localizedName = "Capturing";

		this.maxLevel = 5;

		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent ev){
		if(!(ev.getDamager() instanceof Player player))
			return;

		ItemStack inHand = player.getInventory().getItemInMainHand();

		if(!this.itemHasCustomEnchant(inHand))
			return;

		if(!allowedDroppableTypes.containsKey(ev.getEntity().getType()))
			return;

		if(!(ev.getEntity() instanceof LivingEntity entity))
			return;

		if(entity.getHealth() - ev.getFinalDamage() > 0f)
			return;

		int level = this.GetItemEnchantLevel(inHand);

		double chance = level * (0.1f / 100f);

		if(!Utils.doChance((float)chance))
			return;

		ItemStack stack = new ItemStack(allowedDroppableTypes.get(ev.getEntity().getType()));

		ev.getEntity().getWorld().dropItem(ev.getEntity().getLocation(), stack);

	}
}
