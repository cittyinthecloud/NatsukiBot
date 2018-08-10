package io.github.famous1622.NatsukiBot.logging.types;

import java.util.Date;

import io.github.famous1622.NatsukiBot.utils.BotUtils;
import net.dv8tion.jda.core.entities.User;

public class Operation {
	private Date time;
	private OperationType type;
	private String cause;
	private String party;
	private String data;

	public Operation(Object cause) {
		this.time = new Date();
		this.withCause(cause.getClass());
	}

	public Date getTime() {
		return time;
	}
	
	public String getCause() {
		return cause;
	}

	public String getData() {
		return data;
	}

	public String getParty() {
		return party;
	}

	public OperationType getType() {
		return type;
	}
	
	public Operation withCause(Class<?> cause) {
		return withCause(cause.getSimpleName());
	}
	
	public Operation withCause(String cause) {
		this.cause = cause;
		return this;
	}

	public Operation withData(String data) {
		this.data = data;
		return this;
	}
	
	public Operation withParty(String party) {
		this.party = party;
		return this;
	}
	
	public Operation withParty(User party) {
		return withParty(BotUtils.getDiscordTag(party));
	}

	public Operation withStateParty() {
		return withParty("(BotState)");
	}

	public Operation withType(OperationType type) {
		this.type = type;
		return this;
	}
}
