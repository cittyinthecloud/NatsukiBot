package io.github.famous1622.NatsukiBot.utils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.utils.PermissionUtil;

public class BotUtils {
	
	public static PrivilegeLevel getMemberLevel(Member member) {
		Guild guild = member.getGuild();
		Role adminRole = guild.getRolesByName("Admin", true).get(0);
		Role modRole = guild.getRolesByName("Moderator", true).get(0);
		Role itTeamRole = guild.getRolesByName("IT Team", true).get(0);
		
		if(member.getRoles().contains(adminRole) || member.getRoles().contains(itTeamRole)) {
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
	
	public static boolean memberHasPrivilege(Member member, PrivilegeLevel level) {
		return getMemberLevel(member).compareTo(level)>=0;
	}
	
	public static String getDiscordTag(User user) {
		return user.getName()+"#"+user.getDiscriminator();
	}
	
	public static List<Role> getModifiableRoles(List<Role> roles) {
		if (roles.isEmpty()) {
			return roles;
		} else {
			Guild guild = Main.jda.getGuildById(BotConfig.getGuildId());
			Member self = guild.getMember(Main.jda.getSelfUser());
			return Collections.unmodifiableList(
						roles.stream()
							 .filter(role -> PermissionUtil.canInteract(self, role))
							 .collect(Collectors.toList())
				    );
		}
	}
}
