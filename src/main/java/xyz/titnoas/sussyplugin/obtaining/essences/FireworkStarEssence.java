package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.*;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;
import xyz.titnoas.sussyplugin.ItemUtils;
import xyz.titnoas.sussyplugin.SussyPlugin;

public class FireworkStarEssence extends Essence implements Listener {

	public FireworkStarEssence(Color color, String key, String name, boolean glow, boolean lore, boolean showDisplayName){
		super(color, key, name, glow, lore, showDisplayName);

		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	@Override
	public void onItemEnchanted(ItemStack stack){
		if(stack.getType() != Material.FIREWORK_STAR)
			return;

		ItemMeta meta = stack.getItemMeta();

		FireworkEffectMeta effectMeta = (FireworkEffectMeta)meta;

		FireworkEffect effect = FireworkEffect.builder().withColor(this.color).build();

		effectMeta.setEffect(effect);

		stack.setItemMeta(effectMeta);
	}

	@EventHandler
	public void onCraft(PrepareItemCraftEvent ev){

		if(ev.getRecipe() != null && ev.getRecipe() instanceof Keyed key && key.getKey().namespace().contains("sussyplugin")){
			return;
		}

		for(ItemStack item : ev.getInventory().getMatrix())
		{
			if(!ItemUtils.ItemHasEnchantOfType(Essence.class, item))
				continue;
				ev.getInventory().setResult(new ItemStack(Material.AIR));

		}
	}
}
