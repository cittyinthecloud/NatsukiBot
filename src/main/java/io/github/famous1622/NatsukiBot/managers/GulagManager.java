package io.github.famous1622.NatsukiBot.managers;

import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.data.GulagData;
import io.github.famous1622.NatsukiBot.types.GulagState;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.entities.User;

public class GulagManager {
	private static GulagManager theOne = null;
	private GulagData gulags = GulagData.getGulagData();
	private JDA jda;
	
	public void addGulag(User user, long milliseconds) {
		if(!gulags.containsKey(user)) {
			gulags.put(user, new GulagState());
		}
		GulagState state = gulags.get(user);
		if (state.isGulaged()) {
			state.unGulagTime += milliseconds; 
		} else {
			state.unGulagTime = System.currentTimeMillis() + milliseconds;
		}
		
		sync();
	}
	
	private void sync() {
		syncRoles();
		syncDisk();
	}

	private void syncDisk() {
		gulags.saveToDisk();
	}

	public void syncRoles() {
		gulags.forEach((user, state) -> {
			Member member = jda.getGuildById(BotConfig.getGuildId()	).getMember(user);
			if (member != null) {
				Role gulagRole = member.getGuild().getRolesByName("Probationary", true).get(0);
				boolean inActualGulag = member.getRoles().contains(gulagRole);
				
				if (inActualGulag && !state.isGulaged()) {	
					member.getGuild().getController().modifyMemberRoles(member, state.roles)
													 .reason("Ungulaging member")
													 .queue();
					gulags.remove(member.getUser());
				} else if (!inActualGulag && state.isGulaged()) {
					state.roles = member.getRoles();
					member.getGuild().getController().modifyMemberRoles(member, gulagRole)
													 .reason("Gulaging member")
													 .queue();
				}
			}
		});
	}
	
	public void reload() {
		syncRoles();
	}
	public static GulagManager getManager(JDA jda) {
		if (theOne == null) {
			theOne = new GulagManager(jda);
		}
		return theOne;
	}
	
	private GulagManager(JDA jda) {
		this.jda = jda;
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
