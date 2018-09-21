package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;
import io.github.famous1622.NatsukiBot.eventlog.types.OperationType;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PurgeCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "purge";
	}

	@Override
	public String getHelpMessage() {
		return "Deletes X messages from the current channel. Usage: !purge X";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		if (arguments.size() < 1) {
			event.getChannel().sendMessage("Usage: `!purge X`").queue(m -> m.delete().queueAfter(10000, TimeUnit.MILLISECONDS));
		}
		
		try {
			int limit = Integer.parseInt(arguments.get(0));
			event.getChannel().getHistoryBefore(event.getMessage(), limit).queue(history -> {
				event.getTextChannel().deleteMessages(history.getRetrievedHistory()).queue();
				Main.eventLog.logOperation(new Operation(this).withType(OperationType.PURGEMESSAGES)
															  .withParty(event.getAuthor())
															  .withData(limit + " messages in #" + event.getChannel().getName()));
			});
		} catch (NumberFormatException e) {
			event.getChannel().sendMessage(arguments.get(0)+" is not a valid Number.");
		}
		
		
	}

	@Override
	public boolean mustBePublic() {
		return true;
	}

}
