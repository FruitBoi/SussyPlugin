package xyz.titnoas.velocity;

import com.velocitypowered.api.command.Command;
import com.velocitypowered.api.command.CommandSource;

import com.velocitypowered.api.command.RawCommand;
import com.velocitypowered.api.command.SimpleCommand;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.TextComponent;
import org.checkerframework.checker.nullness.qual.NonNull;

public class FunnyCommand implements SimpleCommand {


	@Override
	public void execute(Invocation invocation) {

		CommandSource commandSource = invocation.source();
		String[] strings = invocation.arguments();

		if (!commandSource.hasPermission("sussyplugin.manage"))
			return;

		if (strings.length != 1) {
			commandSource.sendMessage(Component.text("Invalid arguments: /servermanage (count)"));
			return;
		}

		String arg = strings[0];
		int i;
		try {
			i = Integer.parseInt(arg);
		} catch (NumberFormatException e) {
			commandSource.sendMessage(Component.text("Error parsing arguments."));
			return;
		}

		VelocityPlugin.troll = i;
		commandSource.sendMessage(Component.text("Set."));
	}

	@Override
	public boolean hasPermission(final Invocation invocation) {
		return invocation.source().hasPermission("sussyplugin.manage");
	}
}