package xyz.titnoas.sussyplugin.listener;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import com.velocitypowered.api.event.connection.PluginMessageEvent;
import me.vagdedes.spartan.api.PlayerViolationEvent;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.messaging.PluginMessageListener;
import org.bukkit.plugin.messaging.PluginMessageListenerRegistration;
import org.jetbrains.annotations.NotNull;
import xyz.titnoas.sussyplugin.SussyPlugin;

import java.util.UUID;

public class SpartanListener implements Listener, PluginMessageListener {

	@EventHandler
	public void onPlayerViolate(PlayerViolationEvent ev){

		Bukkit.getScheduler().runTask(SussyPlugin.sussyPlugin, () -> {

			ByteArrayDataOutput out = ByteStreams.newDataOutput();

			out.writeUTF("violation");
			UUID playerUuid = ev.getPlayer().getUniqueId();
			out.writeLong(playerUuid.getLeastSignificantBits());
			out.writeLong(playerUuid.getMostSignificantBits());
			out.writeUTF(ev.getHackType().toString());
			out.writeUTF(ev.getMessage());

			ev.getPlayer().sendPluginMessage(SussyPlugin.sussyPlugin, "sussyplugin:proxycomms", out.toByteArray());
		});

	}

	@Override
	public void onPluginMessageReceived(@NotNull String channel, @NotNull Player player, @NotNull byte[] message) {
		if(!channel.equalsIgnoreCase("sussyplugin:proxycomms"))
			return;


	}
}
