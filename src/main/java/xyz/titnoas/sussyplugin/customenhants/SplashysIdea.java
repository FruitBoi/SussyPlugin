package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.PlayerChangedMainHandEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.HashMap;
import java.util.List;

public class SplashysIdea extends CustomEnchant implements Listener {


	public HashMap<Player, Integer> playerHandMapping = new HashMap<>();

	public SplashysIdea() {
		this.allowedItems = List.of(Material.NETHERITE_SWORD, Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD);
		this.key = "SPLASHYLMAO";
		this.localizedName = "Get fucked";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent ev) {

		if (!(ev.getDamager() instanceof Player damager) || !(ev.getEntity() instanceof Player damagee))
			return;

		ItemStack mainHand = damager.getInventory().getItemInMainHand();

		if (mainHand.getType() == Material.AIR)
			return;

		if (!this.itemHasCustomEnchant(mainHand))
			return;

		if (playerHandMapping.containsKey(damagee))
			return;

		playerHandMapping.put(damagee, damagee.getInventory().getHeldItemSlot());

		Bukkit.getScheduler().runTaskLater(SussyPlugin.sussyPlugin, () -> playerHandMapping.remove(damagee), 400);
	}

	@EventHandler
	public void onInvClick(InventoryClickEvent ev) {

		Player p = null;

		for (Player player : playerHandMapping.keySet()) {
			if (player.getUniqueId() == ev.getWhoClicked().getUniqueId()) {
				p = player;
				break;
			}
		}
		if (p == null)
			return;

		ev.setCancelled(true);

	}

	@EventHandler
	public void onInvOpen(InventoryOpenEvent open) {

		Player p = null;

		for (Player player : playerHandMapping.keySet()) {
			if (player.getUniqueId() == open.getPlayer().getUniqueId()) {
				p = player;
				break;
			}
		}
		if (p == null)
			return;

		open.setCancelled(true);
		open.getInventory().close();
		p.updateInventory();
	}

	@EventHandler
	public void onMainHandChanged(PlayerChangedMainHandEvent ev) {

		Player p = null;

		for (Player player : playerHandMapping.keySet()) {
			if (player.getUniqueId() == ev.getPlayer().getUniqueId()) {
				p = player;
				break;
			}
		}
		if (p == null)
			return;

		ev.getPlayer().getInventory().setHeldItemSlot(playerHandMapping.get(p));
		ev.getPlayer().updateInventory();
	}
}
