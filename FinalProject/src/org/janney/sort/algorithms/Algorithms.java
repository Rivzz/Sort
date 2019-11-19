package org.janney.sort.algorithms;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.bukkit.util.Vector;
import org.janney.sort.gui.GuiModify;
import org.janney.sort.sheep.SheepEntity;
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
	
	public void random(Block b)
	{
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
	
	public void bubblySheep(ArrayList<Entity> entities)
	{	
		int end = entities.size();
		
		if (entities.size() == 0)
			Bukkit.broadcastMessage("No Sheep are spawned");
		else
		{
			new BukkitRunnable()
			{
				int limit = 1, sortedCount = 0;
				ArrayList<Entity> entitiesCopy = entities;
				
				@Override
				public void run()
				{	
					if (limit != end)
					{
						Sheep rightSheep = (Sheep) entitiesCopy.get(limit);
						Sheep leftSheep = (Sheep) entitiesCopy.get(limit - 1);
						
						String rightSheepName = rightSheep.getCustomName();
						String leftSheepName = leftSheep.getCustomName();
						
						int right = Integer.valueOf(rightSheepName);
						int left = Integer.valueOf(leftSheepName);
						
						if (right < left)
						{
							sortedCount = 0;
							
							sheepWalk((limit - 1), limit, entitiesCopy);
							
							Sheep rightCopy = (Sheep) entitiesCopy.get(limit);
							Sheep leftCopy = (Sheep) entitiesCopy.get(limit - 1);
							
							entitiesCopy.set(limit, leftCopy);
							entitiesCopy.set(limit - 1, rightCopy);
						}
						else
							sortedCount += 1;
						
						if (sortedCount == end)
						{
							Bukkit.broadcastMessage("All sheep are sorted");
							cancel();
						}
						
						limit++;
					}
					else
						limit = 1;
				}
			}.runTaskTimer(plugin, 0, 55);
		}
	}
	
	public void sheepWalk(int left, int right, ArrayList<Entity> entities)
	{
		for (World world : Bukkit.getWorlds())
			for (Entity e : world.getEntities())
				if (e.getType() == EntityType.SHEEP)
				{	
					Sheep sheep = (Sheep) entities.get(left);
					Sheep sheep1 = (Sheep) entities.get(right);
					
					new BukkitRunnable()
					{
						int count = 0;
						
						Location sheepLocation = sheep.getLocation();
						
						@Override
						public void run() 
						{	
							if (count == 0)
							{
								Block b1 = Bukkit.getWorld("world").getBlockAt(sheepLocation.getBlockX(), 200, 
										sheepLocation.getBlockZ() + 2);
								
								sheepLocation.add(0, 0, 2);
								
								Location loc = b1.getLocation().add(0.5, 0, 0.5);
							
								moveSheep(sheep, loc);
							}
							
							
							if (count == 1)
							{
								Block b2 = Bukkit.getWorld("world").getBlockAt(sheepLocation.getBlockX() + 2, 200, 
										sheepLocation.getBlockZ());
								
								sheepLocation.add(2, 0, 0);
								
								Location loc2 = b2.getLocation().add(0.5, 0, 0.5);
								
								moveSheep(sheep, loc2);
							}
							
							if (count == 2)
							{
								Block b3 = Bukkit.getWorld("world").getBlockAt(sheepLocation.getBlockX(), 200, 
										sheepLocation.getBlockZ() - 2);
								
								Location loc3 = b3.getLocation().add(0.5, 0, 0.5);
								
								moveSheep(sheep, loc3);
							}
							
							count++;
						}
						
					}.runTaskTimer(plugin, 0, 20);
					
					new BukkitRunnable()
					{
						int count = 0;
						
						Location sheep1Location = sheep1.getLocation();
						
						@Override
						public void run() 
						{	
							if (count == 0)
							{
								Block b1 = Bukkit.getWorld("world").getBlockAt(sheep1Location.getBlockX(), 200, 
										sheep1Location.getBlockZ() + 1);
								
								Location loc = b1.getLocation().add(0.5, 0, 0.5);
								
								sheep1Location.add(0, 0, 1);
								
								moveSheep(sheep1, loc);
							}
							
							if (count == 1)
							{
								Block b2 = Bukkit.getWorld("world").getBlockAt(sheep1Location.getBlockX() - 2, 200, 
										sheep1Location.getBlockZ());
								
								Location loc2 = b2.getLocation().add(0.5, 0, 0.5);
								
								sheep1Location.add(-2, 0, 0);
								
								moveSheep(sheep1, loc2);
							}
							
							if (count == 2)
							{
								Block b3 = Bukkit.getWorld("world").getBlockAt(sheep1Location.getBlockX(), 200, 
										sheep1Location.getBlockZ() - 1);
								
								Location loc3 = b3.getLocation().add(0.5, 0, 0.5);
								
								moveSheep(sheep1, loc3);
							}
							
							count++;
						}
						
					}.runTaskTimer(plugin, 0, 20);
				}
	}
	
	public void moveSheep(Entity e, Location loc)
	{	
		SheepEntity.unFreezeEntity(e);
		
		new BukkitRunnable()
		{
			@Override
			public void run() 
			{	
				Vector pos = e.getLocation().toVector();
				Vector target = loc.toVector();
				Vector velocity = target.subtract(pos);
				
				if (e.getLocation().distance(loc) < 0.1)
				{
				SheepEntity.freezeEntity(e);
				cancel();
				}
				
				e.setVelocity(velocity.normalize().multiply(0.2));
			}
		}.runTaskTimer(plugin, 0, 0);
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
		gui.setList(p);
		
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
