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
		
		Block sheepPortal = Bukkit.getWorld("world").getBlockAt(0, 198, 7);
		Block blockPortal = Bukkit.getWorld("world").getBlockAt(0, 198, -7);
		Block homePortal1 = Bukkit.getWorld("world").getBlockAt(0, 198, -494);
		Block homePortal2 = Bukkit.getWorld("world").getBlockAt(0, 198, 506);
		
		Location sheepLoc = sheepPortal.getLocation();
		Location blockLoc = blockPortal.getLocation();
		Location homeLoc1 = homePortal1.getLocation();
		Location homeLoc2 = homePortal2.getLocation();
		
		Location loc = p.getLocation();
		
		if (loc.distance(sheepLoc) < 2)
		{
			teleportPlayer(p, "sheep");
		}
		
		if (loc.distance(blockLoc) < 2)
		{
			teleportPlayer(p, "block");
		}
		
		if (loc.distance(homeLoc1) < 2)
		{
			teleportPlayer(p, "home");
		}
		
		if (loc.distance(homeLoc2) < 2)
		{
			teleportPlayer(p, "home");
		}
	}
	
	public void teleportPlayer(Player p, String which)
	{
		switch (which)
		{
		case "home":
			Block b2 = Bukkit.getWorld("world").getBlockAt(0, 201, 0);
			
			Location loc2 = b2.getLocation();
			
			loc2.setYaw(0);
			loc2.setPitch(0);
			loc2.add(0.5, 0, 0.5);
			
			p.teleport(loc2);
			break;
		case "sheep":
			Block b = Bukkit.getWorld("world").getBlockAt(0, 201, 500);
			
			Location loc = b.getLocation();
			
			loc.setYaw(180);
			loc.setPitch(0);
			loc.add(0.5, 0, 0.5);
			
			p.teleport(loc);
			break;
		default:
			Block b1 = Bukkit.getWorld("world").getBlockAt(0, 201, -500);
			
			Location loc1 = b1.getLocation();
			
			loc1.setYaw(180);
			loc1.setPitch(0);
			loc1.add(0.5, 0, 0.5);
			
			p.teleport(loc1);
		}
	}
}
