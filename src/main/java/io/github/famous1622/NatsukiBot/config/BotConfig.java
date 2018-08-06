package io.github.famous1622.NatsukiBot.config;

import java.io.FileNotFoundException;
import java.io.FileReader;

import com.google.gson.JsonIOException;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import com.google.gson.stream.JsonReader;

public class BotConfig {
	private static JsonObject config = null;

	private static JsonObject getConfig() {
		if (config == null) {
			try {
				config = new JsonParser().parse(new JsonReader(new FileReader("config.json"))).getAsJsonObject();
			} catch (JsonIOException | JsonSyntaxException | FileNotFoundException e) {
				e.printStackTrace();
			}
		} 
		return config;
	}
	public static String getGuildId() {
		return getConfig().getAsJsonPrimitive("guildId").getAsString();
	}
	public static String getToken(){
		return getConfig().getAsJsonPrimitive("token").getAsString();
	}
	public static String getPrefix(){
		return getConfig().getAsJsonPrimitive("prefix").getAsString();
	}
	//<:Ehehe:407279378414960640>
	public static String getEheheId(){
		return getConfig().getAsJsonPrimitive("eheheId").getAsString();
	}
}
