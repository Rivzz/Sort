package org.janney.sort.world;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import com.sk89q.worldedit.EditSession;
import com.sk89q.worldedit.MaxChangedBlocksException;
import com.sk89q.worldedit.Vector;
import com.sk89q.worldedit.bukkit.BukkitWorld;
import com.sk89q.worldedit.bukkit.WorldEditPlugin;
import com.sk89q.worldedit.schematic.MCEditSchematicFormat;

public class WorldCopy 
{
	@SuppressWarnings("deprecation")
	public static void copy(int name, Location loc) 
	{
		World world = Bukkit.getWorld("world");
		double x = loc.getX();
		double y = loc.getY();
		double z = loc.getZ();

		WorldEditPlugin we = (WorldEditPlugin) Bukkit.getPluginManager().getPlugin("WorldEdit");
		File schematic = new File("plugins/WorldEdit/schematics/" + name + ".schematic");
		EditSession session = we.getWorldEdit().getEditSessionFactory().getEditSession(new BukkitWorld(world), 100000);
				
		try {
			MCEditSchematicFormat.getFormat(schematic).load(schematic).paste(session, new Vector(x, y, (z + 1)), false);
		} catch (MaxChangedBlocksException | com.sk89q.worldedit.data.DataException | IOException e) {
			e.printStackTrace();
		}
	}
}
