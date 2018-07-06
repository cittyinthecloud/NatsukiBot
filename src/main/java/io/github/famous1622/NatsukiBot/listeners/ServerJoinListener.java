package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.CONSTANTS;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerJoinListener extends ListenerAdapter 
{
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) 
	{
		User user = event.getUser();
		String username = user.getAsMention();
		TextChannel channel = event.getGuild().getSystemChannel();
		
		channel.sendMessage(username + " " + CONSTANTS.WELCOMEMESSAGE).queue();
		user.openPrivateChannel().queue((pchannel) -> {pchannel.sendMessage(CONSTANTS.WELCOMEDM).queue();});
		System.out.println("complete");
	}
}
