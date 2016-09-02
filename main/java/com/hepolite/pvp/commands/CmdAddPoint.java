package com.hepolite.pvp.commands;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.hepolite.pvp.PvP;
import com.hepolite.pvp.cappoints.CapturePoint;

public class CmdAddPoint implements CommandExecutor
{
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args)
	{
		if (!(sender instanceof Player) && args.length < 5)
		{
			sender.sendMessage(ChatColor.RED + "This command may only be used by a player");
			return false;
		}
		if (args.length < 1)
			return true;

		String name;
		Location location;
		if (args.length >= 5)
		{
			location = PvP.getCoreData().parseSimpleLocation(args[0] + "=" + args[1] + "=" + args[2] + "=" + args[3]);
			name = get(args, 4);
		}
		else
		{
			location = ((Player) sender).getLocation().subtract(0.5, 0.0, 0.5);
			name = get(args, 0);
		}
		if (location == null)
		{
			sender.sendMessage(ChatColor.RED + "Was unable to parse the location");
			return true;
		}

		sender.sendMessage(ChatColor.RED + "Added point " + ChatColor.WHITE + name + ChatColor.RED + " at location " + ChatColor.WHITE + String.format("%s: %.0f,%.0f,%.0f", location.getWorld().getName(), location.getX(), location.getY(), location.getZ()));
		PvP.getCoreData().addPoint(new CapturePoint(name, location));
		return true;
	}

	private final String get(String[] args, int start)
	{
		if (args.length <= start)
			return "";
		String string = args[start];
		for (int i = start + 1; i < args.length; i++)
			string += " " + args[i];
		return string;
	}
}
