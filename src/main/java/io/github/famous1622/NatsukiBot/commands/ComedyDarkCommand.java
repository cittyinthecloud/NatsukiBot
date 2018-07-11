package io.github.famous1622.NatsukiBot.commands;

import java.util.HashMap;
import java.util.List;

import io.github.famous1622.NatsukiBot.CONSTANTS;
import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.entities.Emote;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.managers.GuildController;

public class ComedyDarkCommand implements Command {
	public static HashMap<String, Guild> guilds = new HashMap<String, Guild>();

	public String getCommand() {
		return "comedy-dark";
	}

	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		Guild guild = event.getGuild();
		GuildController guildController = new GuildController(guild);
		
		List<Emote> ehehes = guild.getEmotesByName("Ehehe", false);
		if (!ehehes.isEmpty()) {
			event.getMessage().addReaction(ehehes.get(0)).queue();
		}
		User author = event.getAuthor();
		Member member = guild.getMember(author);
		List<Role> roles = member.getRoles();
		Role cdrole = guild.getRolesByName("comedy-dark", true).get(0);
		if (roles.contains(cdrole)) {
			guildController.removeSingleRoleFromMember(member, cdrole).queue();
		} else {
			author.openPrivateChannel().queue((pchannel) -> {
				pchannel.sendMessage(CONSTANTS.COMEDYDARKMESSAGE).queue();
				guilds.put(author.getId(), guild);
			});
		}
	}
	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.USER;
	}
}
