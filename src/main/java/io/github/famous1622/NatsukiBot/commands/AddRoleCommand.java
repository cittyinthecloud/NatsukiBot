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
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class AddRoleCommand implements Command {

	@Override
	public String getCommand() {
		return "addrole";
	}

	@Override
	public void onCommand(MessageReceivedEvent event) {
		List<String> arguments = new ArrayList<String>(); 
		arguments.addAll(Arrays.asList(event.getMessage().getContentRaw().split(" ")));
		arguments.remove(0);
		String arg = String.join(" ", arguments);
		Guild guild = event.getGuild();
		List<Role> roles = guild.getRolesByName(arg, true);
		if (!roles.isEmpty()) {
			Role role = roles.get(0);
			try {
				RoleConfiguration roleConfig = RoleConfiguration.getConfig();
				
				roleConfig.setProperty(arg.toLowerCase(), role.getId());
				roleConfig.saveToDisk();
				event.getChannel().sendMessage("Added role: "+role.getName()).queue((message) -> {
					message.delete().queueAfter(10000, TimeUnit.MILLISECONDS);
				});
			} catch (IOException e) {
				event.getChannel().sendMessage("Help I'm a potato").queue();
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.ADMIN;
	}

}
