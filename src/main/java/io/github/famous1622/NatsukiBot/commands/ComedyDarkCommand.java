package io.github.famous1622.NatsukiBot.commands;

import java.util.ArrayList;
import java.util.List;

import io.github.famous1622.NatsukiBot.CONSTANTS;
import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class ComedyDarkCommand implements Command {
	public static List<User> cdusers = new ArrayList<User>();

	public String getCommand() {
		return "comedy-dark";
	}

	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		Guild guild = Main.guild;
		GuildController guildController = new GuildController(guild);
		event.getMessage().addReaction(event.getJDA().getEmoteById(BotConfig.getEheheId())).queue();
		User author = event.getAuthor();
		Member member = guild.getMember(author);
		List<Role> roles = member.getRoles();
		Role cdrole = guild.getRolesByName("comedy-dark", true).get(0);
		if (roles.contains(cdrole)) {
			guildController.removeSingleRoleFromMember(member, cdrole).queue();
		} else {
			author.openPrivateChannel().queue((pchannel) -> {
				pchannel.sendMessage(CONSTANTS.COMEDYDARKMESSAGE).queue();
				cdusers.add(author);
			});
		}
	}
	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.USER;
	}

	@Override
	public String getHelpMessage() {
		return "toggles comedy-dark permissions";
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}
}
