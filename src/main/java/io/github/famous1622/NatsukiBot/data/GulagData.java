package io.github.famous1622.NatsukiBot.data;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.Writer;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonPrimitive;
import com.google.gson.stream.JsonReader;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.types.GulagState;
import net.dv8tion.jda.core.entities.User;

public class GulagData implements Map<User, GulagState> {
	
	private transient Map<User, GulagState> map = new HashMap<User, GulagState>();
	
	private transient static GulagData theOne = null;
	private transient static final String path = "gulag.json";
	
	private GulagData() {
		super();
		loadFromDisk();
	}
	
	public static GulagData getGulagData() {
		if (theOne == null) {
			theOne = new GulagData();	
		}
		return theOne;
	}
	
	public void saveToDisk() {
		JsonArray gulagData = new JsonArray();
		this.forEach((user,state) -> {
			JsonObject itemObj = new JsonObject();
			itemObj.addProperty("user", user.getId());
			itemObj.add("state", Main.gson.toJsonTree(state));
			gulagData.add(itemObj);
		});
		System.out.println(gulagData.toString());
		try (Writer writer = new FileWriter(path)){
			Main.gson.toJson(gulagData,writer);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void loadFromDisk(){
		try (JsonReader reader = new JsonReader(new FileReader(path))) {
			JsonArray gulagData = new JsonParser().parse(reader).getAsJsonArray();
			map.clear();
			gulagData.forEach((gulagElem) -> {
				JsonObject gulagObj = gulagElem.getAsJsonObject();
				JsonPrimitive JSON_user = gulagObj.getAsJsonPrimitive("user");
				JsonObject JSON_state = gulagObj.getAsJsonObject("state");
				map.put(Main.gson.fromJson(JSON_user, User.class), 
						Main.gson.fromJson(JSON_state, GulagState.class));
			});
		} catch (FileNotFoundException e) {
			map.clear();
			saveToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void clear() {
		map.clear();
		saveToDisk();
	}

	@Override
	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	@Override
	public boolean containsValue(Object value) {
		return map.containsValue(value);
	}

	@Override
	public Set<Entry<User, GulagState>> entrySet() {
		return map.entrySet();
	}

	@Override
	public GulagState get(Object key) {
		return map.get(key);
	}

	@Override
	public boolean isEmpty() {
		return map.isEmpty();
	}

	@Override
	public Set<User> keySet() {
		return null;
	}

	@Override
	public GulagState put(User key, GulagState value) {
		GulagState retValue = map.put(key, value);
		saveToDisk();
		return retValue;
	}

	@Override
	public void putAll(Map<? extends User, ? extends GulagState> m) {
		map.putAll(m);
		saveToDisk();
	}

	@Override
	public GulagState remove(Object key) {
		GulagState retValue = map.remove(key);
		return retValue;
	}

	@Override
	public int size() {
		return map.size();
	}

	@Override
	public Collection<GulagState> values() {
		return map.values();
	}
}
