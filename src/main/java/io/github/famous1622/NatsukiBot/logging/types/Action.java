package io.github.famous1622.NatsukiBot.logging.types;

import java.util.Date;

import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.User;

public class Action {
	private Date time;
	private ActionType type;
	private String responsible;
	private String target;
	private String arguments;
	
	public Action() {
		this.time = new Date();
	}

	public ActionType getType() {
		return type;
	}
	
	public String getResponsible() {
		return responsible;
	}

	public String getTarget() {
		return target;
	}

	public String getArguments() {
		return arguments;
	}
	
	public Action withType(ActionType type) {
		this.type = type;
		return this;
	}

	public Action withResponsible(String responsible) {
		this.responsible = responsible;
		return this;
	}
	
	public Action withResponsible(User user) {
		this.responsible = BotUtils.getDiscordTag(user);
		return this;
	}
	
	public Action withBotResponsible() {
		return withResponsible("(NatsukiBot)");
	}
	
	public Action withTarget(String target) {
		this.target = target;
		return this;
	}
	
	public Action withTarget(User user) {
		this.target = BotUtils.getDiscordTag(user);
		return this;
	}
	
	public Action withSelfTarget() {
		return withTarget("(Self)");
	}
	
	public Action withArguments(String arguments) {
		this.arguments = arguments;
		return this;
	}
}
