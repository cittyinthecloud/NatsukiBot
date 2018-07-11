package io.github.famous1622.NatsukiBot.config;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Properties;

public class GulagDataConfiguration extends Properties {
	
	private static final long serialVersionUID = 1L;
	
	private static GulagDataConfiguration theOne = null;
	private static final Path path = Paths.get("gulag.xml");
	
	private GulagDataConfiguration() throws IOException {
		super();
		loadFromDisk();
	}
	
	public static GulagDataConfiguration getConfig() throws IOException {
		if (theOne == null) {
			theOne = new GulagDataConfiguration();	
		}
		return theOne;
	}
	
	public void saveToDisk() throws IOException {
		FileOutputStream file = new FileOutputStream(path.toFile());
		this.storeToXML(file, null);
	}
	
	public void loadFromDisk() throws IOException {
		if (Files.exists(path)) {
			FileInputStream file = new FileInputStream(path.toFile());
			this.loadFromXML(file);
		}
	}
}
