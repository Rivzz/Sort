package org.janney.sort.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

public class Teleport implements Listener
{
	@EventHandler
	public void checkLocation(PlayerMoveEvent e)
	{
		Player p = e.getPlayer();
		
		Block sheepPortal = Bukkit.getWorld("world").getBlockAt(9, 199, 29);
		
		Location sheepLoc = sheepPortal.getLocation();
		Location loc = p.getLocation();
		
		if (loc.distance(sheepLoc) < 2)
		{
			teleportPlayer(p, "sheep");
		}
	}
	
	public void teleportPlayer(Player p, String which)
	{
		switch (which)
		{
			case "sheep":
				Block b = Bukkit.getWorld("world").getBlockAt(9, 202, 35);
				
				Location loc = b.getLocation();
				
				loc.setYaw(180);
				loc.setPitch(0);
				loc.add(0.5, 0, 0.5);
				
				p.teleport(loc);
		}
	}
}
