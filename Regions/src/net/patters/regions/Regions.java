package net.patters.regions;

import net.patters.regions.commands.CreateCommand;
import net.patters.regions.commands.InfoCommand;
import net.patters.regions.commands.ListCommand;
import net.patters.regions.commands.DeleteCommand;

import net.patters.regions.listener.RegionListener;
import net.patters.regions.managers.RegionManager;

import org.bukkit.plugin.java.JavaPlugin;

public class Regions extends JavaPlugin {

	private static RegionManager regionManager;
	private static Regions plugin;

	@Override
	public void onEnable() {
		Regions.regionManager = new RegionManager();
		Regions.plugin = this;
		
		regionManager.load();
		
		this.setupCommands();
		
		this.getServer().getPluginManager().registerEvents(new RegionListener(), this);
	}

	@Override
	public void onDisable() {
		regionManager.save();
	}
	
	private void setupCommands() {
		this.getCommand("rcreate").setExecutor(new CreateCommand());
		this.getCommand("rdelete").setExecutor(new DeleteCommand());
		this.getCommand("rinfo").setExecutor(new InfoCommand());
		this.getCommand("rlist").setExecutor(new ListCommand());
	}
	
	public static Regions getPlugin() {
		return plugin;
	}
	
	public static RegionManager getRegionManager() {
		return regionManager;
	}
}
