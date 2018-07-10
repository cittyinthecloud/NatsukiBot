package io.github.famous1622.NatsukiBot.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@SuppressWarnings("serial")
public class IamConfiguration extends Properties {
	private static IamConfiguration theOne = null;
	private static final Path rolesPath = Paths.get("roles.xml");
	
	private IamConfiguration() throws IOException {
		super();
		loadFromDisk();
	}
	
	public static IamConfiguration getConfig() throws IOException {
		if (theOne == null) {
			theOne = new IamConfiguration();	
		}
		return theOne;
	}
	
	public void saveToDisk() throws IOException {
		FileOutputStream file = new FileOutputStream(rolesPath.toFile());
		this.storeToXML(file, null);
	}
	
	public void loadFromDisk() throws IOException {
		if (Files.exists(rolesPath)) {
			FileInputStream file = new FileInputStream(rolesPath.toFile());
			this.loadFromXML(file);
		}
	}
}