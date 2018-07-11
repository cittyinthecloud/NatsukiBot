package io.github.famous1622.NatsukiBot.utils;

import java.util.ArrayList;
import java.util.List;

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
	
	public static List<Role> idListToRoleList(Guild guild, List<String> ids ){
		List<Role> out = new ArrayList<Role>(ids.size());
		for (String id : ids) {
			out.add(guild.getRoleById(id));
		}
		return out;
	}
	
	public static List<String> roleListToIdList(List<Role> roles){
		List<String> out = new ArrayList<String>(roles.size());
		roles.forEach((role) -> out.add(role.getId()));
		return out;
	}
}
