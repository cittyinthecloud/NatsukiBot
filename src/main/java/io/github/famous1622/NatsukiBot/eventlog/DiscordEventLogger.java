package io.github.famous1622.NatsukiBot.eventlog;

import java.awt.Color;
import java.time.Instant;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageEmbed;

public class DiscordEventLogger implements IEventLogger {

	public DiscordEventLogger() {
	}
	
	@Override
	public boolean logAction(Action action) {
		MessageEmbed eb = new EmbedBuilder().setColor(new Color(16711680))
				.setTitle("Action")
			    .setTimestamp(Instant.ofEpochMilli(action.getTime().getTime()))
			    .addField("Type", action.getType().getReadableName(), false)
			    .addField("Responsible", action.getResponsible(), false)
			    .addField("Target", action.getTarget(), false)
			    .addField("Arguments", action.getArguments(), false)
			    .build();
		Main.jda.getTextChannelById(BotConfig.getLogId()).sendMessage(eb).queue();
		return true;
	}

	@Override
	public boolean logOperation(Operation operation) {
		MessageEmbed eb = new EmbedBuilder().setColor(new Color(16711680))
				.setTitle("Operation")
			    .setTimestamp(Instant.ofEpochMilli(operation.getTime().getTime()))
			    .addField("Type", operation.getType().getReadableName(), false)
			    .addField("Cause", operation.getCause(), false)
			    .addField("Party", operation.getParty(), false)
			    .addField("Data", operation.getData(), false)
			    .build();
		Main.jda.getTextChannelById(BotConfig.getLogId()).sendMessage(eb).queue();
		return true;
	}

}
