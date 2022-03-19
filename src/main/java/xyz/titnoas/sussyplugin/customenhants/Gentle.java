package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.spawners.CustomSpawner;
import xyz.titnoas.sussyplugin.spawners.CustomSpawnerMetadata;

import java.util.List;
import java.util.logging.Level;

public class Gentle extends CustomEnchant implements Listener {

	public Gentle(){
		this.allowedItems = List.of(Material.NETHERITE_PICKAXE, Material.DIAMOND_PICKAXE, Material.GOLDEN_PICKAXE, Material.STONE_PICKAXE, Material.WOODEN_PICKAXE);

		this.key = "GENTLE";
		this.localizedName = "Gentle";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
		this.maxLevel = 1;
	}

	@EventHandler
	public void onSpawnerBreak(BlockBreakEvent ev){
		if(ev.isCancelled()) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev cancelled");
			return;
		}
		if(ev.getBlock().getType() != Material.SPAWNER) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev type not spawner");
			return;
		}
		Player player = ev.getPlayer();

		ItemStack hand = player.getInventory().getItemInMainHand();

		if(!this.itemHasCustomEnchant(hand)) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev no custom enchant");
			return;
		}
		if(!(ev.getBlock().getState() instanceof CreatureSpawner spawner)) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev not instanceof spawner");
			return;
		}
		ev.setDropItems(false);

		ItemStack drop = CustomSpawner.toSpawnerItem(spawner);

		player.getWorld().dropItem(ev.getBlock().getLocation(), drop);
	}

	@EventHandler
	public void onPlace(BlockPlaceEvent ev){
		if(ev.isCancelled()) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev cancelled");
			return;
		}
		if(!ev.canBuild()) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev cant build");
			return;
		}
		if(ev.getItemInHand().getType() != Material.SPAWNER) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev not spawner");
			return;
		}
		if(!(ev.getBlock().getState() instanceof CreatureSpawner spawner)) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev not instanceof spawner");
			return;
		}
		CustomSpawnerMetadata meta = CustomSpawner.fromSpawnerItem(ev.getItemInHand());
		if(meta == null) {
			SussyPlugin.sussyPlugin.getLogger().log(Level.INFO, "Ev no spawner meta");
			return;
		}
		meta.applyTo(spawner);
	}
}
