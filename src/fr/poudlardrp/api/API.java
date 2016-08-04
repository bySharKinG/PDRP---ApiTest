package fr.poudlardrp.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import fr.poudlardrp.api.utils.Sorcier;

public class API extends JavaPlugin {
	public Variables v;
	private static API core;
	public static boolean stop = false;
	
	public void onEnable() {
		this.v = new Variables(this);
		
		core = this;
		this.v.enable();
		
		System.out.println("PoudlardAPI - v" + getDescription().getVersion() + " successfully loaded.");
	}
	
	public void onDisable() {
		this.v.disable();
		stop = true;
	}

	public static API getCore() {
		return core;
	}

	public Sorcier getSorcier(Player player) {
		return this.v.onlinePlayer.get(player);
	}
	
}
