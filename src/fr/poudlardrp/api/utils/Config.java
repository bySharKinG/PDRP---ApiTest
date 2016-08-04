package fr.poudlardrp.api.utils;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;

public class Config extends YamlConfiguration {
	private final File file;
	
	public Config(File file) throws FileNotFoundException, IOException, InvalidConfigurationException {
		this.file = file;
		this.load(file);
		save();
	}
	
	public void save() {
		try {
			if (!this.file.exists()) this.file.createNewFile();
			this.save(this.file);
		} catch (IOException e) {
		}
	}
	
	public void setC(String arg1, Object arg2) {
		set(arg1, arg2);
		save();
	}
	
	public int getCInt(String string) {
		int path = 0;
		int temp;
		String check;
		try {
			temp = this.getInt(string);
			check = "" + temp;
			if (!check.equals(null))
				path = temp;
		} catch (Exception e) {
		}
		return path;
	}
	
	public String getCString(String string) {
		String path = "null";
		String temp;
		try {
			temp = getString(string);
			if(!temp.equals(null)) 
				path = temp;
		} catch (Exception e) {
		}
		return path;
	}

	public boolean getCBoolean(String string) {
		return getBoolean(string);
	}
	
}