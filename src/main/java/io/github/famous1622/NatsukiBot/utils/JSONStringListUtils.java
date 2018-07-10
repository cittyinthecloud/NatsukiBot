package io.github.famous1622.NatsukiBot.utils;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;

public class JSONStringListUtils {
	public static String packList(List<String> list) {
		return new JSONArray(list).toString();
	}
	
	public static List<String> unpackList(String packed){
		JSONArray arr = new JSONArray(packed);
		List<String> out = new ArrayList<String>(arr.length());
		for (Object object : arr) {
			out.add((String) object);
		}
		return out;
	}
}
