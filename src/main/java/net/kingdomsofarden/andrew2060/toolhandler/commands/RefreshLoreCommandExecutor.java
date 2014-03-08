package net.kingdomsofarden.andrew2060.toolhandler.commands;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.ArmorLoreUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.SerializationUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.WeaponLoreUtil;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

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
            ItemStack item = ((Player)sender).getItemInHand();
            switch(item.getType()) {

            case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
                WeaponLoreUtil.write(CachedWeaponInfo.fromString(item, SerializationUtil.deserializeFromLore(item)), item);
                break;
            }
            case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
                ArmorLoreUtil.write(CachedArmorInfo.fromString(item, SerializationUtil.deserializeFromLore(item)), item);
            }
            default: {
                break;
            }

            }
            
            //TODO: Add support for tools/axes

            sender.sendMessage("Command Executed Successfully");
        }
        return true;
    }
}
