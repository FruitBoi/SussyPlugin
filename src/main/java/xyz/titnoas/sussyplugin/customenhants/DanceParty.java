package xyz.titnoas.sussyplugin.customenhants;

import com.xxmicloxx.NoteBlockAPI.model.RepeatMode;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.songplayer.EntitySongPlayer;
import com.xxmicloxx.NoteBlockAPI.utils.NBSDecoder;
import net.kyori.adventure.sound.SoundStop;
import org.bukkit.Bukkit;
import org.bukkit.Sound;
import org.bukkit.SoundCategory;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityPickupItemEvent;
import org.bukkit.event.entity.ItemSpawnEvent;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.inventory.ItemStack;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class DanceParty extends CustomEnchant implements Listener {

	private static ArrayList<EntitySongPlayer> entitySongPlayers  = new ArrayList<>();

	public DanceParty(){
		this.allowedItems = List.of();
		this.key = "DANCEPARTY";
		this.localizedName = ":Catjam:";
		Bukkit.getPluginManager().registerEvents(this, SussyPlugin.sussyPlugin);
	}

	private static boolean ContainsUUID(Set<UUID> list, Player player){
		for(UUID uuid : list)
		{
			if(player.getUniqueId() == uuid)
				return true;
		}
		return false;
	}

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent ev){
		for(EntitySongPlayer songPlayer : entitySongPlayers){

			if(ContainsUUID(songPlayer.getPlayerUUIDs(), ev.getPlayer()))
				continue;

			songPlayer.addPlayer(ev.getPlayer());
		}
	}

	@EventHandler
	public void onPickup(EntityPickupItemEvent ev){

		ItemStack item = ev.getItem().getItemStack();

		if(!this.itemHasCustomEnchant(item))
			return;

		EntitySongPlayer match = null;

		for(EntitySongPlayer player : entitySongPlayers){
			if(player.getEntity().getUniqueId() != ev.getEntity().getUniqueId())
				continue;

			match = player;
			break;
		}

		if(match == null)
			return;

		match.setPlaying(false);

		entitySongPlayers.remove(match);
	}

	@EventHandler
	public void onDrop(ItemSpawnEvent ev){

		ItemStack item = ev.getEntity().getItemStack();

		if(!this.itemHasCustomEnchant(item))
			return;

		int level = this.GetItemEnchantLevel(item);



		File file = new File(Bukkit.getPluginsFolder() + "/SussyPlugin/" + level + ".nbs");

		if(!file.exists())
			return;

		Song song = NBSDecoder.parse(file);

		EntitySongPlayer nbsPlayer = new EntitySongPlayer(song);

		nbsPlayer.setEntity(ev.getEntity());
		nbsPlayer.setRepeatMode(RepeatMode.ALL);
		nbsPlayer.setDistance(16);

		for(Player player : Bukkit.getOnlinePlayers()){
			nbsPlayer.addPlayer(player);
		}

		nbsPlayer.setPlaying(true);

		entitySongPlayers.add(nbsPlayer);

		ev.getEntity().setWillAge(false);
	}
}
