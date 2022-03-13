package xyz.titnoas.velocity;

import com.velocitypowered.api.command.CommandSource;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.format.TextColor;

public class DiscordCommand implements SimpleCommand {
	@Override
	public void execute(Invocation invocation) {
		CommandSource source = invocation.source();

		Component reply = Component.text("Join our discord: ").color(TextColor.color(0x00bdad))
				.append(Component.text("discord.gg/p4dJuzmxFR").clickEvent(ClickEvent.openUrl("https://discord.gg/p4dJuzmxFR"))
						.color(TextColor.color(0x35964a)));

		source.sendMessage(reply);
	}
}
