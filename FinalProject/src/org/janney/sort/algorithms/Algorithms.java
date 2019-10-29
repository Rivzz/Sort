package org.janney.sort.algorithms;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.janney.sort.SharedHash;
import org.janney.sort.gui.GuiModify;
import org.janney.sort.world.WorldCopy;

/*
 * Sorting algorithms
 * @ [bubbly]: Lowest - Highest item id
 */

@SuppressWarnings("deprecation")
public class Algorithms extends SharedHash
{
	private Plugin plugin;
	private GuiModify gui;
	
	public Algorithms(Plugin plugin)
	{
		this.plugin = plugin;
		gui = new GuiModify();
	}
	
	public void bubblyBlock()
	{	
		Bukkit.broadcastMessage(hash.toString());
		
		new BukkitRunnable()
		{
			int limit = hash.size(), count = 1, sortedCount = 0;
			
			@Override
			public void run() 
			{	
				if (count != limit)
				{	
					Location right = hash.get(count);
					Location left = hash.get(count - 1);
					
					Bukkit.broadcastMessage("Right Location value is " + right);
					Bukkit.broadcastMessage("Left Location value is " + left);
					
					int rightAmount = getHowFar(right.getBlock());
					int leftAmount = getHowFar(left.getBlock());
					
					Bukkit.broadcastMessage("Right Amount value is " + rightAmount);
					Bukkit.broadcastMessage("Left Amount value is " + leftAmount);
					
					Location pasteRight = right.add(0, 0, 1);
					Location pasteLeft = left.add(0, 0, 1);
					
					if (rightAmount < leftAmount) 
					{
						sortedCount = 0;
						
						Bukkit.broadcastMessage("Right is great than left *Swapping*");
				
						WorldCopy.copy(11, pasteLeft);
						WorldCopy.copy(11, pasteRight);
						WorldCopy.copy(rightAmount, pasteLeft);
						WorldCopy.copy(leftAmount, pasteRight);
						
						hash.remove(count);
						hash.remove(count - 1);
						
						hash.put(count, left);
						hash.put(count - 1, right);
						
						Bukkit.broadcastMessage(hash.toString());
					}
					else
						sortedCount += 1;
					
					if (sortedCount == limit)
					{
						Bukkit.broadcastMessage("Sorted");
						hash.clear();
						cancel();
					}
					
					count++;
				}
				else
					count = 1;
			}
			
		}.runTaskTimer(plugin, 0, 20*2);
	}
	
	/*
	 * [bubbly]: Looped @ 0 ticks
	 * @ [limit]: Slots in the inventory
	 * @ [sortedCount]: If count == inventory size, cancel
	 */
	
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
