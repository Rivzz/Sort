package org.janney.sort;

import java.util.HashMap;

import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;

public class SharedHash 
{
	protected static HashMap<Integer, Location> hash = new HashMap<Integer, Location>();
	
	public Integer getHowFar(Block b)
	{
		Location loc = b.getLocation().clone();
		double y = loc.getBlockY();
		int distance = 0;
		
		if (b.getType() != Material.AIR)
			for (double i = y; i >= 0; i++)
			{
				loc.setY(i);
				
				if (!loc.getBlock().getType().isSolid())
					break;
				
				distance++;
			}
		
		return distance;
	}
}
