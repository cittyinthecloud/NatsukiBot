package io.github.famous1622.NatsukiBot.listeners;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import io.github.famous1622.NatsukiBot.config.RestoreRoleConfiguration;
import io.github.famous1622.NatsukiBot.utils.JSONUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class ServerLeaveListener extends ListenerAdapter {
	@Override
	public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
		Member member = event.getMember();
		List<Role> roles = member.getRoles();
		List<String> ids = new ArrayList<String>(roles.size());
		for (Role role : roles) {
			ids.add(role.getId());
		}
		try {
			RestoreRoleConfiguration rrconfig = RestoreRoleConfiguration.getConfig();
			String packed = JSONUtils.packList(ids);
			rrconfig.setProperty(member.getUser().getId(), packed);
			rrconfig.saveToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}
}
