package xyz.titnoas.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.command.CommandManager;
import com.velocitypowered.api.command.CommandMeta;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.player.ServerPreConnectEvent;
import com.velocitypowered.api.event.player.TabCompleteEvent;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.event.proxy.ProxyPingEvent;
import com.velocitypowered.api.event.query.ProxyQueryEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.proxy.Player;
import com.velocitypowered.api.proxy.ProxyServer;
import com.velocitypowered.api.proxy.server.QueryResponse;
import com.velocitypowered.api.proxy.server.ServerPing;
import dev.simplix.protocolize.api.Protocolize;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.text.format.TextDecoration;
import space.arim.libertybans.api.LibertyBans;
import space.arim.omnibus.Omnibus;
import space.arim.omnibus.OmnibusProvider;
import xyz.titnoas.velocity.PacketListener.ChatPacketListener;
import xyz.titnoas.velocity.PacketListener.TabCompleteListener;

import java.util.logging.Level;
import java.util.logging.Logger;

@Plugin(id = "sussyplugin", name = "Sussy Plugin", version = "0.1.0-SNAPSHOT",
		url = "https://tcsmp.xyz", description = "Sus", authors = {"Lemon"})
public class VelocityPlugin {

	private final ProxyServer server;
	private final Logger logger;

	private LibertyBans libertyBans;

	public static int troll = 0;

	public static VelocityPlugin velocityPlugin;

	public LibertyBans getLibertyBans(){
		return libertyBans;
	}

	public Logger getLogger(){
		return logger;
	}

	public ProxyServer getProxyServer(){
		return server;
	}

	@Inject
	public VelocityPlugin(ProxyServer server, Logger logger) {
		velocityPlugin = this;
		this.server = server;
		this.logger = logger;
		CommandManager manager = server.getCommandManager();

		CommandMeta meta = manager.metaBuilder("servermanage").build();

		manager.register(meta, new FunnyCommand());

		CommandMeta discord = manager.metaBuilder("discord").aliases("disc", "d").build();
		manager.register(discord, new DiscordCommand());

		logger.info("Lemon's velocity plugin :)");
	}


	@Subscribe
	public void onProxyInitialization(ProxyInitializeEvent ev){
		Protocolize.listenerProvider().registerListener(new TabCompleteListener());
		Protocolize.listenerProvider().registerListener(new ChatPacketListener());
		Omnibus omnibus = OmnibusProvider.getOmnibus();
		libertyBans = omnibus.getRegistry().getProvider(LibertyBans.class).orElseThrow();
	}

	@Subscribe
	public void onPlayerJoin(com.velocitypowered.api.event.connection.LoginEvent ev){

		logger.log(Level.INFO, "Caught join from " + ev.getPlayer().getUsername());

		for(Player player : server.getAllPlayers()){
			if(player.hasPermission("sussyplugin.onjoin"))
				player.sendMessage(Component.text(ev.getPlayer().getUsername() + " has connected.").color(TextColor.color(0x03fc6b)));
		}
	}

	@Subscribe
	public void onServerSwitch(com.velocitypowered.api.event.player.ServerPreConnectEvent ev){

		if(ev.getPlayer().getModInfo().isPresent() || !ev.getResult().getServer().isPresent() ||
				!ev.getResult().getServer().get().getServerInfo().getName().equalsIgnoreCase("modded")){
			return;
		}

		logger.log(Level.INFO, ev.getPlayer().getUsername() + " attempting to join " + ev.getResult().getServer().get().getServerInfo().getName());

		if(!ev.getPlayer().getClientBrand().contains("forge")){
			ev.setResult(ServerPreConnectEvent.ServerResult.denied());
			ev.getPlayer().sendMessage(Component.text("You must install ")
					.color(TextColor.color(0xd11f1f))
					.append(Component.text("Enigmatica 2: Expert").toBuilder().clickEvent(ClickEvent.openUrl("https://www.curseforge.com/minecraft/modpacks/enigmatica2expert")).decorate(TextDecoration.UNDERLINED).color((TextColor.color(0xd11f1f))))
					.append(Component.text(" to join the modded server.").color(TextColor.color(0xd11f1f))));


			logger.log(Level.INFO, ev.getPlayer().getUsername() + " cannot join modded because they don't have forge installed");

		}
	}

	@Subscribe
	public void onProxyPing(ProxyPingEvent ev){

		ServerPing.Builder ping = ev.getPing().asBuilder();

		logger.log(Level.INFO, "Serverlist ping from " + ev.getConnection().getRemoteAddress().toString());

		if(troll != 0)
		{
			ping.onlinePlayers(ping.getOnlinePlayers() + troll);

			if(troll > ping.getMaximumPlayers())
				ping.maximumPlayers(troll + 1);
		}

		ev.setPing(ping.build());
	}

	@Subscribe
	public void onTabComplete(TabCompleteEvent ev){

		if(ev.getPartialMessage().equalsIgnoreCase("/"))
		{
			ev.getSuggestions().clear();
			ev.getSuggestions().add("Not today ;)");
			return;
		}
	}

	@Subscribe
	public void onConn(ProxyQueryEvent ev){
		QueryResponse.Builder response = ev.getResponse().toBuilder();

		response.clearPlayers();
		response.clearPlayers();

		response.proxyVersion("1.8.x - 1.18.x");
		response.plugins(QueryResponse.PluginInformation.of("TCSMP Plugins", "1.0"));


		ev.setResponse(response.build());
	}



}
