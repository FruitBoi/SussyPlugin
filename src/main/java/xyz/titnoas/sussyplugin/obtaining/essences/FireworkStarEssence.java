package xyz.titnoas.sussyplugin.obtaining.essences;

import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.Material;
import org.bukkit.entity.Firework;
import org.bukkit.event.EventHandler;
import org.bukkit.event.inventory.PrepareItemCraftEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.inventory.ShapelessRecipe;
import org.bukkit.inventory.meta.FireworkEffectMeta;
import org.bukkit.inventory.meta.ItemMeta;

public class FireworkStarEssence extends Essence {

	public FireworkStarEssence(Color color, String key, String name, boolean glow, boolean lore, boolean displayName){
		super(color, key, name, glow, lore, displayName);
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

		if(ev.getInventory().getMatrix() == null)
			return;

		for(ItemStack item : ev.getInventory().getMatrix()){
			if(EssenceUtils.IsItemEssence(item))
				ev.getInventory().setResult(new ItemStack(Material.AIR));
		}
	}
}
