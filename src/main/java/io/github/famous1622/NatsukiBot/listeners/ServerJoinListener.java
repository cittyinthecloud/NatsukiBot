package io.github.famous1622.NatsukiBot.listeners;

import java.awt.Color;
import java.util.List;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.data.RoleStashData;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;
import io.github.famous1622.NatsukiBot.eventlog.types.OperationType;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import io.github.famous1622.NatsukiBot.utils.LoggingUtils;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
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
			List<Role> roles = rrconfig.get(user);
			event.getGuild().getController().addRolesToMember(member, BotUtils.getModifiableRoles(roles))
											.reason("Restore pre-leave roles")
											//to update Gulags (if they were gulaged, they're null now, this reloads that)
											.queue((v) -> GulagManager.getManager(user.getJDA()).sync());
			rrconfig.remove(user);
			
			Main.eventLog.logOperation(new Operation(this).withType(OperationType.RESTOREROLES)
														.withParty(user)
														.withData(LoggingUtils.roleListToLogData(roles)));
			
				user.openPrivateChannel().queue(channel ->{
					channel.sendMessage(new EmbedBuilder()
						    .setTitle("Hello again!")
						    .setDescription("I've restored your roles to the way they were before you left. If you would like them changed, please ask a Moderator.")
						    .setColor(new Color(8311585))
						    .setAuthor("Welcome back to the Doki Doki Modding Club", null, "https://i.imgur.com/Jquzd6N.png")
						    .build());
				});
			} else {			
			user.openPrivateChannel().queue(pchannel -> pchannel.sendMessage(BotConfig.getWelcomeDM()).queue());
			user.openPrivateChannel().queue(channel ->{
				channel.sendMessage(BotConfig.getWelcomeDM());
			});
			System.out.println("complete");
		}
	}
}
