package org.janney.sort.algorithms;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.janney.sort.gui.GuiModify;
import org.janney.sort.world.WorldCopy;

/*
 * Sorting algorithms
 * @random(): Puts visual stack in random order
 * @bubblyBlock(): Visual bubble algorithm
 * @bubblyInventory(): GUI bubble algorithm
 */

@SuppressWarnings("deprecation")
public class Algorithms
{
	private Plugin plugin;
	private GuiModify gui;
	
	public Algorithms(Plugin plugin)
	{
		this.plugin = plugin;
		gui = new GuiModify();
	}
	
	public void random()
	{
		World w = Bukkit.getWorld("world");
		Block b = w.getBlockAt(0, 200, 0);
		Location loc = b.getLocation();
		Integer[] nums = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
		List<Integer> numsList = Arrays.asList(nums);
		
		Collections.shuffle(numsList);
		
		for (int j = 0; j < numsList.size(); j++)
		{
			int i = numsList.get(j);
			
			WorldCopy.copy(11, loc);
			WorldCopy.copy(i, loc);

			loc.add(2, 0, 0);
		}
	}
	
	public void bubblyBlock()
	{	
		File file = new File(plugin.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		int count = 0;
		
		while (data.contains("locations." + count))
		{
			count++;
		}
		
		int end = count;
			
		new BukkitRunnable()
		{
			int limit = 1, sortedCount = 0;
			
			@Override
			public void run() 
			{	
				if (limit != end)
				{
					int right = data.getInt("locations." + limit + ".distance");
					int left = data.getInt("locations." + (limit - 1) + ".distance");
					
					if (right < left)
					{
						sortedCount = 0;
						
						WorldCopy.copy(11, (Location) data.get("locations." + limit + ".location")); 
						WorldCopy.copy(11, (Location) data.get("locations." + (limit - 1) + ".location"));
						
						WorldCopy.copy(right, (Location) data.get("locations." + (limit - 1) + ".location"));
						WorldCopy.copy(left, (Location) data.get("locations." + limit + ".location"));
						
						data.set("locations." + limit + ".distance", left);
						data.set("locations." + (limit - 1) + ".distance", right);
						
						try {
							data.save(file);
						} catch (IOException e) {
							e.printStackTrace();
						}
					}
					else
						sortedCount += 1;
					
					if (sortedCount == end)
					{
						Bukkit.broadcastMessage("All stacks sorted");
						cancel();
					}
					
					limit++;
				}
				else
					limit = 1;
			}
			
		}.runTaskTimer(plugin, 0, 5);
	}
	
	public void bubblyInventory(Inventory i, Player p)
	{	
		new BukkitRunnable()
		{
			int limit = 1, sortedCount = 0;
			
			@Override
			public void run() 
			{
				if (limit != 27)
				{
					if (i.getItem(limit).getType().getId() < i.getItem(limit - 1).getType().getId())
					{
						sortedCount = 0;
						
						ItemStack a = i.getItem(limit - 1);
						ItemStack b = i.getItem(limit);
						
						i.setItem(limit, a);
						i.setItem((limit - 1), b);
					}
					else
						sortedCount += 1;
					
					if (sortedCount == 27)
					{
						Bukkit.broadcastMessage("Ended Loop from count");
						cancel();
					}
					else if (gui.getList().contains(p.getName()))
					{
						Bukkit.broadcastMessage("Ended Loop from list");
						gui.setList(p);
						cancel();
					}	
					
					limit++;
				}
				else
					limit = 1;
			}
			
		}.runTaskTimer(plugin, 0, 0);
	}
}
