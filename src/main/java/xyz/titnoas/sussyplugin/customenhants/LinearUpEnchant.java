package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.util.Vector;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.List;

public class LinearUpEnchant extends CustomEnchant implements Listener {

	public LinearUpEnchant(){
		this.allowedItems = List.of(Material.NETHERITE_SWORD, Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD);
		this.key = "LUP";
		this.localizedName = "Beyond";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@EventHandler()
	public void onDamaged(EntityDamageByEntityEvent ev){
		if(!(ev.getDamager() instanceof Player)) {
			return;
		}


		Player player = (Player)ev.getDamager();
		Entity damagee = ev.getEntity();

		ItemStack hand = player.getInventory().getItemInMainHand();

		if(hand.getType() == Material.AIR)
			return;

		if(!this.itemHasCustomEnchant(hand))
			return;

		int level = GetItemEnchantLevel(hand);


		float distance = (float)player.getLocation().distance(damagee.getLocation());

		distance = (1f / distance) * 0.3f;

		double mult = level;

		Vector vel = new Vector(0,distance * mult,0);

		Bukkit.getScheduler().runTaskLater(SussyPlugin.sussyPlugin, new Runnable() {
			@Override
			public void run(){
				damagee.setVelocity(damagee.getVelocity().add(vel));
			}
		}, 1);
	}
}