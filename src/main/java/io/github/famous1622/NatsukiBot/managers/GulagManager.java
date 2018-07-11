package io.github.famous1622.NatsukiBot.managers;

import java.io.IOException;
import java.util.HashMap;

import io.github.famous1622.NatsukiBot.config.GulagDataConfiguration;
import io.github.famous1622.NatsukiBot.types.GulagState;
import io.github.famous1622.NatsukiBot.utils.JSONUtils;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.Role;

public class GulagManager {
	private static GulagManager theOne = new GulagManager();
	private HashMap<Member,GulagState> gulags = new HashMap<Member, GulagState>();
	
	public void addGulag(Member member, long milliseconds) {
		if(!gulags.containsKey(member)) {
			gulags.put(member, new GulagState());
		}
		GulagState state = gulags.get(member);
		state.timesInGulag++;
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
		try {
			GulagDataConfiguration datafile = GulagDataConfiguration.getConfig();
			gulags.forEach((member, state) -> {
				datafile.setProperty(JSONUtils.packMember(member), state.packToString());
				System.out.println(member.getEffectiveName() + ":" + state.packToString());
			});
			datafile.saveToDisk();
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	public void syncRoles() {
		gulags.forEach((member, state) -> {
			if (member != null) {
				Role gulagRole = member.getGuild().getRolesByName("Probationary", true).get(0);
				boolean inActualGulag = member.getRoles().contains(gulagRole);
				
				if (inActualGulag && !state.isGulaged()) {	
					member.getGuild().getController().modifyMemberRoles(member, state.roles).queue();
				} else if (!inActualGulag && state.isGulaged()) {
					state.roles = member.getRoles();
					member.getGuild().getController().modifyMemberRoles(member, gulagRole).queue();
				}
			}
		});
	}
	
	public void reload(JDA jda) {
		try {
			GulagDataConfiguration datafile = GulagDataConfiguration.getConfig();
			datafile.forEach((key,value) -> {
				Member member = JSONUtils.unpackMember(jda, (String) key);
				GulagState state = GulagState.unpack(jda, (String) value);
				gulags.put(member, state);
			});
		} catch (IOException e) {
			e.printStackTrace();
		}
		syncRoles();
	}
	public static GulagManager getManager() {
		return theOne;
	}
	
	private GulagManager() {
	}

	public boolean removeGulag(Member member) {
		if (gulags.containsKey(member)) {
			GulagState state = gulags.get(member);
			if (!state.isGulaged()) {
				return false;
			}
			state.timesInGulag--;
			state.unGulagTime=0;
			gulags.put(member, state);
			sync();
			return true;
		} else {
			return false;
		}
	}
}
