package io.github.famous1622.NatsukiBot.eventlog.types;

public enum ActionType {
	GULAGUSER ("GulagUser"),
	UNGULAGUSER ("UngulagUser"),
	SELFASSIGN ("SelfAssign"),
	SELFUNASSIGN ("SelfUnassign"),
	SENTDM ("SentDM");
	
	private String readableName;
	
	ActionType(String readableName) {
		this.readableName = readableName;
	}

	public String getReadableName() {
		return readableName;
	}
}

