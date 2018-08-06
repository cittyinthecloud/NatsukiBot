package io.github.famous1622.NatsukiBot.types;

import java.util.ArrayList;
import java.util.List;

import net.dv8tion.jda.core.entities.Role;

public class GulagState {
	public long unGulagTime = 0;
	public List<Role> roles = new ArrayList<Role>();
	
	public boolean isGulaged() {
		return unGulagTime > System.currentTimeMillis();
	}
}
//	public String packToString() {
//		JSONObject jo = new JSONObject().put("unGulagTime",  unGulagTime)
//							            .put("roles", BotUtils.roleListToIdList(roles));
//		if (!roles.isEmpty()) {
//			jo.put("guildId", roles.get(0).getGuild().getId());
//		}
//		return jo.toString();
//	}
//
//	public static GulagState unpack(JDA jda, String packed) {
//		JSONObject jo = new JSONObject(packed);
//		GulagState state = new GulagState();
//		state.unGulagTime = jo.getLong("unGulagTime");
//		if (jo.has("guildId")) {
//			@SuppressWarnings("unchecked")
//			List<String> idlist = (List<String>)(List<?>)jo.getJSONArray("roles").toList();
//			state.roles = BotUtils.idListToRoleList(jda.getGuildById(jo.getString("guildId")), idlist);
//		} else {
//			state.roles = new ArrayList<Role>();
//		}
//		return state;
//	}

