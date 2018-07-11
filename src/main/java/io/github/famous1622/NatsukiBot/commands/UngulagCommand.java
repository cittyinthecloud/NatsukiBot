package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class UngulagCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		// TODO Auto-generated method stub
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		// TODO Auto-generated method stub
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
			GulagManager.getManager().removeGulag(member);
			builder.append("Ungulaged ").append(member.getEffectiveName()).append("\n");
		});
		
		event.getChannel().sendMessage(builder).queue((message) -> {
			message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
		});
		
	}

}
