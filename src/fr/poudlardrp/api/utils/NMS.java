package fr.poudlardrp.api.utils;

import java.lang.reflect.Field;

import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;

import fr.poudlardrp.api.API;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;
import net.minecraft.server.v1_8_R3.IChatBaseComponent.ChatSerializer;
import net.minecraft.server.v1_8_R3.NBTTagCompound;
import net.minecraft.server.v1_8_R3.PacketPlayOutChat;
import net.minecraft.server.v1_8_R3.PacketPlayOutPlayerListHeaderFooter;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle;
import net.minecraft.server.v1_8_R3.PacketPlayOutTitle.EnumTitleAction;
import net.minecraft.server.v1_8_R3.PlayerConnection;

public class NMS {
	API p;
	
	public NMS(API p) {
		this.p = p;
	}
	
	public void sendTitle(Player player, String msgTit, String msgSubTit, int ticks1, int ticks2, int ticks3) {
		IChatBaseComponent chatTitle = ChatSerializer.a("{\"text\": \"" + msgTit + "\"}");
		IChatBaseComponent chatSubTitle = ChatSerializer.a("{\"text\": \"" + msgSubTit + "\"}");
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TITLE, chatTitle);
		PacketPlayOutTitle p2 = new PacketPlayOutTitle(EnumTitleAction.SUBTITLE, chatSubTitle);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p2);
		sendTime(player, ticks1, ticks2, ticks3);
	}
	
	public void sendTime(Player player, int ticks1, int ticks2, int ticks3) {
		PacketPlayOutTitle p = new PacketPlayOutTitle(EnumTitleAction.TIMES, null, ticks1, ticks2, ticks3);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(p);
	}

	public void sendTabTitle(Player player, String header, String footer) {
		if (header == null) header = "";
		if (footer == null) footer = "";

		PlayerConnection connection = ((CraftPlayer) player).getHandle().playerConnection;
		IChatBaseComponent tabTitle = ChatSerializer.a("{\"text\": \"" + header + "\"}");
		IChatBaseComponent tabFoot = ChatSerializer.a("{\"text\": \"" + footer + "\"}");
		PacketPlayOutPlayerListHeaderFooter headerPacket = new PacketPlayOutPlayerListHeaderFooter(tabTitle);

		try {
			Field field = headerPacket.getClass().getDeclaredField("b");
			field.setAccessible(true);
			field.set(headerPacket, tabFoot);
		} catch (Exception e) {
		} finally {
			connection.sendPacket(headerPacket);
		}
	}
	
	public void sendActionBar(Player player, String msg) {
		IChatBaseComponent cbc = ChatSerializer.a("{\"text\": \"" + msg + "\"}");
		PacketPlayOutChat poc = new PacketPlayOutChat(cbc, (byte) 2);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(poc);
	}
    
	public void SpecialMessage(String json, Player player) {
		IChatBaseComponent comp = ChatSerializer.a(json);
		PacketPlayOutChat packet = new PacketPlayOutChat(comp);
		((CraftPlayer) player).getHandle().playerConnection.sendPacket(packet);
	}
	
	public void nbtTag(Entity ent, String nbt, int lvl) {
		net.minecraft.server.v1_8_R3.Entity nmsEntity = ((CraftEntity) ent).getHandle();
		NBTTagCompound tag = new NBTTagCompound();

		nmsEntity.c(tag);
		tag.setInt(nbt, lvl);
		nmsEntity.f(tag);
	}
	
}