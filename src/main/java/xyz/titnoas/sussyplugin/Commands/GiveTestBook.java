package xyz.titnoas.sussyplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.customenhants.CustomEnchant;

public class GiveTestBook implements CommandExecutor {

	private SussyPlugin plugin;

	public GiveTestBook(SussyPlugin pl){
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

		if(args.length != 2)
		{
			String error = "/testbook (key) (enchant level)  Available Keys:";

			for(CustomEnchant ench : ItemUtils.customEnchants){
				error += " " + ench.key;
			}
			player.sendMessage(error);
			return true;
		}



		String key = args[0];
		String level = args[1];

		int levelInt = 0;

		try{
			levelInt = Integer.parseInt(level);

		}catch(NumberFormatException ex){
			player.sendMessage("Unable to parse level");
			return true;
		}

		CustomEnchant enchant = ItemUtils.GetEnchantByKey(key);

		if(enchant == null){
			String error = "Custom enchant not found: ";

			for(CustomEnchant ench : ItemUtils.customEnchants){
				error += " " + ench.key;
			}
			player.sendMessage(error);
			return true;
		}



		ItemStack stack = ItemUtils.CreateCustomEnchantBook(enchant, levelInt);

		int firstEmpty = player.getInventory().firstEmpty();
		if(firstEmpty == -1){
			sender.sendMessage("Inventory full!");
			return true;
		}

		player.getInventory().setItem(firstEmpty, stack);

		player.sendMessage(ChatColor.AQUA + "ok enjoy mfer");
		//command.
		return true;
	}

}
