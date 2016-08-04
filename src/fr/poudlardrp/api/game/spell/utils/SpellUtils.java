package fr.poudlardrp.api.game.spell.utils;

import java.util.HashSet;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.util.Vector;

public class SpellUtils {
	
	public static Entity getNearestEntityInSight(Player p, int radius, Location l) {
		for (Entity entity : l.getWorld().getEntities()) {
			if (entity instanceof LivingEntity) {
				if (entity.getLocation().distance(l) <= radius) {
					if (entity != p) {
						return entity;
					}
				}
			}
		}
		return null;
	}

	public static Entity getEntityInSight(Player p) {
		Set<Material> blockr = new HashSet<Material>();
		blockr.add(Material.SAPLING);
		blockr.add(Material.WEB);
		blockr.add(Material.LONG_GRASS);
		blockr.add(Material.DEAD_BUSH);
		blockr.add(Material.YELLOW_FLOWER);
		blockr.add(Material.RED_ROSE);
		blockr.add(Material.BROWN_MUSHROOM);
		blockr.add(Material.RED_MUSHROOM);
		blockr.add(Material.TORCH);
		blockr.add(Material.VINE);
		blockr.add(Material.WATER_LILY);
		blockr.add(Material.DOUBLE_PLANT);
		blockr.add(Material.FLOWER_POT);
		blockr.add(Material.SKULL);
		blockr.add(Material.BANNER);
		blockr.add(Material.LEVER);
		blockr.add(Material.REDSTONE_TORCH_ON);
		blockr.add(Material.STONE_BUTTON);
		blockr.add(Material.WOOD_BUTTON);
		blockr.add(Material.VINE);
		blockr.add(Material.AIR);
		Location l1 = p.getLocation();
		Location l2 = p.getTargetBlock(blockr, 15).getLocation();
		Vector v1 = l1.toVector();
		Vector v2 = l2.toVector();

		Vector diff = v2.subtract(v1);
		double dist = diff.length();

		double dx = (diff.getX() / dist) * 1;
		double dy = (diff.getY() / dist) * 1;
		double dz = (diff.getZ() / dist) * 1;

		Location loc = l1.clone();

		for (double d = 0; d <= dist; d += 1) {
			if (getNearestEntityInSight(p, 2, loc) != null && getNearestEntityInSight(p, 2, loc) != p) {
				d = dist;
				return getNearestEntityInSight(p, 2, loc);
			}
			loc.add(dx, dy, dz);
		}
		return null;
	}

}
