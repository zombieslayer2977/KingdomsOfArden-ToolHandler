package net.kingdomsofarden.andrew2060.toolhandler.util;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.thirdparty.comphoenix.AttributeStorage;

import org.bukkit.inventory.ItemStack;


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
        case DIAMOND_AXE: case DIAMOND_PICKAXE: case DIAMOND_SPADE: case IRON_AXE: case IRON_PICKAXE: case IRON_SPADE: case GOLD_AXE: case GOLD_PICKAXE: case GOLD_SPADE: case STONE_AXE: case STONE_PICKAXE: case STONE_SPADE: case WOOD_AXE: case WOOD_PICKAXE: case WOOD_SPADE: {
            ToolLoreUtil.write((CachedToolInfo)data,item);
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
            data = SerializationUtil.deserializeFromLore(item);
            if(data == null) {
                throw new IllegalArgumentException("Item " + item.getType().toString() + " is not a valid item!");
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

