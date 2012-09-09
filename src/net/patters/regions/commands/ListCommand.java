package net.patters.regions.commands;

import net.patters.regions.Region;
import net.patters.regions.Regions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ListCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
	
		Player player = (Player) sender;
		
		player.sendMessage("Regions :-");
		
		for(Region r : Regions.getRegionManager().getRegions())
		{
			player.sendMessage(">> " + r.getName());
		}
		return true;
		
	 
	}

}
