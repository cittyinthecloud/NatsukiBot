package io.github.famous1622.NatsukiBot.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

@SuppressWarnings("serial")
public class RoleConfiguration extends Properties {
	private static RoleConfiguration theOne = null;
	private static final Path rolesPath = Paths.get("roles.xml");
	
	private RoleConfiguration() throws IOException {
		super();
		loadFromDisk();
	}
	
	public static RoleConfiguration getConfig() throws IOException {
		if (theOne == null) {
			theOne = new RoleConfiguration();	
		}
		return theOne;
	}
	
	public RoleConfiguration saveToDisk() throws IOException {
		FileOutputStream file = new FileOutputStream(rolesPath.toFile());
		this.storeToXML(file, null);
		return this;
	}
	
	public RoleConfiguration loadFromDisk() throws IOException {
		if (Files.exists(rolesPath)) {
			FileInputStream file = new FileInputStream(rolesPath.toFile());
			this.loadFromXML(file);
		}
		return this;
	}
}