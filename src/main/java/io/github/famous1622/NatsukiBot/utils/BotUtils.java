package io.github.famous1622.NatsukiBot.utils;

import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class BotUtils {
	public static PrivilegeLevel getMemberLevel(Member member) {
		Guild guild = member.getGuild();
		Role adminRole = guild.getRolesByName("Admin", true).get(0);
		Role modRole = guild.getRolesByName("Moderator", true).get(0);
		
		if(member.getRoles().contains(adminRole)) {
			return PrivilegeLevel.ADMIN;
		} else if (member.getRoles().contains(modRole)) {
			return PrivilegeLevel.MOD;
		} else {
			return PrivilegeLevel.USER;
		}
	}
}
