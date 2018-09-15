package io.github.famous1622.NatsukiBot.commands;

import java.util.List;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class RoleInfoCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.MOD;
	}

	@Override
	public String getCommand() {
		return "roleinfo";
	}

	@Override
	public String getHelpMessage() {
		return "gets a bunch of info from a role id.";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		if (arguments.size() == 0) {
			event.getChannel().sendMessage("This command requires a role name as an argument.").queue();
		} else {
			String roleName = String.join(" ", arguments);
			try {
				Role role = Main.guild.getRolesByName(roleName, true).get(0);
				
				event.getChannel().sendMessage(new EmbedBuilder().setTitle("Role Info")
																 .addField("Name", role.getName(), false)
																 .addField("ID", role.getId(), false)
																 .addField("Color", Integer.toString(role.getColorRaw()), false)
																 .build()).queue();;
			} catch (IndexOutOfBoundsException e) {
				event.getChannel().sendMessage("Role " + roleName + " does not exist.").queue();
			}
		}
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}

}
