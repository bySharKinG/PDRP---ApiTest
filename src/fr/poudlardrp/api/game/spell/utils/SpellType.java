package fr.poudlardrp.api.game.spell.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;

import fr.poudlardrp.api.utils.Config;

public enum SpellType {
	
	EXPELLIARMUS("Expelliarmus");
	
	private String id;
	private Config config;
	
	private SpellType(String id) {
		try {
			this.id = id;
			try {
				this.config = new Config(new File("plugins/PoudlardAPI/spells", id + ".yml"));
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}
	
	public String getID() {
		return this.id;
	}
	
	public Config getConfig() {
		return this.config;
	}
	
	public int getCooldown() {
		return this.config.getCInt("cooldown");
	}
	
	public static final SpellType getTypeByID(String id) {
		SpellType level = SpellType.EXPELLIARMUS;
		for (SpellType temp : SpellType.values()) if (temp.getID().equals(id)) level = temp;
		return level;
	}

	public final String getName() {
		return this.id;
	}
}