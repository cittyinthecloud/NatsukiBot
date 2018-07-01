package io.github.famous1622.NatsukiBot.listeners;

import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerSuggestionsListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getName().equals("server_suggestions")) {
			Guild guild = event.getGuild();
			Role adminRole = guild.getRolesByName("Admin", true).get(0);
			Role modRole = guild.getRolesByName("Moderator", true).get(0);
			Member member = guild.getMember(event.getAuthor());
			if(member.getRoles().contains(adminRole)||member.getRoles().contains(modRole)) {
				if (event.getMessage().getContentRaw().contains("$")) {
					return;
				}
			}
			event.getMessage().addReaction("⬆").complete();
			event.getMessage().addReaction("⬇").queue();
		}
	}
}
