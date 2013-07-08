package net.kingdomsofarden.andrew2060.toolhandler.commands;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.ModUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.util.Util;

public class ModCommandExecutor implements CommandExecutor {

	private ToolHandlerPlugin plugin;

	public ModCommandExecutor(ToolHandlerPlugin plugin) {
		this.plugin = plugin;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		if(!(sender instanceof Player)) {
			sender.sendMessage("Cannot be run from console!");
			return true;
		}
		if(command.getName().equalsIgnoreCase("modtool")) {
			if(!plugin.permission.has(sender, "toolhandler.mod")) {
				sender.sendMessage("No Permission!");
				return true;
			}
			if(args.length != 1) {
				sender.sendMessage("You must supply one mod name to add!");
				return true;
			}
			Player p = (Player)sender;
			ItemStack item = p.getItemInHand();
			if(!(Util.isArmor(item.getType()) || Util.isWeapon(item.getType()))) {
				sender.sendMessage("Not a valid mod type!");
				return true;
			}
			ModManager modMan = plugin.getModManager();
			if(Util.isArmor(item.getType())) {
				ArmorMod mod = modMan.getArmorMod(args[0]);
				if(mod == null) {
					sender.sendMessage("Mod not found!");
					return true;
				} else {
					ModUtil.addArmorMod(item, mod);
					sender.sendMessage("Mod Successfully Added!");
					return true;
				}
			} else if(Util.isWeapon(item.getType())) {
				switch(item.getType()) {
					case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
						WeaponMod mod = modMan.getWeaponMod(args[0]);
						if(mod == null) {
							sender.sendMessage("Mod not found!");
							return true;
						} else {
							ModUtil.addWeaponMod(item, mod);
							sender.sendMessage("Mod Successfully Added!");
						}
						break;
					}
					case DIAMOND_PICKAXE: case DIAMOND_AXE: case DIAMOND_SPADE: 
					case IRON_PICKAXE: case IRON_AXE: case IRON_SPADE: 
					case GOLD_PICKAXE: case GOLD_AXE: case GOLD_SPADE: 
					case STONE_PICKAXE: case STONE_AXE: case STONE_SPADE: 
					case WOOD_PICKAXE: case WOOD_AXE: case WOOD_SPADE: { 
						ToolMod mod = modMan.getToolMod(args[0]);
						if(mod == null) {
							sender.sendMessage("Mod not found!");
							return true;
						} else {
							ModUtil.addToolMod(item, mod);
							sender.sendMessage("Mod Successfully Added!");
						}
						break;
					}
					case DIAMOND_HOE: case IRON_HOE: case GOLD_HOE: case STONE_HOE: case WOOD_HOE: {
						ScytheMod mod = modMan.getScytheMod(args[0]);
						if(mod == null) {
							sender.sendMessage("Mod not found!");
							return true;
						} else {
							ModUtil.addScytheMod(item, mod);
							sender.sendMessage("Mod Successfully Added!");
						}
						break;
					}
					default: {
						break;
					}	
				}
			}
			return true;
		}
		return true;
	}
}
