package io.github.famous1622.NatsukiBot;

import java.util.HashMap;

import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class Constants {
	public static HashMap<String, MessageEmbed> embeds;
			
	static {
		embeds = new HashMap<>();	
		embeds.put("Observer", new EmbedBuilder().setDescription("Remember to follow the rules, and enjoy your stay").build());
	}
}
