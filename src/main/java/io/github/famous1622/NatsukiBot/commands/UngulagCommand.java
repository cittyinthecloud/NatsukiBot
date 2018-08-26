package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.ActionType;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UngulagCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "ungulag";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		List<Member> members = event.getMessage().getMentionedMembers();
		
		if (members.isEmpty()) {
			event.getChannel().sendMessage("This command requires at least one member to ungulag (as a mention)").queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		}
		
		StringBuilder builder = new StringBuilder();
		
		members.forEach((member) -> {
			boolean wasGulaged = GulagManager.getManager(member.getJDA()).removeGulag(member.getUser());
			if (wasGulaged) {
				builder.append("Ungulaged ").append(member.getEffectiveName()).append("\n");
			} else {
				builder.append(member.getEffectiveName()).append(" was not gulaged.\n");
			}
			
			Main.eventLog.logAction(new Action().withType(ActionType.UNGULAGUSER)
											  .withResponsible(event.getAuthor())
											  .withTarget(member.getUser())
											  .withArguments(""));
		});
		
		event.getChannel().sendMessage(builder.toString()).queue((message) -> {
			message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
		});
		
	}

	@Override
	public String getHelpMessage() {
		return "ungulags a member. Syntax: $ungulag @Member";
	}

	@Override
	public boolean mustBePublic() {
		return true;
	}

}
