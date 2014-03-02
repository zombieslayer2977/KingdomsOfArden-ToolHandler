package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.AttributeStorage;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class NbtUtil {

    public static void writeAttributes(ItemStack item, CachedWeaponInfo data) throws ItemStackChangedException {
        switch(item.getType()) {
        
        case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            WeaponLoreUtil.write(data, item);
            break;
        }
        default: {
            break;
        }
        
        }
        String cachedData = data.toString();
        AttributeStorage storage = AttributeStorage.newTarget(item, ToolHandlerPlugin.identifier);
        storage.setData(cachedData);
        if(item != storage.getTarget()) {
            throw new ItemStackChangedException(storage.getTarget());
        } else {
            return;
        }
    }

    public static String getWeaponAttributes(ItemStack item) throws ItemStackChangedException {
        AttributeStorage storage = AttributeStorage.newTarget(item, ToolHandlerPlugin.identifier);
        String data = storage.getData();
        //Plugin tag is not present - rebuild   
        if(data == null) {
            ItemMeta meta = item.getItemMeta();
            if(!meta.hasLore()) {
                GeneralLoreUtil.populateLoreDefaults(item);
                meta = item.getItemMeta();
            }
            List<String> lore = meta.getLore();
            String parseableBonusDamage = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]","");
            String parseableLifeSteal = ChatColor.stripColor(lore.get(3)).replaceAll("[^.0-9]","");
            String parseableCritChance = ChatColor.stripColor(lore.get(4)).replaceAll("[^.0-9]","");
            double quality = 0.00;
            double bonusDamage = Double.parseDouble(parseableBonusDamage);
            double lifeSteal = Double.parseDouble(parseableLifeSteal);
            double critChance = Double.parseDouble(parseableCritChance);
            List<String> modNames = new ArrayList<String>();
            for(int i = 6; i < lore.size(); i++) {
                String parseableMod = lore.get(i);
                if(!parseableMod.contains(ChatColor.GOLD +"")) {
                    continue;
                } else {
                    parseableMod = ChatColor.stripColor(parseableMod);
                    String modName = parseableMod.replace(" ", "");
                    modNames.add(modName);
                }
            }
            DecimalFormat dF = new DecimalFormat("##.##");
            StringBuilder dataBuilder = new StringBuilder();
            dataBuilder.append(dF.format(quality));
            dataBuilder.append(":");
            dataBuilder.append(dF.format(bonusDamage));
            dataBuilder.append(":");
            dataBuilder.append(dF.format(lifeSteal));
            dataBuilder.append(":");
            dataBuilder.append(dF.format(critChance));
            ModManager modman = ToolHandlerPlugin.instance.getModManager();
            for(String modName : modNames) {
                WeaponMod mod = modman.getWeaponMod(modName);
                if(mod != null) {
                    dataBuilder.append(":");
                    dataBuilder.append(mod.modUUID.toString());
                }
            }
            data = dataBuilder.toString();
            storage.setData(data);
            if(!storage.getTarget().equals(item)) {
                ItemStackChangedException e = new ItemStackChangedException(storage.getTarget());
                throw e;
            }
        }
        return data;
    }
    
    public static class ItemStackChangedException extends Exception {

        private static final long serialVersionUID = -994887853407035957L;
        public final ItemStack newStack;
        
        public ItemStackChangedException(ItemStack newStack) {
            this.newStack = newStack;
        }

    }
}

