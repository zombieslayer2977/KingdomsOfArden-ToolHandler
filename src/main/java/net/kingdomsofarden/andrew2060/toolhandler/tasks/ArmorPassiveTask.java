package net.kingdomsofarden.andrew2060.toolhandler.tasks;

import java.util.List;

import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.util.ModUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.scheduler.BukkitRunnable;

import com.herocraftonline.heroes.util.Util;

public class ArmorPassiveTask extends BukkitRunnable {
    
    private ModManager modmanager;

    public ArmorPassiveTask(ModManager modmanager) {
        this.modmanager = modmanager;
    }

    @Override
    public void run() {
        Player[] players = Bukkit.getServer().getOnlinePlayers();
        for(Player p : players) {
            ItemStack[] armor = p.getInventory().getArmorContents();
            for(ItemStack piece : armor) {
                if(piece == null) {
                    continue;
                } else if(!Util.isArmor(piece.getType())) {
                    continue; 
                } else {
                   List<String> mods = ModUtil.getArmorMods(piece);
                   for(String name : mods) {
                       ArmorMod mod = modmanager.getArmorMod(name);
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
