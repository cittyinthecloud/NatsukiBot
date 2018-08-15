package io.github.famous1622.NatsukiBot.logging;

import io.github.famous1622.NatsukiBot.logging.types.Action;
import io.github.famous1622.NatsukiBot.logging.types.Operation;

public class ConsoleLogger implements ILogger {

	@Override
	public boolean logAction(Action action) {
		System.out.println("----------ACTION----------");
		System.out.println("Type: " + action.getType().getReadableName());
		System.out.println("Responsible: " + action.getResponsible());
		System.out.println("Target: " + action.getTarget());
		System.out.println("Args: " + action.getArguments());
		System.out.println("-------------------------");
		return true;
	}

	@Override
	public boolean logOperation(Operation operation) {
		System.out.println("-------OPERATION--------");
		System.out.println("Type: " + operation.getType().getReadableName());
		System.out.println("Cause: " + operation.getCause());
		System.out.println("Party: " + operation.getParty());
		System.out.println("Data: " + operation.getData());
		System.out.println("-------------------------");
		return true;
	}

}
