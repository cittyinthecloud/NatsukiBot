package io.github.famous1622.NatsukiBot.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONObject;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;

public class JSONUtils {
	public static String packList(List<String> list) {
		return new JSONArray(list).toString();
	}
	
	public static List<String> unpackStringList(String packed){
		JSONArray arr = new JSONArray(packed);
		List<String> out = new ArrayList<String>(arr.length());
		for (Object object : arr) {
			out.add((String) object);
		}
		return out;
	}
	
	public static String packMember(Member member) {
		return new JSONObject().put("guildId", member.getGuild().getId())
							   .put("userId",  member.getUser().getId()).toString();
	}
	
	public static Member unpackMember(JDA jda, String packed) {
		JSONObject object = new JSONObject(packed);
		Guild guild = jda.getGuildById(object.getString("guildId")); 
		return guild.getMemberById(object.getString("userId"));
	}
}
