package io.github.famous1622.NatsukiBot.utils;

import java.awt.Color;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.MessageEmbed;
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
	
	public static Emote getEmote(String emote) {
		return Main.jda.getEmotesByName(emote, false).get(0);
	}

	public static MessageEmbed unPackEmbed(String emb_JSON) {
		JsonReader reader = new JsonReader(new StringReader(emb_JSON));
		reader.setLenient(true);
		JsonObject JSON_embed = new JsonParser().parse(reader).getAsJsonObject();
		EmbedBuilder builder = new EmbedBuilder();
		if (JSON_embed.has("title")) {
			builder.setTitle(JSON_embed.getAsJsonPrimitive("title").getAsString(),
							 JSON_embed.has("url") ? JSON_embed.getAsJsonPrimitive("url").getAsString() : null);
		}
		
		if (JSON_embed.has("description")) {
			builder.setDescription(JSON_embed.getAsJsonPrimitive("description").getAsString());
		}
		
		if (JSON_embed.has("timestamp")) {
			String timestamp = JSON_embed.getAsJsonPrimitive("timestamp").getAsString();
			try {
				builder.setTimestamp(BotUtils.discordDateFormat.parse(timestamp).toInstant());
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if (JSON_embed.has("color")) {
			builder.setColor(new Color(JSON_embed.getAsJsonPrimitive("color").getAsInt()));
		}
		
		if (JSON_embed.has("footer")) {
			JsonObject JSON_footer = JSON_embed.get("footer").getAsJsonObject();
			builder.setFooter(JSON_footer.getAsJsonPrimitive("text").getAsString(), JSON_footer.has("icon_url") ? JSON_footer.get("icon_url").getAsString() : null);
		}
		
		if (JSON_embed.has("image")) {
			JsonObject JSON_image = JSON_embed.get("image").getAsJsonObject();
			builder.setImage(JSON_image.get("url").getAsString());
		}
		
		if (JSON_embed.has("thumbnail")) {
			JsonObject JSON_thumbnail = JSON_embed.get("thumbnail").getAsJsonObject();
			builder.setImage(JSON_thumbnail.get("url").getAsString());
		}
		
		if (JSON_embed.has("author")) {
			JsonObject JSON_author = JSON_embed.get("author").getAsJsonObject();
			builder.setAuthor(JSON_author.get("name").getAsString(), 
							  JSON_author.has("url") ? JSON_author.get("url").getAsString() : null);
		}
		
		if (JSON_embed.has("fields")) {
			JsonArray JSON_fields = JSON_embed.get("fields").getAsJsonArray();
			JSON_fields.forEach((fieldElem) -> {
				JsonObject fieldObj = fieldElem.getAsJsonObject();
				builder.addField(fieldObj.get("name").getAsString(), fieldObj.get("value").getAsString(), fieldObj.has("inline") ? fieldObj.get("inline").getAsBoolean() : false);
			});
		}
		return builder.build();
	}

	public static final SimpleDateFormat discordDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
}
