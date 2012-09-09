package net.patters.regions.commands;

import net.patters.regions.Region;
import net.patters.regions.Regions;
import net.patters.regions.util.Util;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CreateCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(!(args.length == 1)) {
			return true;
		}
		
		if(!(Util.hasLocations(player))) {
			return true;
		}
		
		for(Region r : Regions.getRegionManager().getRegions())
		{
			if(r.getName().equalsIgnoreCase(args[0]))
			{
				player.sendMessage("Region with this name already exists.");
				return true;
			}
		}
		
		Region region = new Region(args[0], Util.loc1.get(player), Util.loc2.get(player), player);
		Regions.getRegionManager().createRegion(region);
		player.sendMessage("You have created a new region called '" + args[0] + "'");
		
		return true;
	}

}
