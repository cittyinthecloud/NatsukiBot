package io.github.famous1622.NatsukiBot.utils;

import java.io.File;
import java.io.IOException;
import java.util.LinkedHashMap;

import net.dv8tion.jda.core.entities.Message.Attachment;

public class TempFileCache extends LinkedHashMap<String, File> {

	private static final long serialVersionUID = -5313814122801109822L;
	
	private int max;
	
	public TempFileCache(int max) {
		super();
		this.max=max;
	}
	
	@Override
	protected boolean removeEldestEntry(java.util.Map.Entry<String, File> eldest) {
		return size() > max;
	}
	
	@Override
	public File remove(Object key) {
		File ret = super.remove(key);
		ret.delete();
		return ret;
	}
	
	public File addAttachment(String key, Attachment value) throws IOException {
		File file = new File(getUniqueFileName());
		value.download(file);
		file.deleteOnExit();
		System.out.println(size());
		return put(key, file);
	}
	
	private static String getUniqueFileName() {
		try {
			File f = File.createTempFile("prefix",null);
			String ret = f.getPath();
			f.delete();
			return ret;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
}
