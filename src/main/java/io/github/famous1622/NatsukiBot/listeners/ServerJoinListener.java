package io.github.famous1622.NatsukiBot.listeners;

import java.util.List;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.data.RoleStashData;
import io.github.famous1622.NatsukiBot.logging.types.Operation;
import io.github.famous1622.NatsukiBot.logging.types.OperationType;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.utils.LoggingUtils;
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
		RoleStashData rrconfig = RoleStashData.getConfig(event.getJDA());

		if(rrconfig.containsKey(user)) {
			List<Role> roles = rrconfig.get(user);
			event.getGuild().getController().addRolesToMember(member, roles)
											.reason("Restore pre-leave roles")
											//to update Gulags (if they were gulaged, they're null now, this reloads that)
											.queue((v) -> GulagManager.getManager(user.getJDA()).reload());
			rrconfig.remove(user);
			
			Main.botLog.logOperation(new Operation(this).withType(OperationType.RESTOREROLES)
														.withParty(user)
														.withData(LoggingUtils.roleListToLogData(roles)));
			
			user.openPrivateChannel().queue(pchannel -> pchannel.sendMessage(BotConfig.getWelcomeBack()).queue());
		} else {			
			user.openPrivateChannel().queue(pchannel -> pchannel.sendMessage(BotConfig.getWelcomeDM()).queue());
			System.out.println("complete");
		}
	}
}
