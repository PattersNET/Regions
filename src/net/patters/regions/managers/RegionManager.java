package net.patters.regions.managers;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import org.bukkit.Location;
import org.bukkit.OfflinePlayer;
import org.bukkit.World;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import net.patters.regions.Region;
import net.patters.regions.Regions;

public class RegionManager {

	private final static File path = new File("plugins" + File.separator + "Regions" + File.separator + "regions.yml");
	private final FileConfiguration regionsData = new YamlConfiguration();

	// Regions API //////////////////////////////////////////////
	
	public List<Region> getRegions() {
		
		List<World> worlds = Regions.getPlugin().getServer().getWorlds();
		List<Region> regions = new ArrayList<Region>();
		
		for(int i = 0; i < worlds.size(); i++) { 
			
			String world = worlds.get(i).getName();
			ConfigurationSection section = regionsData.getConfigurationSection(world);
			
			if(section != null) {
				
				Set<String> regionNames = section.getKeys(false);

				for(String regionName : regionNames) {
					
					int x1 = regionsData.getInt(world + "." + regionName + ".x1");
					int x2 = regionsData.getInt(world + "." + regionName + ".x2");
					int y1 = regionsData.getInt(world + "." + regionName + ".y1");
					int y2 = regionsData.getInt(world + "." + regionName + ".y2");
					int z1 = regionsData.getInt(world + "." + regionName + ".z1");
					int z2 = regionsData.getInt(world + "." + regionName + ".z2");
					Location loc1 = new Location(worlds.get(i), x1, y1, z1);
					Location loc2 = new Location(worlds.get(i), x2, y2, z2);
					String player = regionsData.getString(world + "." + regionName + ".owner");
					List<OfflinePlayer> players = new ArrayList<OfflinePlayer>();
					
					for(String builder : regionsData.getStringList(world + "." + regionName + ".builders"))
					{
						players.add(Regions.getPlugin().getServer().getOfflinePlayer(builder));
					}
					
					regions.add(new Region(regionName, loc1, loc2, Regions.getPlugin().getServer().getPlayer(player), players));
				}
				
			}
			else { continue; }
			
		}
		
		return regions;
		
	}
	
	public boolean inRegion(Location from, Location to, Region region) {

		boolean bFrom = false, bTo = false;
		
		Integer[] x = new Integer[] { Math.min(region.getLoc1().getBlockX(), region.getLoc2().getBlockX()),
									  Math.max(region.getLoc1().getBlockX(), region.getLoc2().getBlockX()) };

		Integer[] y = new Integer[] { Math.min(region.getLoc1().getBlockY(), region.getLoc2().getBlockY()),
									  Math.max(region.getLoc1().getBlockY(), region.getLoc2().getBlockY()) };

		Integer[] z = new Integer[] { Math.min(region.getLoc1().getBlockZ(), region.getLoc2().getBlockZ()),
									  Math.max(region.getLoc1().getBlockZ(), region.getLoc2().getBlockZ()) };
		
		if(from.getBlockX() >= x[0] && from.getBlockX() <= x[1] &&
				from.getBlockY() >= y[0] && from.getBlockY() <= y[1] &&
						from.getBlockZ() >= z[0] && from.getBlockZ() <= z[1]) {
			
			bFrom = true;
			
		}

		if(to.getBlockX() >= x[0] && to.getBlockX() <= x[1] &&
				to.getBlockY() >= y[0] && to.getBlockY() <= y[1] &&
						to.getBlockZ() >= z[0] && to.getBlockZ() <= z[1]) {
			
			bTo = true;
			
		}
		
		return (bFrom == bTo) ? false : true;
	}
	
	public List<Player> getPlayers(Region region) {
		
		Player[] players = Regions.getPlugin().getServer().getOnlinePlayers();
		
		List<Player> playersInRegion = new ArrayList<Player>();
		
		for(Player player : players) {
			if(getRegion(player.getLocation()) == region) { playersInRegion.add(player); };
		}
		
		return playersInRegion;
	}
	
	
	public Region getRegion(Location loc) {
		
		List<Region> regions = getRegions();
		int px = loc.getBlockX();
		int py = loc.getBlockY();
		int pz = loc.getBlockZ();
		
		for(int i = 0; i < regions.size(); i++) {

			Region region = regions.get(i);
			
			Integer[] x = new Integer[] { Math.min(region.getLoc1().getBlockX(), region.getLoc2().getBlockX()),
										  Math.max(region.getLoc1().getBlockX(), region.getLoc2().getBlockX()) };

			Integer[] y = new Integer[] { Math.min(region.getLoc1().getBlockY(), region.getLoc2().getBlockY()),
										  Math.max(region.getLoc1().getBlockY(), region.getLoc2().getBlockY()) };

			Integer[] z = new Integer[] { Math.min(region.getLoc1().getBlockZ(), region.getLoc2().getBlockZ()),
										  Math.max(region.getLoc1().getBlockZ(), region.getLoc2().getBlockZ()) };
			
			if(px >= x[0] && px <= x[1] &&
					py >= y[0] && py <= y[1] &&
							pz >= z[0] && pz <= z[1]) {
				
				return regions.get(i);
				
			}
			
		}
		
		return null;
		
	}

	public Region getRegion(String name) {

		List<Region> regions = getRegions();

		for(int i = 0; i < regions.size(); i++) {
			
			if(regions.get(i).getName().equals(name)) {
				return regions.get(i);
			}
			
		}
		
		
		return null;
	}
	
	public void createRegion(Region region) {

		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".x1", region.getLoc1().getBlockX());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".y1", region.getLoc1().getBlockY());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".z1", region.getLoc1().getBlockZ());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".x2", region.getLoc2().getBlockX());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".y2", region.getLoc2().getBlockY());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".z2", region.getLoc2().getBlockZ());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".owner", region.getOwner().getName());

		
	}

	public void addBuilder(OfflinePlayer builder, Region region)
	{
		List<String> builders = regionsData.getStringList(region.getWorld().getName() + "." + region.getName() + ".builders");
		builders.add(builder.getName());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".builders", builders);
	}

	public void removeBuilder(OfflinePlayer builder, Region region)
	{
		List<String> builders = regionsData.getStringList(region.getWorld().getName() + "." + region.getName() + ".builders");
		builders.remove(builder.getName());
		regionsData.set(region.getWorld().getName() + "." + region.getName() + ".builders", builders);
	}
	
	public void removeRegion(Region region) {
		regionsData.set(region.getWorld().getName() + "." + region.getName(), null);
	}
	
	// Load/Save API ////////////////////////////////////////////
	
	public void load() {
		
		try {
			
			regionsData.load(path);
			
		} 
		catch (FileNotFoundException e) {
			
			path.getParentFile().mkdirs();
			
			try {
				path.createNewFile();
				return;
			}
			catch (IOException ex) {
				return;
			}
			
		} catch (IOException e) {
			// Log TODO
		} catch (InvalidConfigurationException e) {
			// Log TODO
		}
		
		
	}
	
	public void save() {
		
		if(!path.exists()) {
			path.getParentFile().mkdirs();
			try {
				path.createNewFile();
			}
			catch (IOException ex) {
				// Log TODO
			}
		}

		try {
			regionsData.save(path);
		} catch (IOException e) {
			// Log TODO
		}
		
	}
	
}
