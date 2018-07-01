package io.github.famous1622.NatsukiBot.listeners;

import static io.github.famous1622.NatsukiBot.CONSTANTS.PREFIX;

import java.util.ArrayList;
import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter{
	private static List<Command> commands = new ArrayList<Command>();
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) {
			return;
		}
		Message message = event.getMessage();
		String content = message.getContentRaw();
		if (content.startsWith(PREFIX)) {
			content = content.substring(1);
			String commandstr = content.split(" ")[0];
			for (Command command : commands) {
				if (command.getCommand().equals(commandstr)) {
					System.out.println(commandstr + " was matched.");
					command.onCommand(event);
				}
			}
		}
	}
	
	public static void addCommand(Command command) {
		commands.add(command);
	}
}
