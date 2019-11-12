package org.janney.sort.world;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.janney.sort.algorithms.Algorithms;
import org.janney.sort.sheep.SheepEntity;

/*
 * Calculating distance of block to sky
 * @clearConfig(): Clears data.yml
 * @getHowFarStore(): Gets distance of coordinates to sky, saves data.yml
 * @checkNext(): Determines if block exists next to other
 */

public class HowFar
{
	private Plugin plugin;
	private Algorithms alg;
	
	public HowFar(Plugin plugin)
	{
		this.plugin = plugin;
		alg = new Algorithms(plugin);
	}
	
	public void clearConfig()
	{
		File file = new File(plugin.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		data.set("locations", null);
		try {
			data.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private void checkNext(Block b)
	{
		Location loc = b.getLocation().clone();
		Location locAdded = loc.add(2, 0, 0);
		World w = Bukkit.getWorld("world");
		
		if (locAdded.getBlock().getType() != Material.AIR)
			getHowFarStore(w.getBlockAt(locAdded));
		else
			alg.bubblyBlock();
	}
	
	public void getHowFarStore(Block b)
	{	
		Location loc = b.getLocation().clone();
		File file = new File(plugin.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		int count = 0, distance = 0;
		double y = loc.getBlockY();
		
		if (b.getType() != Material.AIR)
			for (double i = y; i >= 0; i++)
			{
				loc.setY(i);
				
				if (!loc.getBlock().getType().isSolid())
					break;
				
				distance++;
			}
		
		while (data.contains("locations." + count))
		{
			count++;
		}
		
		data.set("locations." + count + ".distance", distance);
		data.set("locations." + count + ".location", loc.add(0, -distance, 0));
		try {
			data.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		checkNext(b);
	}
	
	public void moveNextTick(Entity e, Location loc)
	{	
		SheepEntity.unFreezeEntity(e);
		
		new BukkitRunnable()
		{
			@Override
			public void run() 
			{	
				Vector copyPos = e.getLocation().toVector();
				Vector copyTarget = loc.toVector();
				Vector velocityCopy = copyTarget.subtract(copyPos);
				
				if (e.getLocation().distance(loc) < 0.1)
				{
				SheepEntity.freezeEntity(e);
				cancel();
				}
				
				e.setVelocity(velocityCopy.normalize().multiply(0.2));
			}
		}.runTaskTimer(plugin, 0, 0);
	}
}
