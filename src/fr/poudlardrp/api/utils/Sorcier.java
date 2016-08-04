package fr.poudlardrp.api.utils;

import java.io.File;
import java.io.IOException;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.Vector;

import fr.poudlardrp.api.API;
import fr.poudlardrp.api.game.spell.utils.Spell;
import fr.poudlardrp.api.game.spell.utils.SpellType;

public class Sorcier {
	API plugin;
	Player player;
	Config config;
	int currentSlot = 0;
	public Scoreboard scoreboard;
	Spell currentSpell;
	
	public Sorcier(API plugin, Player player) {
		this.plugin = plugin;
		this.player = player;
	}
	
	public Config getConfig() {
		if (config == null)
			try {
				config = new Config(new File("plugins/GHub/Player", player.getUniqueId() + ".yml"));
			} catch (IOException | InvalidConfigurationException e) {
				e.printStackTrace();
			}
	
		return config;
	}
	
	public boolean isWhitelist() {
		return player.isWhitelisted();
	}
	
	public boolean isBan() {
		return player.isBanned();
	}
	
	public boolean isMute() {
		return false;
	}
	
	public boolean isOp() {
		return player.isOp();
	}
	
	public void kick(String raison) {
		player.kickPlayer(raison.replace('&', '§'));
	}
	
	public int getCoins() {
		return getConfig().getCInt("coins");
	}
	
	public SpellType getSpellType() {
		return getSpell().getType();
	}
	
	public Spell getSpell() {
		return this.currentSpell;
	}
	
	public boolean hasSpell() {
		return this.currentSpell != null;
	}
	
	public int getCurrentSlot() {
		return this.currentSlot;
	}
	
	public void setCurrentSlot(int slot) {
		this.currentSlot = slot;
	}
	
//	public void removeParticle() {
//		try {
//			getParticle().clear();
//			if(this.plugin.v.currentPart.containsKey(player)) this.plugin.v.currentPart.remove(player);
//		} catch (Exception e) {
//		}
//	}
	
//	public List<String> getParticles() {
//		return Arrays.asList(this.plugin.v.particles);
//	}
	
//	public void join() {
//		this.plugin.v.group.put(player, getConfig().getCString("group"));
//		this.plugin.v.options.put(player, getConfig().getStringList("options"));
//		this.plugin.v.quitMessage.put(player, getQuitMessage().replace("%player%", getName())
//				.replace('&', '§'));
//		this.plugin.v.onlinePlayer.put(player, this);
//	}
//	
//	public void quit() {
//		this.plugin.v.group.remove(player);
//		this.plugin.v.options.remove(player);
//		this.plugin.v.quitMessage.remove(player);
//		remove("all", null);
//		this.plugin.v.onlinePlayer.remove(player);
//	}
//	
//	public boolean isNewplayer() {
//		File file = new File("plugins/GHub/Player", player.getUniqueId() + ".yml");
//		return file.exists();
//	}
	
//	public void newPlayer() {
//		Date currentDate = new Date();
//		String txtDate = new SimpleDateFormat("dd MM yyyy hh mm ss").format(currentDate);
//		try {
//			new File("plugins/GHub/Player", player.getUniqueId() + ".yml").createNewFile();
//		} catch (IOException e) {
//		}
//		getConfig().setC("group", this.plugin.getConfig().getString("groups.default"));
//		getConfig().setC("coins", 0);
//		getConfig().setC("firstJoin", txtDate);
//		defineLastLocation();
//		ArrayList<String> list = new ArrayList<>();
//		list.add("chat");
//		list.add("players");
//		list.add("mp");
//		getConfig().setC("options", list);
//		int lastTotalPlayer = this.plugin.getConfig().getInt("totalPlayer");
//		int totalPlayer = lastTotalPlayer + 1;
//		this.plugin.getConfig().set("totalPlayer", totalPlayer);
//		this.plugin.saveConfig();
//		if(this.plugin.getConfig().getBoolean("newPlayer.check")) Bukkit.broadcastMessage(this.plugin.getConfig().getString("newPlayer.message").replace('&', '§').replace("%player%", getName()).replace("%total%", "" + totalPlayer));
//		join();
//	}
	
	public Location getLastLocation() {
		return new Location(this.plugin.getServer().getWorld(getConfig().getCString("lastLoc").split(" ")[0]), Double.parseDouble(getConfig().getCString("lastLoc").split(" ")[1]),
				Double.parseDouble(getConfig().getCString("lastLoc").split(" ")[2]), Double.parseDouble(getConfig().getCString("lastLoc").split(" ")[3]));
	}
	
	public Location getLocation() {
		return new Location(player.getWorld(), player.getLocation().getX(),
				player.getLocation().getY(), player.getLocation().getZ());
	}
	
	public double getX() {
		return getLocation().getX();
	}
	
	public double getY() {
		return getLocation().getY();
	}
	
	public double getZ() {
		return getLocation().getZ();
	}
	
	public World getWorld() {
		return getLocation().getWorld();
	}
	
	public boolean isMoving() {
		return !getLocation().equals(getLastLocation());
	}
	
	public void defineLastLocation() {
		getConfig().setC("lastLoc", getWorld().getName() +
				" " + getX() +
				" " + getY() +
				" " + getZ());
	}
	
    public Location getLocationFacing() {
        Location l = player.getLocation().clone();
        Vector v = l.getDirection();
        v.setY(0);
        v.multiply(1.5);
        l.add(v);
        l.setYaw(l.getYaw() + 180);
        int n;
        boolean ok = false;
        for (n = 0; n < 5; n++) {
            if (l.getBlock().getType().isSolid()) {
                l.add(0, 1, 0);
            } else {
                ok = true;
                break;
            }
        }
        if (!ok) {
            l.subtract(0, 5, 0);
        }
        return l;
    }
	
	public GameMode getGamemode() {
		return player.getGameMode();
	}
	
//	public String getChatFormat() {
//		return this.plugin.getConfig().getString("groups." + getGroup() + ".chat");
//	}
//	
//	public String getQuitMessage() {
//		return this.plugin.getConfig().getString("groups." + getGroup() + ".quit");
//	}
//	
//	public String getJoinMessage() {
//		return this.plugin.getConfig().getString("groups." + getGroup() + ".join");
//	}
	
	public String getName() {
		return player.getName();
	}
	
	public void sendMessage(String message) {
		player.sendMessage(message);
	}

//	public void sendCMessage(String arg1) {
//		MessageType type = MessageType.valueOf(arg1.split(" ")[0].toUpperCase());
//		String message = "";
//		for (int i = 1; i < arg1.split(" ").length; i++) {
//			message = message + arg1.split(" ")[i] + " ";
//		}
//		switch (type) {
//		case ACTIONBAR:
//			GHub.getInstance().v.nms.sendActionBar(player, message.replace('&', '§').replace("%player%", player.getName()).replace("%group%", getGroup()));
//			break;
//		case CHAT:
//			player.sendMessage(message.replace('&', '§').replace("%player%", player.getName()).replace("%group%", getGroup()));
//			break;
//		case TITLE:
//			GHub.getInstance().v.nms.sendTitle(player, message.replace('&', '§').replace("%player%", player.getName()).replace("%group%", getGroup()), " ", 1, 40, 1);
//			break;
//		case SUBTITLE:
//			GHub.getInstance().v.nms.sendTitle(player, " ", message.replace('&', '§').replace("%player%", player.getName()).replace("%group%", getGroup()), 1, 40, 1);
//			break;
//		default:
//			break;
//		}
//	}
	
	public void openInventory(Inventory menu) {
		player.openInventory(menu);
	}

	public Inventory getInventory() {
		return player.getInventory();
	}
	
	public void setScoreboard(Scoreboard scoreboard) {
		this.scoreboard = scoreboard;
	}
	
	public Scoreboard getScoreboard() {
		if(this.scoreboard != null) return this.scoreboard;
		else return null;
	}
	
	public Player getPlayer() {
		return player;
	}

	public void teleport(Location loc) {
		this.plugin.v.nms.sendActionBar(player, "§6T§l§portation en cour...");
		player.teleport(loc);
		this.plugin.v.nms.sendActionBar(player, "§6T§l§portation §ff§ctu§...");
	}
	
}