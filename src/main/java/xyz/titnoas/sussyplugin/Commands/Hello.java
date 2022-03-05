package xyz.titnoas.sussyplugin.Commands;

import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.command.*;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.permissions.Permission;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataHolder;
import org.bukkit.persistence.PersistentDataType;
import xyz.titnoas.sussyplugin.SussyPlugin;

public class Hello implements CommandExecutor {

	private SussyPlugin plugin;

	public Hello(SussyPlugin pl){
		this.plugin = pl;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args){


		if(!(sender instanceof Player))
		{
			sender.sendMessage("Not a player");
			return true;
		}
		Player player = (Player)sender;

		if(!command.testPermission(sender)){
			sender.sendMessage("You do not have permission for this command.");
			return true;
		}

		player.sendMessage("SUS");

		ItemStack stack = new ItemStack(Material.DIAMOND_SWORD);
		stack.setAmount(1);

		ItemMeta meta = stack.getItemMeta();

		meta.addItemFlags(ItemFlag.HIDE_UNBREAKABLE);
		meta.setUnbreakable(true);
		PersistentDataContainer container = meta.getPersistentDataContainer();



		container.set(SussyPlugin.customItemKey, PersistentDataType.STRING, "SUSITEM");

		stack.setItemMeta(meta);

		int firstEmpty = player.getInventory().firstEmpty();
		if(firstEmpty == -1){
			sender.sendMessage("Inventory full!");
			return true;
		}

		player.getInventory().setItem(firstEmpty, stack);

		player.sendMessage("Added");
		//command.
		return true;
	}

}
