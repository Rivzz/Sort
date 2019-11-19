package org.janney.sort.gui;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.janney.sort.algorithms.Algorithms;

/*
 * Opens virtual inventory
 * @build(): Names items in slot origin
 * @openGui(): Opens inventory with random items from config.yml
 */

public class Gui 
{
	private Plugin plugin;
	private Algorithms alg;
	
	public Gui(Plugin plugin)
	{
		this.plugin = plugin;
		alg = new Algorithms(plugin);
	}
	
	private ItemStack build(String name, Material material)
	{
		ItemStack item = new ItemStack(material);
		ItemMeta meta = item.getItemMeta();
		
		meta.setDisplayName(name);
		item.setItemMeta(meta);
		
		return item;
	}
	
	public void openGui(Player p, String algorithm)
	{
		int index = 0;
		Random random = new Random();
		File file = new File(plugin.getDataFolder(), "config.yml");
		FileConfiguration configFile = YamlConfiguration.loadConfiguration(file);
		Inventory i = Bukkit.createInventory(null, 27, "Sorting Inventory");
		
		List<String> list = new ArrayList<String>();
		
		list = configFile.getStringList("items");
		
		for (int j = 0; j < i.getSize(); j++)
		{
			index = random.nextInt(list.size());
			
			i.setItem(j, build("Item # " + (j + 1), Material.valueOf(list.get(index).toUpperCase())));
		}
		
		p.openInventory(i);
		
		new BukkitRunnable()
		{
			@Override
			public void run() 
			{
				switch (algorithm)
				{
				case "bubble":
					alg.bubblyInventory(i, p);
					break;
				}
			}
		}.runTaskLater(plugin, 20*2);
	}
}
