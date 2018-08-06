package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.CONSTANTS;
import io.github.famous1622.NatsukiBot.data.RoleStashData;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerJoinListener extends ListenerAdapter 
{
	@Override
	public void onGuildMemberJoin(GuildMemberJoinEvent event) 
	{
		Member member = event.getMember();
		User user = event.getUser();
		RoleStashData rrconfig = RoleStashData.getConfig(event.getJDA());

		if(rrconfig.containsKey(user)) {
			event.getGuild().getController().addRolesToMember(member, rrconfig.get(user))
											.reason("Restore pre-leave roles")
											//to update Gulags (if they were gulaged, they're null now, this reloads that)
											.queue((v) -> GulagManager.getManager(user.getJDA()).reload());
		} else {
			String username = user.getAsMention();
			TextChannel channel = event.getGuild().getSystemChannel();
			
			channel.sendMessage(username + " " + CONSTANTS.WELCOMEMESSAGE).queue();
			user.openPrivateChannel().queue((pchannel) -> {pchannel.sendMessage(CONSTANTS.WELCOMEDM).queue();});
			System.out.println("complete");
		}
	}
}
