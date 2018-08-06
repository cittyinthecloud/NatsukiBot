package io.github.famous1622.NatsukiBot.types;

import java.util.List;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
	public PrivilegeLevel getRequiredLevel();
	public String getCommand();
	public String getHelpMessage();
	public void onCommand(MessageReceivedEvent event, List<String> arguments);
	public boolean mustBePublic();
}