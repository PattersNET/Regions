package net.patters.regions;

import java.util.List;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;

public class Region {

	private String name;
	private Location loc1;
	private Location loc2;
	private OfflinePlayer player;
	private List<OfflinePlayer> players;

	public Region(String name, Location loc1, Location loc2, OfflinePlayer player) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.player = player;
	}

	public Region(String name, Location loc1, Location loc2, OfflinePlayer player, List<OfflinePlayer> players) {
		this.name = name;
		this.loc1 = loc1;
		this.loc2 = loc2;
		this.player = player;
		this.players = players;
	}
	
	public String getName() {
		return name;
	}
	
	public OfflinePlayer getOwner() {
		return player;
	}
	
	public List<OfflinePlayer> getBuilders() {
		return players;
	}
	
	public World getWorld() {
		return loc1.getWorld();
	}
	
	public Location getLoc1() {
		return loc1;
	}
	
	public Location getLoc2() {
		return loc2;
	}

}
