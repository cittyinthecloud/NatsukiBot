package io.github.famous1622.NatsukiBot.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.User;

public class UserSerializer implements JsonSerializer<User>, JsonDeserializer<User> {

	private JDA jda;
	public UserSerializer(JDA jda) {
		this.jda = jda;
	}

	@Override
	public User deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context)
			throws JsonParseException {
		return jda.getUserById(json.getAsString());
	}

	@Override
	public JsonElement serialize(User src, Type typeOfSrc, JsonSerializationContext context) {
		return new JsonPrimitive(src.getId());
	}

}
