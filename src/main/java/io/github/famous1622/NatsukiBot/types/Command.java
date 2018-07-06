package io.github.famous1622.NatsukiBot.types;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface Command {
	public PrivilegeLevel getRequiredLevel();
	public String getCommand();
	public void onCommand(MessageReceivedEvent event);
}
