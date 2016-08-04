package fr.poudlardrp.api.game.spell.utils;

import org.bukkit.entity.Player;

public interface SpellMethods {
	public boolean activate(Player player);
	public void cooldownStart(Player player);
	public void cooldownFinish(Player player);
	public void cooldownActive(Player player);
}
