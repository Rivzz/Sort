package org.janney.sort.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;
import org.janney.sort.algorithms.Algorithms;
import org.janney.sort.gui.Gui;
import org.janney.sort.sheep.SheepEntity;
import org.janney.sort.world.Holograms;
import org.janney.sort.world.HowFar;

/*
 * Placeholder commands
 * @ [sort]: Root command
 * @ [gui] [bubbly]: Bubble sort for GUI
 * @ [visual] [bubbly]: Bubble sort for VISUAL
 * @ [random]: Organizes visual sort RANDOM
 */

public class Placeholder implements CommandExecutor
{
	private Plugin plugin;
	private Gui gui;
	private HowFar distance;
	private Algorithms alg;
	private SheepEntity sheep;
	private Holograms holo;
	
	public Placeholder(Plugin plugin)
	{
		this.plugin = plugin;
		gui = new Gui(plugin);
		distance = new HowFar(plugin);
		alg = new Algorithms(plugin);
		sheep = new SheepEntity();
		holo = new Holograms(plugin);
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String commandLabel, String[] args) 
	{
		if (!(sender instanceof Player))
			return true;
		
		Player p = (Player) sender;
		World w = p.getWorld();
		Block b = w.getBlockAt(0, 200, 0);
		
		if (commandLabel.equalsIgnoreCase("sort"))
		{
			if (args.length == 0)
			{
				p.sendMessage(ChatColor.RED + "Sort Syntax: /sort (visual|gui) (bubbly)");
				p.sendMessage(ChatColor.RED + "Sort Syntax: /sort (random)");
			}
			
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("random"))
					alg.random();
				
				if (args[0].equalsIgnoreCase("sheep"))
				{
					SheepEntity.removeSheep();
					
					new BukkitRunnable()
					{
						@Override
						public void run()
						{
							SheepEntity.spawnSheep();
						}
					}.runTaskLater(plugin, 5);
					
					new BukkitRunnable()
					{
						@Override
						public void run() 
						{
							alg.bubblySheep(sheep.getArrayList());
						}
					}.runTaskLater(plugin, 20);
				}
			}
			
			if (args.length == 2)
			{
				if (args[0].equalsIgnoreCase("gui"))
					if (args[1].equalsIgnoreCase("bubbly"))
						gui.openGui(p, args[1]);
				
				if (args[0].equalsIgnoreCase("visual"))
					if (args[1].equalsIgnoreCase("bubbly"))
					{
						distance.clearConfig();
						distance.getHowFarStore(b);
					}
			}
			
			if (args.length >= 3)
			{
				if (args[0].equalsIgnoreCase("hologram"))
				{
					if (args[1].equalsIgnoreCase("create"))
						if (!args[2].isEmpty())
						{
							String string = "";
							
							for (int i = 2; i < args.length; i++)
							{
								string = string + args[i] + " ";
							}
							
							holo.createHologram(p, string);
							
						}
					
					if (args[1].equalsIgnoreCase("delete"))
						if (!args[2].isEmpty())
						{
							try {
								holo.deleteHologram(p, Integer.valueOf(args[2]));
							} catch (NumberFormatException e) {
								p.sendMessage("Hologram " + args[2] + " doesn't exist");
							}
						}
				}
				
			}
		}
		return true;
	}
}
