package io.github.famous1622.NatsukiBot.eventlog;

import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;

public interface IEventLogger {
	public boolean logAction(Action action);
	public boolean logOperation(Operation operation);
}
