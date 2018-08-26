package io.github.famous1622.NatsukiBot.config;

import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class BotConfig {

	private static Config config = ConfigFactory.load();
	
	public static String getGuildId() {
		return config.getString("guildId");
	}
	
	public static String getToken() {
		return config.getString("token");
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
}
