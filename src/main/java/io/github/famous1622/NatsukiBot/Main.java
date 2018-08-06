package io.github.famous1622.NatsukiBot;

import java.util.Timer;
import java.util.TimerTask;

import javax.security.auth.login.LoginException;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import io.github.famous1622.NatsukiBot.commands.ComedyDarkCommand;
import io.github.famous1622.NatsukiBot.commands.DisableRoleCommand;
import io.github.famous1622.NatsukiBot.commands.GulagCommand;
import io.github.famous1622.NatsukiBot.commands.HelpCommand;
import io.github.famous1622.NatsukiBot.commands.RoleCommand;
import io.github.famous1622.NatsukiBot.commands.RoleSelfAssignToggleCommand;
import io.github.famous1622.NatsukiBot.commands.UngulagCommand;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.listeners.CommandListener;
import io.github.famous1622.NatsukiBot.listeners.PrivateMessageListener;
import io.github.famous1622.NatsukiBot.listeners.ServerJoinListener;
import io.github.famous1622.NatsukiBot.listeners.ServerLeaveListener;
import io.github.famous1622.NatsukiBot.managers.GulagManager;
import io.github.famous1622.NatsukiBot.types.GulagState;
import io.github.famous1622.NatsukiBot.utils.GulagStateSerializer;
import io.github.famous1622.NatsukiBot.utils.UserSerializer;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.entities.Game;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;

public class Main {
	public static JDA jda;
	
	public static Gson gson;
	
	public static Guild guild;
	
	public static void main(String[] args) throws LoginException, InterruptedException {
//		if (args.length<1) {
//			System.out.println("Requires a token argument");
//		} else {
		jda = new JDABuilder(AccountType.BOT)
				.setToken(BotConfig.getToken())
				.addEventListener(new CommandListener())
				.addEventListener(new PrivateMessageListener())
				//.addEventListener(new ServerSuggestionsListener())
				.addEventListener(new ServerJoinListener())
				.addEventListener(new ServerLeaveListener())
				.buildBlocking();
		
		gson = new GsonBuilder().setPrettyPrinting()
		 .registerTypeAdapter(User.class, new UserSerializer(jda))
		 .registerTypeAdapter(GulagState.class, new GulagStateSerializer(jda))
		 .create();
		
		CommandListener.addCommand(new ComedyDarkCommand());
		CommandListener.addCommand(new RoleSelfAssignToggleCommand());
		CommandListener.addCommand(new RoleCommand());
		CommandListener.addCommand(new DisableRoleCommand());
		CommandListener.addCommand(new GulagCommand());
		CommandListener.addCommand(new UngulagCommand());
		CommandListener.addCommand(new HelpCommand());
				
		GulagManager.getManager(jda).reload();
		
		Timer gulagTimer = new Timer(true);
		gulagTimer.scheduleAtFixedRate(new TimerTask() {
			@Override
			public void run() {
				GulagManager.getManager(jda).syncRoles();
			}
		}, 5000, 5*60*1000);
		
		guild = jda.getGuildById(BotConfig.getGuildId());
		
		
		
		jda.getPresence().setGame(Game.watching("Doki Doki Modding Club!"));
	}
	//}
}
