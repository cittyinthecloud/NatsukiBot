package io.github.famous1622.NatsukiBot.listeners;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import io.github.famous1622.NatsukiBot.logging.types.Action;
import io.github.famous1622.NatsukiBot.logging.types.ActionType;
import io.github.famous1622.NatsukiBot.logging.types.Operation;
import io.github.famous1622.NatsukiBot.logging.types.OperationType;
import io.github.famous1622.NatsukiBot.utils.LoggingUtils;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class PrivateMessageListener extends ListenerAdapter {
	@Override
	public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
		if (event.getAuthor().isBot()) {
			return;
		}
		Main.botLog.logOperation(new Operation(this).withType(OperationType.RECIEVEDM)
													.withParty(event.getAuthor())
													.withData(LoggingUtils.messageToLogData(event.getMessage())));
		
		if (ComedyDarkCommand.cdusers.contains(event.getAuthor()) && event.getMessage().getContentDisplay().equalsIgnoreCase("I understand the rules of the comedy-dark channel")) {
			event.getMessage().addReaction("👌").queue();
			Member member = Main.guild.getMember(event.getAuthor());
			Role cdrole = Main.guild.getRolesByName("comedy-dark", true).get(0);
			Main.guild.getController().addSingleRoleToMember(member, cdrole).queue();
			Main.botLog.logAction(new Action().withType(ActionType.SELFASSIGN)
											  .withResponsible(event.getAuthor())
											  .withSelfTarget()
											  .withArguments("comedy-dark"));
		}
	}
}
