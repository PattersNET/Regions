package net.patters.regions.commands;

import net.patters.regions.Region;
import net.patters.regions.Regions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class InfoCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
	
		if(!(args.length == 1)) {
			return true;
		}
		
		Region region = Regions.getRegionManager().getRegion(args[0]);
		
		if(region == null) {
			return true;
		}
		
		sender.sendMessage("Name: " + region.getName());
		sender.sendMessage("Owner: " + region.getOwner().getName());
		sender.sendMessage("Location :-");
		sender.sendMessage(">> World: " + region.getWorld().getName());
		sender.sendMessage(">> x1: " + region.getLoc1().getBlockX()
				+ ", x2: " + region.getLoc2().getBlockX());
		sender.sendMessage(">> 1: " + region.getLoc1().getBlockY()
				+ ", y2: " + region.getLoc2().getBlockY());
		sender.sendMessage(">> z1: " + region.getLoc1().getBlockZ()
				+ ", z2: " + region.getLoc2().getBlockZ());
		
		return true;
	 
	}

}
