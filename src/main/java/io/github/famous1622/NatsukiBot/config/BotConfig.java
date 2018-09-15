package io.github.famous1622.NatsukiBot.config;

import java.io.File;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigException;
import com.typesafe.config.ConfigFactory;
import com.typesafe.config.ConfigObject;

import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class BotConfig {

	final static Logger logger = LoggerFactory.getLogger("NatsukiBot.Config");
	private static Config config = ConfigFactory.parseFile(new File("application.conf"));
	private static Map<String, MessageEmbed> embeds = null;
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
					embedMap.put(name, BotUtils.unPackEmbed((String) conf_Embed.unwrapped()));
				});
			} catch (ConfigException.Missing e) {
				logger.warn("No embeds object in config");
			}
			embeds = Collections.unmodifiableMap(embedMap);
		}
		return embeds;
	}
}
