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
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftEntity;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Sheep;
import org.bukkit.entity.Slime;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import net.minecraft.server.v1_8_R3.NBTTagCompound;

public class SheepEntity 
{
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public ArrayList<Entity> getArrayList()
	{
		return entities;
	}
	
	public static void removeSheep()
	{	
		for (World world : Bukkit.getWorlds())
			for (Entity e : world.getEntities())
			{
				if (e.getType() == EntityType.SHEEP || e.getType() == EntityType.SLIME)
					e.remove();
				
				if (e.getType() == EntityType.ARMOR_STAND)
					if (e.getCustomName().equalsIgnoreCase("1") || e.getCustomName().equalsIgnoreCase("2")
							|| e.getCustomName().equalsIgnoreCase("3") || e.getCustomName().equalsIgnoreCase("4")
							|| e.getCustomName().equalsIgnoreCase("5") || e.getCustomName().equalsIgnoreCase("6")
							|| e.getCustomName().equalsIgnoreCase("7") || e.getCustomName().equalsIgnoreCase("8")
							|| e.getCustomName().equalsIgnoreCase("9") || e.getCustomName().equalsIgnoreCase("10"))
						e.remove();	
			}	
		
		entities.clear();
	}
	
	public static void spawnSheep()
	{
		Block b = Bukkit.getWorld("world").getBlockAt(-9, 200, 489); // -9, 200, 489
		Location loc = b.getLocation();
		Integer[] nums = new Integer[] {1, 2, 3, 4, 5, 6, 7, 8, 9, 10};
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
				sheep.setColor(DyeColor.BLUE);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 2:
				sheep.setColor(DyeColor.GREEN);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 3:
				sheep.setColor(DyeColor.LIGHT_BLUE);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 4:
				sheep.setColor(DyeColor.LIME);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 5:
				sheep.setColor(DyeColor.MAGENTA);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 6:
				sheep.setColor(DyeColor.ORANGE);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 7:
				sheep.setColor(DyeColor.PINK);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 8:
				sheep.setColor(DyeColor.PURPLE);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			case 9:
				sheep.setColor(DyeColor.RED);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
				break;
			default:
				sheep.setColor(DyeColor.YELLOW);
				sheep.setCustomName(String.valueOf(j));
				nameTag(sheep);
			}
			
			freezeEntity(sheep);
			entities.add(sheep);
			loc.add(2, 0, 0);
		}
	}
	
	private static void nameTag(Entity sheep)
	{
		ArmorStand as = (ArmorStand) Bukkit.getWorld("world").spawnEntity(sheep.getLocation(), EntityType.ARMOR_STAND);
		as.setCustomName(sheep.getCustomName());
		as.setCustomNameVisible(true);
		as.setGravity(false);
		as.setVisible(false);
		as.setSmall(true);

		Slime slime = (Slime) Bukkit.getWorld("world").spawnEntity(sheep.getLocation(), EntityType.SLIME);
		slime.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, 9999999, 10, false, false), true);
		slime.setSize(-2);

		slime.setPassenger(as);
		sheep.setPassenger(slime);
	}
	
	public static void freezeEntity(Entity e)
	{
		net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) e).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 1);
		nmsEn.f(compound);
	}
	
	public static void unFreezeEntity(Entity e)
	{
		net.minecraft.server.v1_8_R3.Entity nmsEn = ((CraftEntity) e).getHandle();
		NBTTagCompound compound = new NBTTagCompound();
		nmsEn.c(compound);
		compound.setByte("NoAI", (byte) 0);
		nmsEn.f(compound);
	}
}
