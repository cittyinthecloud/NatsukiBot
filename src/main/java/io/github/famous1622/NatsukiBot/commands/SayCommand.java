package io.github.famous1622.NatsukiBot.commands;

import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class SayCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "say";
	}

	@Override
	public String getHelpMessage() {
		return "says something";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		event.getChannel().sendMessage(String.join(" ", arguments)).queue();
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}

}
