package io.github.famous1622.NatsukiBot.utils;

import java.lang.reflect.Type;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;

import io.github.famous1622.NatsukiBot.types.GulagState;
import net.dv8tion.jda.core.JDA;

public class GulagStateSerializer implements JsonSerializer<GulagState>, JsonDeserializer<GulagState>  {

	private JDA jda;

	public GulagStateSerializer(JDA jda) {
		this.jda = jda;
	}

	@Override
	public JsonElement serialize(GulagState gulag, Type arg1, JsonSerializationContext arg2) {
		JsonObject output = new JsonObject();
		JsonArray roles = new JsonArray();
		gulag.roles.forEach((role) -> roles.add(role.getId()));
		output.add("roles", roles);
		output.addProperty("unGulagTime", gulag.unGulagTime);
		return output;
	}

	@Override
	public GulagState deserialize(JsonElement elem, Type arg1, JsonDeserializationContext arg2)
			throws JsonParseException {
		GulagState gulag = new GulagState();
		
		JsonObject gulagObj = elem.getAsJsonObject();
		gulag.unGulagTime = gulagObj.get("unGulagTime").getAsLong();
		
		gulagObj.getAsJsonArray("roles").forEach((roleId) -> {
			gulag.roles.add(jda.getRoleById(roleId.getAsString()));
		});
		
		return gulag;
	}

}
