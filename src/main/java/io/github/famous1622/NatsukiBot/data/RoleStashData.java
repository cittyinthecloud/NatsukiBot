package io.github.famous1622.NatsukiBot.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

import io.github.famous1622.NatsukiBot.Main;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public class RoleStashData {
	
	private transient static RoleStashData theOne = null;
	private static final String path = "rrconfig.json";
	
	private transient Map<String,List<Role>> stash = new HashMap<String,List<Role>>();

	private JDA jda;
	
	private RoleStashData(JDA jda) {
		super();
		this.jda = jda;
		loadFromDisk();
	}
	
	public static RoleStashData getConfig(JDA jda) {
		if (theOne == null) {
			theOne = new RoleStashData(jda);	
		}
		return theOne;
	}
	
	public void saveToDisk() {
		JsonArray stashData = new JsonArray();
		 stash.forEach((user,rolelist) -> {
			JsonObject entryObj = new JsonObject();
			JsonArray JSON_rolelist = new JsonArray();
			rolelist.forEach(role -> JSON_rolelist.add(role.getId()));
			entryObj.addProperty("user", user);
			entryObj.add("rolelist", JSON_rolelist);
			stashData.add(entryObj);
		});
		try (Writer writer = new FileWriter(path)){
			Main.gson.toJson(stashData,writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromDisk() {
		try (JsonReader reader = new JsonReader(new FileReader(path))) {
			this.stash.clear();
			JsonArray stashData = new JsonParser().parse(reader).getAsJsonArray();
			stashData.forEach((entryElem) -> {
				JsonObject entryObj = entryElem.getAsJsonObject();
				String user = entryObj.getAsJsonPrimitive("user").getAsString();
				JsonArray JSON_rolelist = entryObj.getAsJsonArray("rolelist");
				List<Role> rolelist = new ArrayList<Role>();
				JSON_rolelist.forEach((roleId) -> rolelist.add(jda.getRoleById(roleId.getAsString())));
				this.stash.put(user, rolelist);
			});
		} catch (FileNotFoundException e) {
			System.out.println("Role stash not found");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void put(User user, List<Role> roles) {
		this.stash.put(user.getId(), roles);
		saveToDisk();
	}

	public List<Role> get(User user) {
		return this.stash.get(user.getId());
	}

	public boolean containsKey(User user) {
		return this.stash.containsKey(user.getId());
	}
	
	public void remove(User user) {
		this.stash.remove(user.getId());
		saveToDisk();
	}
}
