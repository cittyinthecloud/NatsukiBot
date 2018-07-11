package io.github.famous1622.NatsukiBot.listeners;

import java.io.IOException;
import java.util.List;

import io.github.famous1622.NatsukiBot.CONSTANTS;
import io.github.famous1622.NatsukiBot.config.RestoreRoleConfiguration;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import io.github.famous1622.NatsukiBot.utils.JSONUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
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
		try {
			RestoreRoleConfiguration rrconfig = RestoreRoleConfiguration.getConfig();
		
			if(rrconfig.containsKey(user.getId())) {
				String packed = rrconfig.getProperty(user.getId());
				List<String> unpacked = JSONUtils.unpackStringList(packed);
				System.out.println(unpacked.size());
				List<Role> roles = BotUtils.idListToRoleList(event.getGuild(), unpacked);
				event.getGuild().getController().addRolesToMember(member, roles)
												.reason("Restore pre-leave roles")
												//to update Gulags (if they were gulaged, they're null now, this reloads that)
												.queue((v) -> GulagManager.getManager().reload(member.getJDA()));
			} else {
				String username = user.getAsMention();
				TextChannel channel = event.getGuild().getSystemChannel();
				
				channel.sendMessage(username + " " + CONSTANTS.WELCOMEMESSAGE).queue();
				user.openPrivateChannel().queue((pchannel) -> {pchannel.sendMessage(CONSTANTS.WELCOMEDM).queue();});
				System.out.println("complete");
			}
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
