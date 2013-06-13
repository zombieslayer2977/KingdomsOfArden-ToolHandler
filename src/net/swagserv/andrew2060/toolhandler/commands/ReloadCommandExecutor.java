package net.swagserv.andrew2060.toolhandler.commands;

import net.swagserv.andrew2060.toolhandler.ToolHandlerPlugin;
import net.swagserv.andrew2060.toolhandler.mods.ModManager;
import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ReloadCommandExecutor implements CommandExecutor {
	private ToolHandlerPlugin plugin;

	public ReloadCommandExecutor(ToolHandlerPlugin plugin) {
		this.plugin = plugin;
	}
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(command.getName().equalsIgnoreCase("toolhandler")) {
			if(!plugin.permission.has(sender, "toolhandler.reload")) {
				sender.sendMessage("No Permission!");
				return true;
			}
			plugin.setModManager(new ModManager(plugin));
			sender.sendMessage("Mods reloaded");
		}
		return true;
	}

}

