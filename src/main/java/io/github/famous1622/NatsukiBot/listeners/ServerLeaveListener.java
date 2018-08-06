package io.github.famous1622.NatsukiBot.listeners;

import java.util.List;

import io.github.famous1622.NatsukiBot.data.RoleStashData;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerLeaveListener extends ListenerAdapter {
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Member member = event.getMember();
		List<Role> roles = member.getRoles();
		RoleStashData rrconfig = RoleStashData.getConfig(event.getJDA());
		rrconfig.put(member.getUser(), roles);
		rrconfig.saveToDisk();
		GulagManager.getManager(member.getJDA()).reload();		
	}
}
