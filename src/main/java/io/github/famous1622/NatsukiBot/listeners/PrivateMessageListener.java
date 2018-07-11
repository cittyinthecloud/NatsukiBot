package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;
import net.dv8tion.jda.core.managers.GuildController;

public class PrivateMessageListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if (event.isFromType(ChannelType.PRIVATE)) {
			if (event.getMessage().getContentDisplay().equalsIgnoreCase("I understand the rules of the comedy-dark channel")) {
				String authorid = event.getAuthor().getId();
				if (ComedyDarkCommand.guilds.containsKey(authorid)) {
					Guild guild = ComedyDarkCommand.guilds.get(authorid);
					event.getMessage().addReaction("👌").queue();
					Member member = guild.getMember(event.getAuthor());
					Role cdrole = guild.getRolesByName("comedy-dark", true).get(0);
					new GuildController(guild).addSingleRoleToMember(member, cdrole).queue();
				}
			}
		}
	}
	
}
