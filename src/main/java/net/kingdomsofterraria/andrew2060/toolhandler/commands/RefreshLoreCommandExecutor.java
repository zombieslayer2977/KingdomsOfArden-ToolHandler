package net.kingdomsofterraria.andrew2060.toolhandler.commands;

import net.kingdomsofterraria.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofterraria.andrew2060.toolhandler.util.GeneralLoreUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class RefreshLoreCommandExecutor implements CommandExecutor {

	private ToolHandlerPlugin plugin;

	public RefreshLoreCommandExecutor(ToolHandlerPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Cannot be run from console!");
			return true;
		}
		if(command.getName().equalsIgnoreCase("refreshtoollore")) {
			if(!plugin.permission.has(sender, "toolhandler.refresh")) {
				sender.sendMessage("No Permission!");
				return true;
			}
			GeneralLoreUtil.updateLore(((Player)sender).getItemInHand());
			sender.sendMessage("Command Executed Successfully");
		}
		return true;
	}
}
