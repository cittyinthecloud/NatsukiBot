package io.github.famous1622.NatsukiBot.commands;

import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class DisableRoleCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "noiam";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		RoleCommand.commandDisabled = !RoleCommand.commandDisabled;
		if (RoleCommand.commandDisabled) {
			event.getChannel().sendMessage("**DISABLED ROLE COMMAND**").queue();
		} else {
			event.getChannel().sendMessage("**ENABLED ROLE COMMAND**").queue();
		}
	}

}
