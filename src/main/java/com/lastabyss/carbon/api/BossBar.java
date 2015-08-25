package com.lastabyss.carbon.api;

import java.util.UUID;

import net.minecraft.server.v1_8_R3.ChatComponentText;
import net.minecraft.server.v1_8_R3.IChatBaseComponent;

import org.bukkit.entity.Player;

import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar;
import com.lastabyss.carbon.network.packets.CarbonPacketPlayOutBossBar.EnumBossBarAction;
import com.lastabyss.carbon.utils.Utils;

public class BossBar {

	private UUID uniqueId;
	private IChatBaseComponent message;
	private float health;
	private BossBarColor color;
	private BossBarDivider divider;
	private boolean darkenSky;
	private boolean isDragon;
	private CarbonPacketPlayOutBossBar packet;

	public BossBar(UUID uuid, IChatBaseComponent message, BossBarColor color, BossBarDivider divider, CarbonPacketPlayOutBossBar packet, boolean darkenSky, boolean isDragon) {
		this.uniqueId = uuid;
		this.message = message;
		this.color = color;
		this.divider = divider;
		this.darkenSky = darkenSky;
		this.isDragon = isDragon;
		this.health = 1.0F;
		this.packet = packet;
	}

	public UUID getUniqueId() {
		return this.uniqueId;
	}

	public IChatBaseComponent getMessage() {
		return this.message;
	}
	
	protected void setMessage(String message) {
		this.message = new ChatComponentText(message);
		packet.resetValues(this);
		packet.setAction(EnumBossBarAction.UPDATE_HEALTH);
	}

	public float getHealth() {
		return this.health;
	}

	protected void setHealth(float health) {
		this.health = health;
		packet.resetValues(this);
		packet.setAction(EnumBossBarAction.UPDATE_HEALTH);
	}

	public BossBarColor getColor() {
		return this.color;
	}

	protected void setColor(BossBarColor color) {
		this.color = color;
		packet.setAction(EnumBossBarAction.UPDATE_STYLE);
	}

	public BossBarDivider getDivider() {
		return this.divider;
	}

	protected void setDivider(BossBarDivider divider) {
		this.divider = divider;
		packet.setAction(EnumBossBarAction.UPDATE_STYLE);
	}

	public boolean shouldDarkenSky() {
		return this.darkenSky;
	}

	public boolean isDragon() {
		return this.isDragon;
	}
	
	protected void sendPacket(Player player) {
		Utils.sendPacket(player, packet);
	}
	
	protected void removeBar(Player player) {
		packet.setAction(EnumBossBarAction.REMOVE);
		sendPacket(player);
	}

	public enum BossBarDivider {
		PROGRESS, NOTCHED_6, NOTCHED_10, NOTCHED_12, NOTCHED_20
	}

	public enum BossBarColor {
		PINK, BLUE, RED, GREEN, YELLOW, PURPLE, WHITE
	}

}
