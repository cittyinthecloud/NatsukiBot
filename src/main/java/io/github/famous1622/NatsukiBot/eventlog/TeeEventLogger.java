package io.github.famous1622.NatsukiBot.eventlog;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import io.github.famous1622.NatsukiBot.eventlog.types.Action;
import io.github.famous1622.NatsukiBot.eventlog.types.Operation;

public class TeeEventLogger implements IEventLogger {
	
	public List<IEventLogger> loggers;

	public TeeEventLogger(IEventLogger... eventLoggers ) {
		loggers = Arrays.asList(eventLoggers);
	}
	
	@Override
	public boolean logAction(Action action) {
		loggers.forEach(logger -> logger.logAction(action));
		return true;
	}

	@Override
	public boolean logOperation(Operation operation) {
		loggers.forEach(logger -> logger.logOperation(operation));
		return true;
	}
}
