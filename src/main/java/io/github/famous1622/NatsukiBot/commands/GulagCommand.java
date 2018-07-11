package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class GulagCommand implements Command {
	
	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "gulag";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		List<Member> mentions = event.getMessage().getMentionedMembers();
		if (mentions.size()!=1 || arguments.size()<2) {
			event.getChannel().sendMessage("Syntax: `$gulag @User Xh`").queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		}
		String mention = arguments.get(0);
		String time = arguments.get(1);
		if (!mention.startsWith("@") || !time.endsWith("h")) {
			event.getChannel().sendMessage("Syntax: `$gulag @User Xh`").queue((message) -> {
				message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
			});
		}
		Member member = mentions.get(0);
		time = time.substring(0, time.length()-1);
		long gulagtime = Long.parseLong(time)*1000;
		GulagManager.getManager().addGulag(member, gulagtime);
	} 
}
