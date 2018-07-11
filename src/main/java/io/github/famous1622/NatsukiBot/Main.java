package io.github.famous1622.NatsukiBot;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import io.github.famous1622.NatsukiBot.commands.DisableRoleCommand;
import io.github.famous1622.NatsukiBot.commands.GulagCommand;
import io.github.famous1622.NatsukiBot.commands.HelpCommand;
import io.github.famous1622.NatsukiBot.commands.RoleCommand;
import io.github.famous1622.NatsukiBot.commands.RoleSelfAssignToggleCommand;
import io.github.famous1622.NatsukiBot.commands.UngulagCommand;
import io.github.famous1622.NatsukiBot.listeners.CommandListener;
import io.github.famous1622.NatsukiBot.listeners.PrivateMessageListener;
import io.github.famous1622.NatsukiBot.listeners.ServerJoinListener;
import io.github.famous1622.NatsukiBot.listeners.ServerLeaveListener;
import io.github.famous1622.NatsukiBot.listeners.ServerSuggestionsListener;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
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
					.addEventListener(new ServerJoinListener())
					.addEventListener(new ServerLeaveListener())
					.buildBlocking();
			CommandListener.addCommand(new ComedyDarkCommand());
			CommandListener.addCommand(new RoleSelfAssignToggleCommand());
			CommandListener.addCommand(new RoleCommand());
			CommandListener.addCommand(new DisableRoleCommand());
			CommandListener.addCommand(new GulagCommand());
			CommandListener.addCommand(new UngulagCommand());
			
			CommandListener.addCommand(new HelpCommand());
			
			GulagManager.getManager().loadFromDisk(jda);
			
			Timer gulagTimer = new Timer(true);
			gulagTimer.scheduleAtFixedRate(new TimerTask() {
				@Override
				public void run() {
					GulagManager.getManager().syncRoles();
				}
			}, 5000, 5*60*1000);
		}
	}
}
