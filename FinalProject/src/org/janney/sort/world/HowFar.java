package org.janney.sort.world;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.plugin.Plugin;
import org.janney.sort.SharedHash;
import org.janney.sort.algorithms.Algorithms;

public class HowFar extends SharedHash
{
	private Algorithms alg;
	
	public HowFar(Plugin plugin)
	{
		alg = new Algorithms(plugin);
	}
	
	private void checkNext(Block b)
	{
		Location loc = b.getLocation().clone();
		Location locAdded = loc.add(2, 0, 0);
		World w = Bukkit.getWorld("world");
		
		if (locAdded.getBlock().getType() != Material.AIR)
			getHowFarHash(w.getBlockAt(locAdded));
		else
			alg.bubblyBlock();
	}
	
	public void getHowFarHash(Block b)
	{
		Location loc = b.getLocation().clone();
		int count = 0;
	
		while (hash.containsKey(count))
		{
			count++;
		}
		
		hash.put(count, loc);
	
		checkNext(b);
	}
}
