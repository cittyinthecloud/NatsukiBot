package io.github.famous1622.NatsukiBot.eventlog.types;

public enum OperationType {
	RUNCOMMAND ("RunCommand"),
	REFUSECOMMAND ("RefuseCommand"),
	RECIEVEDM ("RecieveDM"),
	RESTOREROLES ("RestoreRoles"),
	STASHROLES ("StashRoles"),
	REMOVEGULAG ("RemoveGulag");
	
	private String readableName;
	
	OperationType(String readableName) {
		this.readableName = readableName;
	}

	public String getReadableName() {
		return readableName;
	}
}
