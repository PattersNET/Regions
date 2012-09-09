package net.patters.regions.util;

import java.util.HashMap;

import net.patters.regions.Region;

import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Util {

	public static HashMap<Player, Region> playerRegions = new HashMap<Player, Region>();
	public static HashMap<Player, Location> loc1 = new HashMap<Player, Location>();
	public static HashMap<Player, Location> loc2 = new HashMap<Player, Location>();
	
	public static boolean hasLocations(Player player) {
		return (loc1.containsKey(player) && loc2.containsKey(player));
	}

}
