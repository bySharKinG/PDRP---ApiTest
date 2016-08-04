package fr.poudlardrp.api.game.spell.utils;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import fr.poudlardrp.api.API;
import fr.poudlardrp.api.utils.Config;
import fr.poudlardrp.api.utils.Sorcier;

public class SpellManager implements Listener {
	public API plugin;
	public Cooldown cooldown;
	public Config config;
	public ItemStack item;
	
	
	public SpellManager(API plugin) {
		this.plugin = plugin;
		this.cooldown = new Cooldown();
	}
	
	@EventHandler
	public void playerInteract(PlayerInteractEvent e) {
		Sorcier player = this.plugin.getSorcier(e.getPlayer());
		try {
			if (player.hasSpell()) {
				if (e.hasItem() && !(e.getItem() == null)) {
					this.item = e.getItem();
					String spellName = player.getSpell().getType().getID();
					Spell spell = this.plugin.v.spells.get(spellName);
					String itemName = e.getItem().getItemMeta().getDisplayName();
					if(!this.cooldown.isInCooldown(e.getPlayer(), spell)) {
						this.config = new Config(new File("plugins/GHub/gadget", spellName + ".yml"));
						if(itemName.equals(this.config.getCString("Name")
								.replace('&', '§').replace("%player%", e.getPlayer().getName()))) {
							Entity nearby = SpellUtils.getEntityInSight(player.getPlayer());
							if(nearby != null && nearby instanceof Player) {
								if (this.plugin.v.spells.get(spellName).getMethods().activate(e.getPlayer(), nearby)) 
									this.cooldown.addCooldown(e.getPlayer(), this.plugin.v.spells.get(spellName));
							}
							e.setCancelled(true);
						}
					}
				}
			}
		} catch (Exception e2) {
		}
	}
	
	private class Cooldown extends Thread {
		private final Map<String, Double> data;
		private Sorcier player = null;
		
		public Cooldown() {
			this.data = new HashMap<String, Double>();
			start();
		}
		
		public void run() {
			while (true) {
				if (API.stop) disable();
				try {
					sleep(1000);
				} catch (InterruptedException e) {
				}
				final Map<String, Double> cache = new HashMap<String, Double>(this.data);
				for (String key : cache.keySet()) {
					final double number = this.data.get(key) - 1;
					this.data.remove(key);
					if (number > 0) {
						this.data.put(key, number); 
						plugin.v.spells.get(key.split(" ")[0]).setCurrentCooldown(number);
						this.player = API.getCore().getSorcier(plugin.getServer().getPlayer(key.split(" ")[1]));
						plugin.v.spells.get(key.split(" ")[0]).getMethods().cooldownActive(plugin.getServer().getPlayer(key.split(" ")[1]));
					} else plugin.v.spells.get(key.split(" ")[0]).getMethods().cooldownFinish(plugin.getServer().getPlayer(key.split(" ")[1]));

				}
			}
		}
		
		@SuppressWarnings("deprecation")
		private void disable() {
			stop();
		}

		public boolean isInCooldown(Player player, Spell spell) {
			return this.data.containsKey(spell.getName() + " " + player.getName());
		}
		
		public void addCooldown(Player player, Spell spell) {
			spell.getMethods().cooldownStart(player);
			this.data.clear();
			this.data.put(spell.getName() + " " + player.getName(), spell.getCooldown());
		}
	}
}