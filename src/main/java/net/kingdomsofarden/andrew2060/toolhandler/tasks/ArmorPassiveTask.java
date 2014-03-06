package net.kingdomsofarden.andrew2060.toolhandler.tasks;

import java.util.UUID;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.CacheManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.herocraftonline.heroes.util.Util;

public class ArmorPassiveTask extends BukkitRunnable {
    
    private ToolHandlerPlugin plugin;
    private CacheManager cacheManager;
    private ModManager modmanager;

    public ArmorPassiveTask(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
        this.modmanager = plugin.getModManager();
        this.cacheManager = plugin.getCacheManager();
    }

    @Override
    public void run() {
        Player[] players = plugin.getServer().getOnlinePlayers();
        for(Player p : players) {
            ItemStack[] armor = p.getInventory().getArmorContents();
            for(ItemStack piece : armor) {
                if(piece == null) {
                    continue;
                } else if(!Util.isArmor(piece.getType())) {
                    continue; 
                } else {
                   UUID[] mods = cacheManager.getCachedArmorInfo(piece).getMods();
                   for(UUID id : mods) {
                       ArmorMod mod = modmanager.getArmorMod(id);
                       if(mod == null) {
                           continue;
                       } else {
                           mod.executeOnTick(p);
                       }
                   }
                   mods = null;
                }
            } 
        }
        players = null;
    }

}
