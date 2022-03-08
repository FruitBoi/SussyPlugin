package xyz.titnoas.sussyplugin.customenhants;

import io.papermc.paper.datapack.DatapackManager;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.List;

public class TestEnchant extends CustomEnchant implements Listener {

	public TestEnchant(){
		this.allowedItems = List.of(Material.NETHERITE_SWORD, Material.DIAMOND_SWORD, Material.GOLDEN_SWORD, Material.IRON_SWORD, Material.STONE_SWORD, Material.WOODEN_SWORD);
		this.key = "TEST";
		this.localizedName = "Testing Enchant";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}


	@EventHandler
	public void onPlayerInteract(PlayerInteractEvent ev) {

		if(!this.itemHasCustomEnchant(ev.getItem()))
			return;

		ItemStack spawner = new ItemStack(Material.SPAWNER);



		Player p = ev.getPlayer();

		p.sendMessage("lmao");
	}
}
