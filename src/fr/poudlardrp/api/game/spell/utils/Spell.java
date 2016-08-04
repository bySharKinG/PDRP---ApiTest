package fr.poudlardrp.api.game.spell.utils;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.util.Locale;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

import fr.poudlardrp.api.API;

public abstract class Spell implements SpellMethods {
	private SpellMethods methods;
	private final String name;
	private double cooldown;
	private double currentCooldown;
	private SpellType type;
	
	public Spell(String name, SpellType type) {
		this.name = name;
		this.type = type;
		this.methods = null;
	}

	public void setCoolDown(double cooldown) {
		this.cooldown = cooldown;
	}
	
	public void define(SpellMethods methods) {
		this.methods = methods;
	}
	
	public final SpellMethods getMethods() {
		return this.methods;
	}
	
	public final SpellType getType() {
		return this.type;
	}

	public final double getCooldown() {
		return this.cooldown;
	}
	
	public final double getCurrentCooldown() {
		return this.currentCooldown;
	}
	
	public final void setCurrentCooldown(double cooldown) {
		this.currentCooldown = cooldown;
	}
	
	public final void sendCooldownBar(Player player, String format) {
        StringBuilder stringBuilder = new StringBuilder();

        double currentCooldown = getCurrentCooldown();
        double maxCooldown = getCooldown();

        int res = (int) (currentCooldown / maxCooldown * 10);
        ChatColor color;
        for (int i = 0; i < 10; i++) {
            color = ChatColor.RED;
            if (i < 10 - res)
                color = ChatColor.GREEN;
            stringBuilder.append(color + "█");
        }

        DecimalFormatSymbols otherSymbols = new DecimalFormatSymbols(Locale.US);
        otherSymbols.setDecimalSeparator('.');
        otherSymbols.setGroupingSeparator('.');
        otherSymbols.setPatternSeparator('.');
        final DecimalFormat decimalFormat = new DecimalFormat("0.0", otherSymbols);
        String timeLeft = decimalFormat.format(currentCooldown) + "s";
        
		API.getCore().v.nms.sendActionBar(player, format.replace("%player%", player.getName()).replace('&', '§')
				.replace("%name%", getName()).replace("%bar%", stringBuilder.toString()).replace("%time%", timeLeft).replace("%cooldownbar% ", " "));

    }
	
	public final String getName() {
		return this.name;
	}
}