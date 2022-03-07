package xyz.titnoas.sussyplugin.Commands;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.Potion;
import org.bukkit.potion.PotionData;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.jetbrains.annotations.NotNull;
import xyz.titnoas.sussyplugin.SussyPlugin;
import xyz.titnoas.sussyplugin.utilshit.CustomEffectType;

public class ApplyEffect implements CommandExecutor {

    private SussyPlugin plugin;

    public ApplyEffect(SussyPlugin pl){
        this.plugin = pl;
    }

    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String s, @NotNull String[] strings) {
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

        PotionEffectType custom1 = new CustomEffectType();
        ItemStack potion = new ItemStack(Material.POTION);

        PotionMeta p = (PotionMeta) potion.getItemMeta();
        p.setMainEffect(custom1);
        potion.setItemMeta(p);

        int firstEmpty = player.getInventory().firstEmpty();
        if(firstEmpty == -1){
            sender.sendMessage("Inventory full!");
            return true;
        }

        player.getInventory().setItem(firstEmpty, potion);

        player.sendMessage(ChatColor.AQUA + "fuck bukkit api");
        return true;
    }
}
