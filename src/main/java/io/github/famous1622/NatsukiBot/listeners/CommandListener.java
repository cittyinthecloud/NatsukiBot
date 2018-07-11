package io.github.famous1622.NatsukiBot.listeners;

import static io.github.famous1622.NatsukiBot.CONSTANTS.PREFIX;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
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
		Guild guild = event.getGuild();
		Member member = guild.getMember(event.getAuthor());
		Message message = event.getMessage();
		String content = message.getContentDisplay();
		
		if (event.getAuthor().isBot()) return;
		
		if (content.startsWith(PREFIX)) {
			content = content.substring(1);
			
			String commandstr = content.split(" ")[0];
			List<String> arguments = new ArrayList<String>(); 
			arguments.addAll(Arrays.asList(content.split(" ")));
			arguments.remove(0);
			
			for (Command command : commands) {
				if (command.getCommand().equals(commandstr)) {
					System.out.println(commandstr + " was matched.");
					if (BotUtils.memberHasPrivilege(member, command.getRequiredLevel())) {
						command.onCommand(event,arguments);
					}
				}
			}
		}
	}
	
	public static void addCommand(Command command) {
		commands.add(command);
	}
	
	public static List<Command> getCommands(){
		return Collections.unmodifiableList(commands);
	}
}
