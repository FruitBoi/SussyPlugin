package xyz.titnoas.sussyplugin.listener;

import com.destroystokyo.paper.event.inventory.PrepareGrindstoneEvent;
import com.destroystokyo.paper.event.server.PaperServerListPingEvent;
import com.destroystokyo.paper.loottable.LootableInventoryReplenishEvent;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.event.world.LootGenerateEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.loot.LootTable;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.util.Vector;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.customenhants.CustomEnchant;
import xyz.titnoas.sussyplugin.customenhants.DanceParty;
import xyz.titnoas.sussyplugin.customenhants.EssentialEnchant;
import xyz.titnoas.sussyplugin.utilshit.Utils;

import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;

public class ItemsListener implements Listener {

	private SussyPlugin plugin;

	public static int troll = -1;

	public ItemsListener(SussyPlugin plugin) {
		this.plugin = plugin;
	}

	@EventHandler
	public void onAnvilPrepare(PrepareAnvilEvent ev){

		AnvilInventory inv = ev.getInventory();

		if(inv.getFirstItem() == null || inv.getSecondItem() == null)
			return;

		List<CustomEnchant> primaryEnc = ItemUtils.GetItemCustomEnchants(inv.getFirstItem());
		List<CustomEnchant> secondaryEnc = ItemUtils.GetItemCustomEnchants(inv.getSecondItem());

		HashMap<CustomEnchant, Integer> toApply = new HashMap<>();

		for(CustomEnchant onSecondary : secondaryEnc){

			if(!primaryEnc.contains(onSecondary) && (onSecondary.allowedItems == null ||
					onSecondary.allowedItems.size() == 0 ||
					onSecondary.allowedItems.contains(inv.getFirstItem().getType()))) {
				toApply.put(onSecondary, onSecondary.GetItemEnchantLevel(inv.getSecondItem()));
				continue;
			}

			if(primaryEnc.contains(onSecondary) && onSecondary.allowBookCombine &&
			onSecondary.GetItemEnchantLevel(inv.getFirstItem()) == onSecondary.GetItemEnchantLevel(inv.getSecondItem())){

				if(onSecondary.maxLevel < onSecondary.GetItemEnchantLevel(inv.getFirstItem()) + 1)
					continue;

				toApply.put(onSecondary, onSecondary.GetItemEnchantLevel(inv.getFirstItem()) + 1);
				continue;
			}
		}

		if(toApply.isEmpty())
			return;


		ItemStack output;

		if(ev.getResult() == null)
			output = new ItemStack(inv.getFirstItem());
		else
			output = new ItemStack(ev.getResult());

		toApply.forEach((CustomEnchant enc, Integer level) ->
				ItemUtils.ApplyCustomEnchantToItem(output, enc, level, true));

		ev.setResult(output);
	}
	@EventHandler
	public void onGrindstone(PrepareGrindstoneEvent ev){

		ItemStack result = ev.getResult();

		if(result == null)
			return;

		if(result.getType() == Material.AIR)
			return;

		if(ItemUtils.GetItemCustomEnchants(result).size() <= 0)
			return;

		ItemUtils.RemoveAllCustomEnchantsFromItem(result, false);
	}

	@EventHandler
	public void onLoot(LootGenerateEvent ev){

		if(Utils.doChance(10/100f)) {

			//Could not think of a better way to initally hand out essential books. I made the chance not
			//too grindy but it will still take someone a bit to find it. Hoping to make the book itself quite valuable.

			List<ItemStack> loot = ev.getLoot();

			var essential = ItemUtils.GetCustomEnchantOfType(EssentialEnchant.class);

			loot.add(ItemUtils.CreateCustomEnchantBook(essential, 1));
		}

		if(Utils.doChance(0.1f/100f))
		{
			//:Catjam:

			List<ItemStack> loot = ev.getLoot();

			var danceParty = ItemUtils.GetCustomEnchantOfType(DanceParty.class);

			int rand = Utils.getRandom().nextInt(1, 11);

			loot.add(ItemUtils.CreateCustomEnchantBook(danceParty, rand));
		}
	}

	@EventHandler
	public void onEntityDamaged(EntityDamageByEntityEvent ev) {
		if (!(ev.getDamager() instanceof Player)) {
			return;
		}

		Player player = (Player) ev.getDamager();
		Entity damaged = ev.getEntity();
		ItemStack weapon = player.getInventory().getItemInMainHand();

		if (weapon.getType() == Material.AIR) {
			return;
		}


		ItemMeta meta = weapon.getItemMeta();

		PersistentDataContainer persistentDataContainer = meta.getPersistentDataContainer();

		if (!persistentDataContainer.has(SussyPlugin.customItemKey, PersistentDataType.STRING)) {
			return;
		}
		String data = persistentDataContainer.get(SussyPlugin.customItemKey, PersistentDataType.STRING);


		if(data != null && data.equals("SUSITEM")) {

			Vector damagedLoc = damaged.getLocation().toVector();
			Vector playerLoc = player.getLocation().toVector();

			Vector dir = damagedLoc.subtract(playerLoc);

			float pitch = (float) Math.cos(Math.toRadians(player.getLocation().getPitch() + 90f)) * -1f;

			dir.setY(pitch);

			dir = dir.multiply(dir.length());

			damaged.setVelocity(damaged.getVelocity().add(dir));
		}
	}
}
