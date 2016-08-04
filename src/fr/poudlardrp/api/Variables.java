package fr.poudlardrp.api;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Player;

import fr.poudlardrp.api.game.spell.utils.Spell;
import fr.poudlardrp.api.game.spell.utils.SpellType;
import fr.poudlardrp.api.utils.NMS;
import fr.poudlardrp.api.utils.Sorcier;

public class Variables {
	API plugin;
	public NMS nms;
	public Map<Player, Sorcier> onlinePlayer;
	public Map<String, Spell> spells;
	
	public String[] spellsLists = {"Expelliarmus"};
	
	public Variables(API plugin) {
		this.plugin = plugin;
	}
	
	public void enable() {
		this.nms = new NMS(this.plugin);
		this.onlinePlayer = new HashMap<Player, Sorcier>();
		this.spells = new HashMap<String, Spell>();
		enableSpells();
	}
	
	public void disable() {
		
	}
	
	public void enableSpells() {
		for(String spell : spellsLists) this.spells.put(spell, newInstance(SpellType.valueOf(spell)));
	}

	public Spell newInstance(SpellType type) {
		Spell spell = null;
		try {
			Class<?> clazz = Class.forName("fr.poudlardrp.api.spell." + type.getID());
			Constructor<?> ctor = clazz.getConstructor(API.class, Player.class);
			spell = (Spell) ctor.newInstance(this.plugin);
		} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | IllegalArgumentException
				| InvocationTargetException | NoSuchMethodException | SecurityException e) {
		}
		return spell;
	}
	
}
