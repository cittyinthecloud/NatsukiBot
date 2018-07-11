package io.github.famous1622.NatsukiBot.commands;

import java.util.List;

import io.github.famous1622.NatsukiBot.listeners.CommandListener;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class HelpCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.USER;
	}

	@Override
	public String getCommand() {
		return "help";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		StringBuilder message = new StringBuilder("Here's the list of commands:\n```");
		Member member = event.getMember();
		List<Command> commands = CommandListener.getCommands();
		User author = event.getAuthor();
		
		commands.stream()
				.filter(command -> BotUtils.memberHasPrivilege(member, command.getRequiredLevel()))
				.forEachOrdered(command -> {
					message.append("\n").append(command.getCommand()).append("\n    ").append(command.getHelpMessage()).append('\n');
				});
		
		event.getMessage().delete().queue();
		author.openPrivateChannel().queue((pchannel) -> {
			pchannel.sendMessage(message.append("```")).queue();
		});
	}

	@Override
	public String getHelpMessage() {
		return "prints this help message";
	}
	
}
