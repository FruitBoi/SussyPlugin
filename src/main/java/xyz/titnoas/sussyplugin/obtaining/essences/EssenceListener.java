package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

import javax.swing.text.html.HTMLDocument;

public class EssenceListener implements Listener {

	private final SussyPlugin plugin;

	public EssenceListener(SussyPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent ev) {
		/*
		int tocraft = 64;

		Essence type = null;

		int level = 0;

		for(ItemStack stack : ev.getInventory().getMatrix()){

			if(stack == null)
				return;

			if(!EssenceUtils.IsEssence(stack))
				return;

			if(type == null)
				type = EssenceUtils.GetEssenceTypeFromItem(stack);

			if(type != EssenceUtils.GetEssenceTypeFromItem(stack))
				return;

			if(level == 0)
				level = type.GetItemEnchantLevel(stack);

			if(type.GetItemEnchantLevel(stack) != level)
				return;

			if(stack.getAmount() < tocraft)
				tocraft = stack.getAmount();

		}
		if(ev.getInventory().getMatrix().length != 9)
			return;


		*/
	}
}
