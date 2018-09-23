package io.github.famous1622.NatsukiBot.managers;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;

import io.github.famous1622.NatsukiBot.Main;
import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.data.GulagData;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;
import io.github.famous1622.NatsukiBot.eventlog.types.OperationType;
import io.github.famous1622.NatsukiBot.types.GulagState;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.MessageBuilder;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public class GulagManager {
	private static GulagManager theOne = null;
	private GulagData gulags;
	private JDA jda;
	private Guild guild;
	private static DateTimeFormatter atTimeFormat = DateTimeFormatter.ofPattern("yyyy/MM/dd 'at' hh:mm a");
	
	public void addGulag(User user, long milliseconds, String reason) {
		if(!gulags.containsKey(user)) {
			gulags.put(user, new GulagState());
		}
		GulagState state = gulags.get(user);
		if (state.isGulaged()) {
			state.unGulagTime += milliseconds; 
		} else {
			state.unGulagTime = System.currentTimeMillis() + milliseconds;
		}
		
		state.reason = reason;
		sync();
	}
	
	public void sync() {
		syncRoles();
		syncDisk();
	}

	private void syncDisk() {
		gulags.saveToDisk();
	}

	public void syncRoles() {
		gulags.forEach((user, state) -> {
			if (user==null) {
				return;
			}
			Member member = this.guild.getMember(user);
			if (member != null) {
				Role gulagRole = this.guild.getRolesByName("Probationary", true).get(0);
				boolean inActualGulag = member.getRoles().contains(gulagRole);
				
				if (inActualGulag && !state.isGulaged()) {	
					this.guild.getController().modifyMemberRoles(member, state.roles)
													 .reason("Ungulaging member")
													 .queue();
					Main.eventLog.logOperation(new Operation(this).withType(OperationType.REMOVEGULAG)
																  .withParty(member.getUser())
																  .withData(""));
					gulags.remove(member.getUser());
							
					
				} else if (!inActualGulag && state.isGulaged()) {
					state.roles = member.getRoles();
					this.guild.getController().modifyMemberRoles(member, gulagRole)
													 .reason("Gulaging member")
													 .queue();
					
					String dateString = atTimeFormat.format(Instant.ofEpochMilli(state.unGulagTime).atOffset(ZoneOffset.UTC).toZonedDateTime());
					this.guild.getTextChannelById(BotConfig.getProbationId()).sendMessage(
							new MessageBuilder().append(member).setEmbed(
									new EmbedBuilder().setDescription("You have been gulaged until "+dateString+" UTC")
									.addField("Reason", "```"+state.reason+"```", false)
									.build()
							).build()
						).queue();
				}
			}
		});
	}
	
	public static GulagManager getManager(JDA jda) {
		if (theOne == null) {
			theOne = new GulagManager(jda);
		}
		return theOne;
	}
	
	private GulagManager(JDA jda) {
		this.gulags = GulagData.getGulagData();
		this.jda = jda;
		this.guild = this.jda.getGuildById(BotConfig.getGuildId());
	}

	public boolean removeGulag(User user) {
		if (gulags.containsKey(user)) {
			GulagState state = gulags.get(user);
			if (!state.isGulaged()) {
				return false;
			}
			state.unGulagTime=0;
			gulags.put(user, state);
			sync();
			return true;
		} else {
			return false;
		}
	}
}
