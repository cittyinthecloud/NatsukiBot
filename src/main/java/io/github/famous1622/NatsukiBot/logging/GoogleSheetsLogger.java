package io.github.famous1622.NatsukiBot.logging;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.services.sheets.v4.Sheets;
import com.google.api.services.sheets.v4.SheetsScopes;
import com.google.api.services.sheets.v4.model.Spreadsheet;
import com.google.api.services.sheets.v4.model.ValueRange;

import io.github.famous1622.NatsukiBot.config.BotConfig;
import io.github.famous1622.NatsukiBot.config.SheetsConfig;
import io.github.famous1622.NatsukiBot.logging.types.Action;
import io.github.famous1622.NatsukiBot.logging.types.Operation;

public class GoogleSheetsLogger implements ILogger {
	
	private static final JsonFactory JSON_FACTORY = JacksonFactory.getDefaultInstance();
	
	private Sheets sheets;
	private static SheetsConfig config = BotConfig.getSheetsConfig();
	
	public GoogleSheetsLogger() {
		try {
			GoogleCredential creds = GoogleCredential.fromStream(new FileInputStream("client_secret.json"))
					.createScoped(Collections.singleton(SheetsScopes.SPREADSHEETS));
			
			final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			
			sheets = new Sheets.Builder(HTTP_TRANSPORT, JSON_FACTORY, creds)
									   .setApplicationName("NatsukiBot")
									   .build();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (GeneralSecurityException e) {
			e.printStackTrace();
		}
	}

	@Override
	public boolean logAction(Action action) {
		ValueRange reqbody = new ValueRange();

		reqbody.setValues(Collections.singletonList(Arrays.asList(
							action.getTime().toString(), 
							action.getType().getReadableName(), 
							action.getResponsible(), 
							action.getTarget(), 
							action.getArguments()
						 )));
		try {
			sheets.spreadsheets().values().append(config.getSpreadsheetId(), config.getActionPageName()+"!A1", reqbody);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

	@Override
	public boolean logOperation(Operation operation) {
		ValueRange reqbody = new ValueRange();

		reqbody.setValues(Collections.singletonList(Arrays.asList(
							operation.getTime().toString(), 
							operation.getType().getReadableName(), 
							operation.getCause(), 
							operation.getParty(), 
							operation.getData()
						 )));
		try {
			sheets.spreadsheets().values().append(config.getSpreadsheetId(), config.getOperationPageName()+"!A1", reqbody);
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
		return true;
	}

}
