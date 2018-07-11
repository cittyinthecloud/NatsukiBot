package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DisableRoleCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.ADMIN;
	}

	@Override
	public String getCommand() {
		return "noiam";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		RoleCommand.commandDisabled = !RoleCommand.commandDisabled;
		if (RoleCommand.commandDisabled) {
			event.getChannel().sendMessage("Disabled $iam").queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		} else {
			event.getChannel().sendMessage("Enabled $iam").queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		}
	}

	@Override
	public String getHelpMessage() {
		return "toggles whether or not the $iam command is usable";
	}
}
