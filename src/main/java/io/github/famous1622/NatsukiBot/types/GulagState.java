package io.github.famous1622.NatsukiBot.types;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONObject;

import io.github.famous1622.NatsukiBot.utils.BotUtils;
import io.github.famous1622.NatsukiBot.utils.JSONUtils;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;

public class GulagState {
	public int timesInGulag = 0;
	public long unGulagTime = 0;
	public List<Role> roles = new ArrayList<Role>();
	
	public boolean isGulaged() {
		return unGulagTime > System.currentTimeMillis();
	}

	public String packToString() {
		JSONObject jo = new JSONObject().put("timesInGulag", timesInGulag)
							            .put("unGulagTime",  unGulagTime)
							            .put("roles", BotUtils.roleListToIdList(roles));
		if (!roles.isEmpty()) {
			jo.put("guildId", roles.get(0).getGuild().getId());
		}
		return jo.toString();
	}

	public static GulagState unpack(JDA jda, String packed) {
		JSONObject jo = new JSONObject(packed);
		GulagState state = new GulagState();
		state.timesInGulag = jo.getInt("timesInGulag");
		state.unGulagTime = jo.getLong("unGulagTime");
		if (jo.has("guildId")) {
			@SuppressWarnings("unchecked")
			List<String> idlist = (List<String>)(List<?>)jo.getJSONArray("roles").toList();
			state.roles = BotUtils.idListToRoleList(jda.getGuildById(jo.getString("guildId")), idlist);
		} else {
			state.roles = new ArrayList<Role>();
		}
		return state;
	}
}
