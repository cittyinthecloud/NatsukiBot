package io.github.famous1622.NatsukiBot.listeners;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;
import io.github.famous1622.NatsukiBot.eventlog.types.OperationType;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter{
	private static List<Command> commands = new ArrayList<Command>();
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		
		Member member;
		
		if (event.isFromType(ChannelType.PRIVATE)) {
			member = event.getJDA().getGuildById(BotConfig.getGuildId()).getMember(event.getAuthor());
		} else {
			member = event.getMember();
		}
		
		Message message = event.getMessage();
		String content = message.getContentRaw();
		
		if (event.getAuthor().isBot()) return;
				
		if (content.startsWith(BotConfig.getPrefix())) {
			content = content.substring(BotConfig.getPrefix().length());
			
			String commandstr = content.split(" ")[0];
			List<String> arguments = new ArrayList<String>(); 
			arguments.addAll(Arrays.asList(content.split(" ")));
			arguments.remove(0);
			
			for (Command command : commands) {
				if (command.getCommand().equals(commandstr)) {
					System.out.println(commandstr + " was matched.");
					if (BotUtils.memberHasPrivilege(member, command.getRequiredLevel())) {
						if(!command.mustBePublic() || !event.isFromType(ChannelType.PRIVATE)) {
							command.onCommand(event,arguments);
							
							if (event.isFromType(ChannelType.TEXT)) {
								event.getMessage().delete().queue();
							}
							
							Main.eventLog.logOperation(new Operation(this).withType(OperationType.RUNCOMMAND)
																		.withParty(event.getAuthor())
																		.withData(event.getMessage().getContentDisplay()));
						} else {
							Main.eventLog.logOperation(new Operation(this).withType(OperationType.REFUSECOMMAND)
																		.withParty(event.getAuthor())
																		.withData("Public command attempted in a private context: " + event.getMessage().getContentDisplay()));
						}
					} else {
						Main.eventLog.logOperation(new Operation(this).withType(OperationType.REFUSECOMMAND)
																	.withParty(event.getAuthor())
																	.withData("Command attempted with inadequate permission: " + event.getMessage().getContentDisplay()));
					}
					break;
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
