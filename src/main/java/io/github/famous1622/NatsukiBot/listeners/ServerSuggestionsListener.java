package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerSuggestionsListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		if(event.getChannel().getName().equals("server_suggestions")) {
			Guild guild = event.getGuild();
			Member member = guild.getMember(event.getAuthor());
			if(BotUtils.memberHasPrivilege(member, PrivilegeLevel.MOD)) {
				if (event.getMessage().getContentRaw().contains("$")) {
					return;
				}
			}
			event.getMessage().addReaction("⬆").queue((v)->{
				event.getMessage().addReaction("⬇").queue();
			});
		}
	}
}
