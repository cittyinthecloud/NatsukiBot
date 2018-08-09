package io.github.famous1622.NatsukiBot.logging;

import io.github.famous1622.NatsukiBot.logging.types.Action;
import io.github.famous1622.NatsukiBot.logging.types.Operation;

public interface ILogger {
	public boolean logAction(Action action);
	public boolean logOperation(Operation operation);
}
