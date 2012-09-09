package net.patters.regions.event;

import net.patters.regions.Region;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RegionEnterEvent extends Event {

	private Region region;
	private Player player;
    private static final HandlerList handlers = new HandlerList();

	public RegionEnterEvent(Player player, Region region) {
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
