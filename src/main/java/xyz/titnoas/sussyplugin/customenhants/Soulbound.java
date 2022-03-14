package xyz.titnoas.sussyplugin.customenhants;

import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.Iterator;
import java.util.List;

public class Soulbound extends CustomEnchant implements Listener {

	public Soulbound() {
		this.allowedItems = List.of();
		this.key = "SOULBOUND";
		this.localizedName = "Soulbound";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
		this.allowBookCombine = false;
		this.maxLevel = 2;
	}

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent ev) {

		if (ev.getKeepInventory())
			return;


		for (Iterator<ItemStack> iterator = ev.getDrops().iterator(); iterator.hasNext(); ) {
			ItemStack drop = iterator.next();

			if (!this.itemHasCustomEnchant(drop))
				continue;

			int level = this.GetItemEnchantLevel(drop);

			if (level - 1 < 0)
				continue;

			if (level - 1 <= 0) {

				ItemUtils.RemoveEnchantFromItem(drop, this);

				if (!ItemUtils.ItemHasAnyCustomEnchantsOrEnchants(drop))
					ItemUtils.RemoveGlow(drop);


				drop = new ItemStack(drop);
			} else {
				ItemUtils.UpdateEnchantLevel(drop, this, level - 1);
			}

			iterator.remove();
			ev.getItemsToKeep().add(drop);
		}
	}
}
