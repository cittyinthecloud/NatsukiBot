package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.stream.Collectors;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class BotPurgeCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "botpurge";
	}

	@Override
	public String getHelpMessage() {
		return "Deletes bot messages from the current channel.";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		event.getChannel().getHistory().retrievePast(100).queue(msgs -> {
			List<Message> msgsToDelete = msgs.stream().filter(m -> m.getAuthor().isBot()).collect(Collectors.toList());
			
			if (!msgsToDelete.isEmpty()) {
				event.getTextChannel().deleteMessages(msgsToDelete).queue();
			}
		});
	}

	@Override
	public boolean mustBePublic() {
		return true;
	}

}
