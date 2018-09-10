package io.github.famous1622.NatsukiBot.config;

import java.awt.Color;
import java.io.File;
import java.io.StringReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class BotConfig {

	final static Logger logger = LoggerFactory.getLogger("NatsukiBot.Config");
	private static Config config = ConfigFactory.parseFile(new File("application.conf"));
	private static Map<String, MessageEmbed> embeds = null;
	private static final SimpleDateFormat discordDateFormat = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
	
	public static String getGuildId() {
		return config.getString("guildId");
	}
	
	public static String getToken() {
		if (System.getenv("NBOT_TOKEN") != null) {
			return System.getenv("NBOT_TOKEN");
		} else {
			return config.getString("token");
		}
	}
	
	public static String getPrefix() {
		return config.getString("prefix");
	}
	
	public static String getEheheId() {
		return config.getString("eheheId");
	}
	
	public static String getWelcomeDM() {
		return config.getString("welcomeDm");
	}

	public static String getWelcomeBack() {
		return config.getString("welcomeBackMessage");
	}
	
	public static String getComedyDarkMessage() {
		return config.getString("cdmessage");
	}
	
	
	public static SheetsConfig getSheetsConfig() {
		return new SheetsConfig(config.getString("sheets.id"), config.getString("sheets.action"), config.getString("sheets.operation"));
	}

	public static String getLogId() {
		return config.getString("discord.id");
	}
	
	public static Map<String,MessageEmbed> getEmbeds() {
		if (embeds == null) {
			HashMap<String, MessageEmbed> embedMap = new HashMap<String,MessageEmbed>();
			try {
				ConfigObject config_EmbedMap = config.getObject("embeds");
				config_EmbedMap.forEach((name, conf_Embed) -> {
					embedMap.put(name, unPackEmbed((String) conf_Embed.unwrapped()));
				});
			} catch (ConfigException.Missing e) {
				logger.warn("No embeds object in config");
			}
			embeds = Collections.unmodifiableMap(embedMap);
		}
		return embeds;
	}
	
	private static MessageEmbed unPackEmbed(String emb_JSON) {
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
				builder.setTimestamp(discordDateFormat.parse(timestamp).toInstant());
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
}
