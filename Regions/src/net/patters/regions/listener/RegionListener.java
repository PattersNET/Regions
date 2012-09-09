package net.patters.regions.listener;

import java.util.List;

import net.patters.regions.Region;
import net.patters.regions.Regions;
import net.patters.regions.event.RegionEnterEvent;
import net.patters.regions.event.RegionExitEvent;
import net.patters.regions.util.Util;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.event.player.PlayerMoveEvent;

public class RegionListener implements Listener {
	
	@EventHandler
	public void onMove(PlayerMoveEvent e) {
		
		List<Region> regions = Regions.getRegionManager().getRegions();
		Player player = e.getPlayer();
		Location loc = player.getLocation();

		for(int i = 0; i < regions.size(); i++) {
			
			if(Regions.getRegionManager().inRegion(e.getFrom(), e.getTo(), regions.get(i))) {

				Regions.getPlugin().getLogger().info(loc.toString());
				
				if(Regions.getRegionManager().getRegion(loc) != null) {
					Regions.getPlugin().getServer().getPluginManager().callEvent(new RegionExitEvent(player, regions.get(i)));
				}
				else {
					Regions.getPlugin().getServer().getPluginManager().callEvent(new RegionEnterEvent(player, regions.get(i)));
				}
			
			}
		}
	}
	
	@EventHandler
	public void onEnter(RegionEnterEvent e)
	{
		e.getPlayer().sendMessage("You have entered '" + e.getRegion().getName() + "'");
	}
	
	@EventHandler
	public void onExit(RegionExitEvent e)
	{
		e.getPlayer().sendMessage("You have left '" + e.getRegion().getName() + "'");
	}
	
	@EventHandler
	public void onInteract(PlayerInteractEvent e) {
		
		Player player = e.getPlayer();
		
		if(!(player.hasPermission("regions.tool"))) { return; }
		if(!(e.getMaterial() == Material.WOOD_AXE)) { return; }
		
		if(e.getAction() == Action.LEFT_CLICK_BLOCK) {
			Util.loc1.put(player, e.getClickedBlock().getLocation());
			player.sendMessage("Location #1 set at X: " + Util.loc1.get(player).getBlockX() + " Y: " + Util.loc1.get(player).getBlockY() + " Z:" + Util.loc1.get(player).getBlockZ());
		}
		else if(e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			Util.loc2.put(player, e.getClickedBlock().getLocation());
			player.sendMessage("Location #2 set at X: " + Util.loc2.get(player).getBlockX() + " Y: " + Util.loc2.get(player).getBlockY() + " Z:" + Util.loc2.get(player).getBlockZ());
		}
		
	}
	
	@EventHandler
	public void onPlace(BlockPlaceEvent e)
	{
		Region r = Regions.getRegionManager().getRegion(e.getBlockPlaced().getLocation());

		if(r == null) { return; }
		if(!r.getBuilders().contains(e.getPlayer()) && e.getPlayer() != r.getOwner())
		{ 
			e.setCancelled(true); return;
		}
	}
	
	@EventHandler
	public void onBreak(BlockBreakEvent e)
	{
		Region r = Regions.getRegionManager().getRegion(e.getBlock().getLocation());
		
		if(r == null) { return; }
		if(!r.getBuilders().contains(e.getPlayer()) && e.getPlayer() != r.getOwner())
		{ 
			e.setCancelled(true); return;
		}
	}
	
}
