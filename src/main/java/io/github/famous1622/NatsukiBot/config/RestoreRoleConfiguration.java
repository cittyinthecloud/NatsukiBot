package io.github.famous1622.NatsukiBot.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class RestoreRoleConfiguration extends Properties {
	
	private static final long serialVersionUID = 1L;
	
	private static RestoreRoleConfiguration theOne = null;
	private static final Path rolesPath = Paths.get("rrconfig.xml");
	
	private RestoreRoleConfiguration() throws IOException {
		super();
		loadFromDisk();
	}
	
	public static RestoreRoleConfiguration getConfig() throws IOException {
		if (theOne == null) {
			theOne = new RestoreRoleConfiguration();	
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
