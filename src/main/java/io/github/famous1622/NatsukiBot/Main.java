package io.github.famous1622.NatsukiBot;

import javax.security.auth.login.LoginException;

import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import io.github.famous1622.NatsukiBot.listeners.CommandListener;
import io.github.famous1622.NatsukiBot.listeners.PrivateMessageListener;
import io.github.famous1622.NatsukiBot.listeners.ServerSuggestionsListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;

public class Main {
	public static JDA jda;
	public static void main(String[] args) throws LoginException, InterruptedException {
		if (args.length<1) {
			System.out.println("Requires a token argument");
		} else {
			jda = new JDABuilder(AccountType.BOT)
					.setToken(args[0])
					.addEventListener(new CommandListener())
					.addEventListener(new PrivateMessageListener())
					.addEventListener(new ServerSuggestionsListener())
					.buildBlocking();
			CommandListener.addCommand(new ComedyDarkCommand());
		}
	}
}
