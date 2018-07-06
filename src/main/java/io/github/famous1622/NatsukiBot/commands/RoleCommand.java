package io.github.famous1622.NatsukiBot.commands;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.TimeUnit;

import io.github.famous1622.NatsukiBot.config.RoleConfiguration;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
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
	public void onCommand(MessageReceivedEvent event) {
		List<String> arguments = new ArrayList<String>(); 
		arguments.addAll(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
		arguments.remove(0);
		String arg = String.join(" ", arguments);
		Guild guild = event.getGuild();
		Member member = guild.getMember(event.getAuthor());
		try {
			RoleConfiguration roleConfig = RoleConfiguration.getConfig();
			if (roleConfig.containsKey(arg.toLowerCase())) {
				Role role = guild.getRoleById(roleConfig.getProperty(arg.toLowerCase()));
				if (member.getRoles().contains(role)) {
					guild.getController().removeSingleRoleFromMember(member,role).queue();
					event.getMessage().delete().queue();
					event.getChannel().sendMessage("Removed role: "+role.getName()).queue((message) -> {
						message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
					});
				} else {
					guild.getController().addSingleRoleToMember(member,role).queue();
					event.getMessage().delete().queue();
					event.getChannel().sendMessage("Added role: "+role.getName()).queue((message) -> {
						message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
					});
				}
			}
		} catch (IOException e) {
			event.getChannel().sendMessage("Help I'm a potato").queue();
			e.printStackTrace();
		}

	}

	@Override
	public PrivilegeLevel getRequiredLevel() {
		if (commandDisabled) {
			return PrivilegeLevel.ADMIN;
		}
		return PrivilegeLevel.USER;
	}
}
