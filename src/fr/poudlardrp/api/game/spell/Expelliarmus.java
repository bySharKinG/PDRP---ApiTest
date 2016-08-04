package fr.poudlardrp.api.game.spell;

import org.bukkit.entity.Player;

import fr.poudlardrp.api.API;
import fr.poudlardrp.api.game.spell.utils.Spell;
import fr.poudlardrp.api.game.spell.utils.SpellType;

public class Expelliarmus extends Spell {
	API plugin;
	Player player;
	
	public Expelliarmus(API plugin, Player player) {
		super("Expelliarmus", SpellType.EXPELLIARMUS);
		this.plugin = plugin;
		this.player = player;
		this.define(this);
	}

	@Override
	public boolean activate(Player player) {
		return false;
	}

	@Override
	public void cooldownStart(Player player) {
		
	}

	@Override
	public void cooldownFinish(Player player) {
		
	}

	@Override
	public void cooldownActive(Player player) {
		
	}

}
