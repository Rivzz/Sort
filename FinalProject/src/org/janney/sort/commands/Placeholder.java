package org.janney.sort.commands;

import org.bukkit.ChatColor;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import org.janney.sort.algorithms.Algorithms;
import org.janney.sort.gui.Gui;
import org.janney.sort.world.HowFar;

/*
 * Placeholder commands
 * @ [sort]: Opens GUI & runs algorithm
 */

public class Placeholder implements CommandExecutor
{
	@SuppressWarnings("unused")
	private Plugin plugin;
	private Gui gui;
	private HowFar distance;
	private Algorithms alg;
	
	public Placeholder(Plugin plugin)
	{
		this.plugin = plugin;
		gui = new Gui(plugin);
		distance = new HowFar(plugin);
		alg = new Algorithms(plugin);
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
				helpMessage(p);
			if (args.length == 1)
			{
				if (args[0].equalsIgnoreCase("bubbly"))
					gui.openGui(p, args[0]);
				
				if (args[0].equalsIgnoreCase("check"))
				{
					// Calls distance at point 0 in world
					distance.clearConfig();
					distance.getHowFarStore(b);
				}
				
				if (args[0].equalsIgnoreCase("random"))
				{
					alg.random();
				}
			}
		}
		return true;
	}

	private void helpMessage(Player p)
	{
		p.sendMessage(ChatColor.RED + "Sort Syntax: /sort (bubbly|test)");
	}
}
