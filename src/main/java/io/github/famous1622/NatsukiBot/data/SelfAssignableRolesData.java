package io.github.famous1622.NatsukiBot.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.stream.JsonReader;

import io.github.famous1622.NatsukiBot.Main;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Role;

public class SelfAssignableRolesData implements Map<String, Role> {
	private transient static SelfAssignableRolesData theOne = null;
	private static final String rolesPath = "roles.json";
	
	private transient Map<String, Role> roles = new HashMap<String, Role>();

	private SelfAssignableRolesData() {
		loadFromDisk();
	}
	
	public static SelfAssignableRolesData getConfig(JDA jda) {
		if (theOne == null) {
			theOne = new SelfAssignableRolesData();	
		}
		return theOne;
	}

	
	public void saveToDisk(){
		JsonObject roleObj = new JsonObject();
		roles.forEach((name, role) -> {
			roleObj.addProperty(name, role.getId());
		});
		try (Writer writer = new FileWriter(rolesPath)){
			Main.gson.toJson(roleObj,writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromDisk() {
		if (Files.exists(Paths.get(rolesPath))) {
			try (JsonReader reader = new JsonReader(new FileReader(rolesPath))) {
				JsonObject JSON_roles = new JsonParser().parse(reader).getAsJsonObject();
				roles.clear();
				JSON_roles.entrySet().forEach((entry) -> {
					roles.put(entry.getKey(), Main.guild.getRoleById(entry.getValue().getAsString()));
					System.out.println(roles.toString());
				});
			} catch (FileNotFoundException e) {
				System.out.println("No roles file :shrugika:");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void clear() {
		roles.clear();
		saveToDisk();
	}

	@Override
	public boolean containsKey(Object key) {
		return roles.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return roles.containsValue(value);
	}

	@Override
	public Set<Entry<String, Role>> entrySet() {
		return roles.entrySet();
	}

	@Override
	public Role get(Object arg0) {
		return roles.get(arg0);
	}

	@Override
	public boolean isEmpty() {
		return roles.isEmpty();
	}

	@Override
	public Set<String> keySet() {
		return roles.keySet();
	}

	@Override
	public Role put(String arg0, Role arg1) {
		Role retval = roles.put(arg0, arg1);
		saveToDisk();
		return retval;
	}

	@Override
	public void putAll(Map<? extends String, ? extends Role> arg0) {
		roles.putAll(arg0);
	}

	@Override
	public Role remove(Object arg0) {
		Role retval = roles.remove(arg0);
		saveToDisk();
		return retval;
	}

	@Override
	public int size() {
		return roles.size();
	}

	@Override
	public Collection<Role> values() {
		return roles.values();
	}
}