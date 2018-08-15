package io.github.famous1622.NatsukiBot.config;

public class SheetsConfig {

	protected SheetsConfig(String spreadsheetId, String actionPageName, String operationPageName) {
		this.spreadsheetId = spreadsheetId;
		this.actionPageName = actionPageName;
		this.operationPageName = operationPageName;
	}

	private String spreadsheetId;
	private String actionPageName;
	private String operationPageName;

	public String getSpreadsheetId() {
		return spreadsheetId;
	}

	public String getActionPageName() {
		return actionPageName;
	}

	public String getOperationPageName() {
		return operationPageName;
	}
	
}