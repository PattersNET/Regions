package net.patters.regions.event;

import net.patters.regions.Region;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionExitEvent extends Event {

	private Player player;
	private Region region;
    private static final HandlerList handlers = new HandlerList();

	public RegionExitEvent(Player player, Region region) {
		this.player = player;
		this.region = region;
	}
	
	public Player getPlayer() {
		return player;
	}

	public Region getRegion() {
		return region;
	}
	
	public HandlerList getHandlers() {
		return handlers;
	}
	
    public static HandlerList getHandlerList() {
        return handlers;
    }

}
