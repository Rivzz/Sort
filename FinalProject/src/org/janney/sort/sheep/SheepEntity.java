package org.janney.sort.sheep;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.DyeColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.craftbukkit.v1_8_R1.entity.CraftEntity;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.entity.Sheep;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.janney.sort.world.HowFar;

import net.minecraft.server.v1_8_R1.NBTTagCompound;

public class SheepEntity 
{
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	private Plugin plugin;
	private HowFar distance;
	
	public SheepEntity(Plugin plugin)
	{
		this.plugin = plugin;
		distance = new HowFar(plugin);
	}
	
	public static void removeSheep()
	{	
		for (World world : Bukkit.getWorlds())
			for (Entity e : world.getEntities())
				if (e.getType() == EntityType.SHEEP)
				{
					e.remove();
					entities.clear();
				}
	}
	
	public void sheepWalk(Player p)
	{
		for (World world : Bukkit.getWorlds())
			for (Entity e : world.getEntities())
				if (e.getType() == EntityType.SHEEP)
				{	
					Sheep sheep = (Sheep) entities.get(0);
					Sheep sheep1 = (Sheep) entities.get(1);
					
					new BukkitRunnable()
					{
						int count = 0;
						
						@Override
						public void run() 
						{	
							if (count == 0)
							{
								Block b1 = Bukkit.getWorld("world").getBlockAt(0, 200, 17);
								Location loc = b1.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep, loc);
							}
							
							if (count == 1)
							{
								Block b2 = Bukkit.getWorld("world").getBlockAt(2, 200, 17);
								Location loc2 = b2.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep, loc2);
							}
							
							if (count == 2)
							{
								Block b3 = Bukkit.getWorld("world").getBlockAt(2, 200, 15);
								Location loc3 = b3.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep, loc3);
							}
							
							count++;
						}
						
					}.runTaskTimer(plugin, 0, 20*2);
					
					new BukkitRunnable()
					{
						int count = 0;
						
						@Override
						public void run() 
						{	
							if (count == 0)
							{
								Block b1 = Bukkit.getWorld("world").getBlockAt(2, 200, 17);
								Location loc = b1.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep1, loc);
							}
							
							if (count == 1)
							{
								Block b2 = Bukkit.getWorld("world").getBlockAt(0, 200, 17);
								Location loc2 = b2.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep1, loc2);
							}
							
							if (count == 2)
							{
								Block b3 = Bukkit.getWorld("world").getBlockAt(0, 200, 15);
								Location loc3 = b3.getLocation().add(0.5, 0, 0.5);
								
								distance.moveNextTick(sheep1, loc3);
							}
							
							count++;
						}
						
					}.runTaskTimer(plugin, 0, 20*2);
				}
	}
	
	public static void spawnSheep()
	{
		Block b = Bukkit.getWorld("world").getBlockAt(0, 200, 15);
		Location loc = b.getLocation();
		Integer[] nums = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15};
		List<Integer> numsList = Arrays.asList(nums);
		
		Collections.shuffle(numsList);
		
		for (int i = 0; i < numsList.size(); i++)
		{	
			Location locClone = loc.clone();
			Sheep sheep = (Sheep) Bukkit.getWorld("world").spawnEntity(locClone.add(0.5, 0, 0.5), EntityType.SHEEP);
			int j = numsList.get(i);
			
			switch (j)
			{
			case 1:
				sheep.setColor(DyeColor.BLACK);
				break;
			case 2:
				sheep.setColor(DyeColor.BLUE);
				break;
			case 3:
				sheep.setColor(DyeColor.BROWN);
				break;
			case 4:
				sheep.setColor(DyeColor.CYAN);
				break;
			case 5:
				sheep.setColor(DyeColor.GRAY);
				break;
			case 6:
				sheep.setColor(DyeColor.GREEN);
				break;
			case 7:
				sheep.setColor(DyeColor.LIGHT_BLUE);
				break;
			case 8:
				sheep.setColor(DyeColor.LIME);
				break;
			case 9:
				sheep.setColor(DyeColor.MAGENTA);
				break;
			case 10:
				sheep.setColor(DyeColor.ORANGE);
				break;
			case 11:
				sheep.setColor(DyeColor.PINK);
				break;
			case 12:
				sheep.setColor(DyeColor.PURPLE);
				break;
			case 13:
				sheep.setColor(DyeColor.RED);
				break;
			case 14:
				sheep.setColor(DyeColor.WHITE);
				break;
			default:
				sheep.setColor(DyeColor.YELLOW);
			}
			
			freezeEntity(sheep);
			entities.add(sheep);
			loc.add(2, 0, 0);
		}
	}
	
	  public static void freezeEntity(Entity e)
	  {
	      net.minecraft.server.v1_8_R1.Entity nmsEn = ((CraftEntity) e).getHandle();
	      NBTTagCompound compound = new NBTTagCompound();
	      nmsEn.c(compound);
	      compound.setByte("NoAI", (byte) 1);
	      nmsEn.f(compound);
	  }
	  
	  public static void unFreezeEntity(Entity e)
	  {
	      net.minecraft.server.v1_8_R1.Entity nmsEn = ((CraftEntity) e).getHandle();
	      NBTTagCompound compound = new NBTTagCompound();
	      nmsEn.c(compound);
	      compound.setByte("NoAI", (byte) 0);
	      nmsEn.f(compound);
	  }
}
