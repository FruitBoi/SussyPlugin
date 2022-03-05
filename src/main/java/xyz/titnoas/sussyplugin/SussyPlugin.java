package xyz.titnoas.sussyplugin;

import com.comphenix.protocol.ProtocolLibrary;
import com.comphenix.protocol.ProtocolManager;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import xyz.titnoas.sussyplugin.Commands.GiveTestBook;
import xyz.titnoas.sussyplugin.Commands.Hello;
import xyz.titnoas.sussyplugin.listener.ItemsListener;
import xyz.titnoas.sussyplugin.utilshit.Glow;

import java.lang.reflect.Field;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SussyPlugin extends JavaPlugin {


	public static NamespacedKey customItemKey;

	public static SussyPlugin sussyPlugin;

	@Override
	public void onEnable(){
		sussyPlugin = this;
		customItemKey = new NamespacedKey(this, "CUSTOMITEM");
		this.getLogger().log(Level.INFO, "Enabling SussyPlugin");
		this.getCommand("sus").setExecutor(new Hello(this));
		this.getCommand("testbook").setExecutor(new GiveTestBook(this));
		if(Glow.glowKey == null)
			Glow.glowKey = new NamespacedKey(SussyPlugin.sussyPlugin, "GlowEnchant");
		registerGlow();
		PluginManager manager = Bukkit.getPluginManager();

		manager.registerEvents(new ItemsListener(this), this);
		ItemUtils.Init();
		ProtocolManager protocolManager = ProtocolLibrary.getProtocolManager();

	}

	@Override
	public void onDisable(){

	}

	public void registerGlow() {
		try {
			Field f = Enchantment.class.getDeclaredField("acceptingNew");
			f.setAccessible(true);
			f.set(null, true);
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		try {
			Glow glow = new Glow();
			Enchantment.registerEnchantment(glow);
		}
		catch (IllegalArgumentException e){
		}
		catch(Exception e){
			e.printStackTrace();
		}
	}

}
