package io.github.famous1622.NatsukiBot.commands;

import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PingCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.USER;
	}

	@Override
	public String getCommand() {
		return "ping";
	}

	@Override
	public String getHelpMessage() {
		return "Pong!";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		event.getChannel().sendMessage("Pong! ("+ ChronoUnit.MILLIS.between(event.getMessage().getCreationTime(), Instant.now())+" ms)");
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}
}
