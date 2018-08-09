package io.github.famous1622.NatsukiBot.listeners;

import java.util.List;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.data.RoleStashData;
import io.github.famous1622.NatsukiBot.logging.types.Operation;
import io.github.famous1622.NatsukiBot.logging.types.OperationType;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.utils.BotUtils;
import io.github.famous1622.NatsukiBot.utils.LoggingUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerLeaveListener extends ListenerAdapter {
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Member member = event.getMember();
		List<Role> roles = BotUtils.getModifiableRoles(member.getRoles());
		RoleStashData rrconfig = RoleStashData.getConfig(event.getJDA());
		rrconfig.put(member.getUser(), roles);
		Main.botLog.logOperation(new Operation(this).withType(OperationType.STASHROLES)
													.withParty(member.getUser())
													.withData(LoggingUtils.roleListToLogData(roles)));
		rrconfig.saveToDisk();
		GulagManager.getManager(member.getJDA()).reload();		
	}
}
