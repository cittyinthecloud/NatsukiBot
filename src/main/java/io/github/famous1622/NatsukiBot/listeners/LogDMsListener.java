package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.ActionType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class LogDMsListener extends ListenerAdapter {
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		Main.eventLog.logAction(new Action().withType(ActionType.SENTDM)
											.withResponsible(event.getAuthor())
											.withTarget("")
											.withArguments(event.getMessage().getContentDisplay()));
		
	}
}
