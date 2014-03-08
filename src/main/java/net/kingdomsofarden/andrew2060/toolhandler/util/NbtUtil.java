package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.EmptyModSlot;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.AttributeStorage;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;


public class NbtUtil {

    public static void writeAttributes(ItemStack item, CachedItemInfo data) throws ItemStackChangedException {
        switch(item.getType()) {
        
        case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            WeaponLoreUtil.write((CachedWeaponInfo)data, item);
            break;
        }
        case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
            ArmorLoreUtil.write((CachedArmorInfo)data, item);
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

    public static String getAttributes(ItemStack item) throws ItemStackChangedException {
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
            String parseableAttribute1 = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]","");
            String parseableAttribute2 = ChatColor.stripColor(lore.get(3)).replaceAll("[^.0-9]","");
            String parseableAttribute3 = ChatColor.stripColor(lore.get(4)).replaceAll("[^.0-9]","");
            double quality = 0.00;
            double attribute1 = Double.parseDouble(parseableAttribute1);
            double attribute2 = Double.parseDouble(parseableAttribute2);
            double attribute3 = Double.parseDouble(parseableAttribute3);
            List<String> modNames = new ArrayList<String>();
            for(int i = 6; i < lore.size(); i++) {
                String parseableMod = lore.get(i);
                if(!parseableMod.contains(ChatColor.GOLD +"")) {
                    if(parseableMod.contains(ChatColor.DARK_GRAY + "")) {
                        if(parseableMod.contains(ChatColor.MAGIC + "")) {
                            modNames.add("AddedModSlot");
                        } else {
                            modNames.add("IncludedModSlot");
                        }
                    }
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
            dataBuilder.append(dF.format(attribute1));
            dataBuilder.append(":");
            dataBuilder.append(dF.format(attribute2));
            dataBuilder.append(":");
            dataBuilder.append(dF.format(attribute3));
            ModManager modman = ToolHandlerPlugin.instance.getModManager();
            int emptyModSlots = 0;
            int emptyBonusModSlots = 0;
            for(String modName : modNames) {
                WeaponMod mod = modman.getWeaponMod(modName);
                if(mod != null) {
                    dataBuilder.append(":");
                    dataBuilder.append(mod.modUUID.toString());
                } else {
                    if(modName.equals("AddedModSlot")) {
                        emptyBonusModSlots++;
                    } else if(modName.equals("IncludedModSlot")) {
                        emptyModSlots++;
                    }
                }
            }
            for(int i = 0; i < emptyModSlots; i++) {
                dataBuilder.append(":");
                dataBuilder.append(EmptyModSlot.baseId);
            }
            for(int i = 0; i < emptyBonusModSlots; i++) {
                dataBuilder.append(":");
                dataBuilder.append(EmptyModSlot.bonusId);
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

