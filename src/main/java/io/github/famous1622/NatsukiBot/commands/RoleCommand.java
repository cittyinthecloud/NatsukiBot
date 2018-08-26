package io.github.famous1622.NatsukiBot.commands;

import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.data.SelfAssignableRolesData;
import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.ActionType;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RoleCommand implements Command {
	public static boolean commandDisabled = false;

	@Override
	public String getCommand() {
		return "iam";
	}
	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		String arg = String.join(" ", arguments);
		Guild guild = Main.guild;
		Member member = guild.getMember(event.getAuthor());
		SelfAssignableRolesData roleData = SelfAssignableRolesData.getConfig(guild.getJDA());
		if (roleData.containsKey(arg.toLowerCase())) {
			Role role = roleData.get(arg.toLowerCase());
			if (member.getRoles().contains(role)) {
				guild.getController().removeSingleRoleFromMember(member,role)
								     .reason("Role self-unassigned")
									 .queue();
				Main.eventLog.logAction(new Action().withType(ActionType.SELFUNASSIGN)
												  .withResponsible(event.getAuthor())
												  .withSelfTarget()
												  .withArguments(arg));
				event.getMessage().delete().queue();
				event.getChannel().sendMessage("Removed role: "+role.getName()).queue((message) -> {
					message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
				});
			} else {
				guild.getController().addSingleRoleToMember(member,role)
									 .reason("Role self-assigned")
									 .queue();
				Main.eventLog.logAction(new Action().withType(ActionType.SELFASSIGN)
												  .withResponsible(event.getAuthor())
												  .withSelfTarget()
												  .withArguments(arg));
				if(event.isFromType(ChannelType.TEXT)) {
					event.getMessage().delete().queue();
				}
				event.getChannel().sendMessage("Added role: "+role.getName()).queue((message) -> {
					message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
				});
			}
		}

	}

	@Override
	public PrivilegeLevel getRequiredLevel() {
		if (commandDisabled) {
			return PrivilegeLevel.ADMIN;
		}
		return PrivilegeLevel.USER;
	}

	@Override
	public String getHelpMessage() {
		return "assigns the user a role. Syntax: $iam [role]";
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}
}
