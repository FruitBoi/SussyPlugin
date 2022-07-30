package xyz.titnoas.velocity.PacketListener;

import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.proxy.protocol.packet.TabCompleteRequest;
import com.velocitypowered.proxy.protocol.packet.TabCompleteResponse;
import com.velocitypowered.proxy.protocol.packet.chat.LegacyChat;
import com.velocitypowered.proxy.protocol.packet.chat.PlayerChat;
import com.velocitypowered.proxy.protocol.packet.chat.PlayerCommand;
import dev.simplix.protocolize.api.Direction;
import dev.simplix.protocolize.api.listener.AbstractPacketListener;
import dev.simplix.protocolize.api.listener.PacketReceiveEvent;
import dev.simplix.protocolize.api.listener.PacketSendEvent;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.TextColor;
import space.arim.libertybans.api.PlayerVictim;
import space.arim.libertybans.api.PunishmentType;
import space.arim.libertybans.api.punish.DraftPunishment;
import space.arim.libertybans.api.punish.PunishmentDrafter;
import xyz.titnoas.velocity.VelocityPlugin;

import java.util.logging.Level;

public class ChatPacketListenerNew extends AbstractPacketListener<com.velocitypowered.proxy.protocol.packet.chat.PlayerChat> {


	public ChatPacketListenerNew() {
		super(PlayerChat.class, Direction.UPSTREAM, 0);
	}

	@Override
	public void packetReceive(PacketReceiveEvent<PlayerChat> packetReceiveEvent) {
		var packet = packetReceiveEvent.packet();


		var msg = packet.getMessage();

		if (msg.toLowerCase().contains("${jndi:")) {
			packetReceiveEvent.cancelled(true);

			PunishmentDrafter drafter = VelocityPlugin.velocityPlugin.getLibertyBans().getDrafter();

			DraftPunishment draftBan = drafter.draftBuilder().type(PunishmentType.BAN).victim(PlayerVictim.of(packetReceiveEvent.player().uniqueId())).reason("LOG4J").build();

			var velPlayer = VelocityPlugin.velocityPlugin.getProxyServer().getPlayer(packetReceiveEvent.player().uniqueId());

			velPlayer.ifPresent(player -> VelocityPlugin.velocityPlugin.getLogger().log(Level.SEVERE, player.getUsername() + " has attempted log4shell"));


			draftBan.enactPunishment().thenAcceptSync(punishment -> {

				if (punishment.isEmpty()) {
					VelocityPlugin.velocityPlugin.getLogger().log(Level.SEVERE, "User that was already banned exploited log4shell");

					Player velocityPlayer = VelocityPlugin.velocityPlugin.getProxyServer().getPlayer(packetReceiveEvent.player().uniqueId()).orElseThrow();
					velocityPlayer.disconnect(Component.text("Disconnected"));
					return;
				}
				VelocityPlugin.velocityPlugin.getLogger().log(Level.SEVERE, "USER HAS ATTEMPTED TO EXPLOIT LOG4SHELL AND HAS BEEN BANNED.");
			});

		}


		if (msg.toLowerCase().startsWith("/pl") ||
				msg.toLowerCase().startsWith("/plugins") ||
				msg.toLowerCase().startsWith("/bukkit:plugins") ||
				msg.toLowerCase().startsWith("/bukkit:pl")) {

			var velplayeropt = VelocityPlugin.velocityPlugin.getProxyServer().getPlayer(packetReceiveEvent.player().uniqueId());
			if (velplayeropt.isPresent() && velplayeropt.get().hasPermission("sussyplugin.plbypass")) {
				return;
			}
			packetReceiveEvent.cancelled(true);
			if (velplayeropt.isPresent()) {
				Player velocityPlayer = velplayeropt.get();
				velocityPlayer.sendMessage(Component.text("You can not use /plugins on this server.").color(TextColor.color(255, 0, 0)));
			}

		}
	}


	@Override
	public void packetSend(PacketSendEvent<PlayerChat> packetSendEvent) {

	}
}
