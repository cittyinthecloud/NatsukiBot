package io.github.famous1622.NatsukiBot.utils;

import java.util.List;
import java.util.stream.Collectors;

import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.Role;

public class LoggingUtils {
	public static String messageToLogData(Message message) {
		StringBuilder data = new StringBuilder();
		data.append(message.getContentRaw());
		if (!message.getAttachments().isEmpty()) {
			data.append(" ||| ATTACHMENTS FOLLOW");
			message.getAttachments().forEach((attachment) -> {
				data.append(" ||| ").append(attachment.getUrl());
			});
			data.append(" |||");
		}
		return data.toString();
	}
	
	public static String roleListToLogData(List<Role> roles) {
		return String.join(",", roles.stream().map((role) -> role.getName()).collect(Collectors.toList()));
	}
}
