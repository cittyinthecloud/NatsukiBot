package io.github.famous1622.NatsukiBot.commands;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.List;

import io.github.famous1622.NatsukiBot.types.Command;
import io.github.famous1622.NatsukiBot.types.PrivilegeLevel;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class IPCommand implements Command {

	@Override
	public PrivilegeLevel getRequiredLevel() {
		return PrivilegeLevel.ADMIN;
	}

	@Override
	public String getCommand() {
		return "ip";
	}

	@Override
	public String getHelpMessage() {
		return "Gives you my IP, perv! :Hmph:";
	}

	@Override
	public void onCommand(MessageReceivedEvent event, List<String> arguments) {
		URL whatismyip;
		BufferedReader in = null;
		try {
			whatismyip = new URL("http://checkip.amazonaws.com");
			in = new BufferedReader(new InputStreamReader(
	                whatismyip.openStream()));
			final BufferedReader in2 = in;
			event.getAuthor().openPrivateChannel().queue(pchannel -> {
				try {
					pchannel.sendMessage(in2.readLine()).queue();
				} catch (IOException e) {
					pchannel.sendMessage("Something went wrong grabbing the IP, sorry!").queue();
				}
			});
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (in != null) {
				try {
					in.close();
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
	}

	@Override
	public boolean mustBePublic() {
		return false;
	}

}
