package org.janney.sort.gui;

import java.util.ArrayList;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

/*
 * Prevents interaction with sorting inventory
 */

public class GuiModify implements Listener
{
	private static ArrayList<String> list = new ArrayList<String>();
	
	public ArrayList<String> getList()
	{
		return list;
	}
	
	public void setList(Player p)
	{
		list.remove(p.getName());
	}
	
	@EventHandler
	public void modify(InventoryClickEvent e)
	{
		String invName = e.getInventory().getTitle();
		
		if (invName.equalsIgnoreCase("Sorting Inventory"))
			e.setCancelled(true);
	}
	
	@EventHandler
	public void close(InventoryCloseEvent e) 
	{
		Player p = (Player) e.getPlayer();
		
		list.add(p.getName());
	}
}
