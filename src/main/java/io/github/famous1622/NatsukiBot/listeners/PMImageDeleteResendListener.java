package io.github.famous1622.NatsukiBot.listeners;

import java.io.IOException;
import java.util.HashMap;

import io.github.famous1622.NatsukiBot.utils.TempFileCache;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageDeleteEvent;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PMImageDeleteResendListener extends ListenerAdapter {
	private static TempFileCache cache = new TempFileCache(5);
	private static HashMap<String, String> nameCache = new HashMap<String, String>();
	
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (!event.getAuthor().isBot() && !event.getMessage().getAttachments().isEmpty()) {
			try {
				cache.addAttachment(event.getMessageId(), event.getMessage().getAttachments().get(0));
				nameCache.put(event.getMessageId(), event.getMessage().getAttachments().get(0).getFileName());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onPrivateMessageDelete(PrivateMessageDeleteEvent event) {
		if (cache.containsKey(event.getMessageId())) {
			event.getChannel().sendFile(cache.get(event.getMessageId()), nameCache.get(event.getMessageId()), new MessageBuilder().setContent(":eye:").build()).queue();
			cache.remove(event.getMessageId());
		}
	}
	
}
