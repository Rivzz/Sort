package org.janney.sort;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;
import org.janney.sort.commands.Placeholder;
import org.janney.sort.gui.GuiModify;

/*
 * Update from Ace
 */

public class Sort extends JavaPlugin
{
	private File file;
	private FileConfiguration fileConfig;
	
	@Override
	public void onEnable()
	{
		getCommand("sort").setExecutor(new Placeholder(this));
		getServer().getPluginManager().registerEvents(new GuiModify(), this);
		
		generateConfig("config");
		generateConfig("data");
	}
	
	private void generateConfig(String name)
	{
		file = new File(getDataFolder(), name + ".yml");
		
		if (!file.exists())
		{
			System.out.println("Generating config file " + name);
			
			file.getParentFile().mkdirs();
			saveResource(name + ".yml", false);
		}
		
		fileConfig = new YamlConfiguration();
		
		try {
			fileConfig.load(file);
		} catch (IOException | InvalidConfigurationException e) {
			e.printStackTrace();
		}
	}
}
