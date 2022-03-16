package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.event.player.PlayerBucketFishEvent;
import org.bukkit.event.player.PlayerFishEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

import javax.swing.text.html.HTMLDocument;

public class EssenceListener implements Listener {

	private final SussyPlugin plugin;

	public EssenceListener(SussyPlugin plugin) {
		this.plugin = plugin;
	}

}
