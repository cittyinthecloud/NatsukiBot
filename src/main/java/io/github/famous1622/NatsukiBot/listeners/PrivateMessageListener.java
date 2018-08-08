package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateMessageListener extends ListenerAdapter {
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (ComedyDarkCommand.cdusers.contains(event.getAuthor()) && event.getMessage().getContentDisplay().equalsIgnoreCase("I understand the rules of the comedy-dark channel")) {
			event.getMessage().addReaction("👌").queue();
			Member member = Main.guild.getMember(event.getAuthor());
			Role cdrole = Main.guild.getRolesByName("comedy-dark", true).get(0);
			Main.guild.getController().addSingleRoleToMember(member, cdrole).queue();
		}
	}
}
