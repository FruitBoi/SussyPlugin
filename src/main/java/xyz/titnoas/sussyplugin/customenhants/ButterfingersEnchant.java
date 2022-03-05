package xyz.titnoas.sussyplugin.customenhants;


import net.kyori.adventure.text.*;
import net.kyori.adventure.text.format.NamedTextColor;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.List;
import java.util.Random;

public class ButterfingersEnchant extends CustomEnchant implements Listener {

	private static Random random = new Random();

	public ButterfingersEnchant(){
		this.allowedItems = List.of(Material.NETHERITE_SWORD, Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD);
		this.key = "BUTTERFINGERS";
		this.localizedName = "Butterfingers";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@EventHandler
	public void onPlayerAttack(EntityDamageByEntityEvent ev){

		if(!(ev.getDamager() instanceof Player))
			return;

		if(!(ev.getEntity() instanceof Player))
			return;

		Player attacker = (Player)ev.getDamager();

		var attackerInventory = attacker.getInventory();

		if(!this.itemHasCustomEnchant(attackerInventory.getItemInMainHand()))
			return;

		int level = this.GetItemEnchantLevel(attackerInventory.getItemInMainHand());

		float chance = random.nextFloat();

		float attackDropChance = (1f / 100f) * level;

		if(!(chance <= attackDropChance))
			return;

		//maybe add curse prot check here?

		Player target = (Player)ev.getEntity();
		Inventory targetInv = target.getInventory();


		if(targetInv.isEmpty())
			return;

		ItemStack[] items = targetInv.getContents();

		if(items == null)
			return;

		int toDrop;

		do{
			toDrop = random.nextInt(items.length);
		}while(items[toDrop] == null || items[toDrop].getType() == Material.AIR);

		ItemStack item = items[toDrop];

		targetInv.clear(toDrop);
		target.getWorld().dropItem(target.getLocation(), item);


		TextComponent comp = Component.text("Oops, it looks like ", NamedTextColor.AQUA)
				.append(item.displayName()).hoverEvent(item.asHoverEvent())
				.append(Component.text(" slipped through your fingers!", NamedTextColor.AQUA));

		target.sendMessage(comp);
	}


}
