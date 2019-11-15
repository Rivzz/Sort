package org.janney.sort.world;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

public class Holograms 
{
	private Plugin plugin;
	
	public Holograms(Plugin plugin)
	{
		this.plugin = plugin;
	}
	
	public void createHologram(Player p, String text)
	{
		
		Location loc = p.getLocation().add(0, -.5, 0);
		ArmorStand as = (ArmorStand) Bukkit.getWorld("world").spawnEntity(loc, EntityType.ARMOR_STAND);
		
		File file = new File(plugin.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		int i = 0;
		
		while (data.contains("holograms." + i + ".location"))
		{
			i++;
		}
		
		data.set("holograms." + i + ".location", loc);
		data.set("holograms." + i + ".text", text);

		try {
			data.save(file);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		as.setCustomName(ChatColor.translateAlternateColorCodes('&', text));
		as.setCustomNameVisible(true);
		as.setGravity(false);
		as.setVisible(false);
		as.setSmall(true);
		
		p.sendMessage("Created Hologram: " + ChatColor.translateAlternateColorCodes('&', text));
	}
	
	public void deleteHologram(Player p, int i)
	{
		File file = new File(plugin.getDataFolder(), "data.yml");
		FileConfiguration data = YamlConfiguration.loadConfiguration(file);
		
		if (data.contains("holograms." + i + ".location"))
		{
			for (World world : Bukkit.getWorlds())
				for (Entity e : world.getEntities())
					if (e.getType() == EntityType.ARMOR_STAND)
						if (e.getCustomName().equalsIgnoreCase(ChatColor.translateAlternateColorCodes('&', data.getString("holograms." + i + ".text"))))
							e.remove();
			
			p.sendMessage("Deleted hologram #" + i);
			data.set("holograms." + i , null);
			
			try {
				data.save(file);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		else
			p.sendMessage("Hologram #" + i + " doesn't exist");
	}
}
