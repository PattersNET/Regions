package net.patters.regions.commands;

import net.patters.regions.Region;
import net.patters.regions.Regions;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DeleteCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String cmdLabel, String[] args) {
		
		if(!(sender instanceof Player)) {
			return true;
		}
		
		Player player = (Player) sender;
		
		if(!(args.length == 1)) {
			return true;
		}
		
		Region region = Regions.getRegionManager().getRegion(args[0]);
		
		if(region == null)
		{
			return true;
		}
		
		Regions.getRegionManager().removeRegion(region);
		player.sendMessage("Area removed '" + args[0] + "'");
		
		return true;
	}

}
