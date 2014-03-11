package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedToolInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

import org.bukkit.inventory.ItemStack;

public class ModUtil {

 
    /**
     * Adds a specified weapon mod to a weapon itemstack
     * 
     * @param weapon - ItemStack representing the weapon to add a mod to 
     * @param mod - WeaponMod to Add
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack), null if no space or improper type
     */

    public static ItemStack addWeaponMod(ItemStack weapon, WeaponMod mod) {
        switch(weapon.getType()) {
        case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
            CachedWeaponInfo cachedItem = ToolHandlerPlugin.instance.getCacheManager().getCachedWeaponInfo(weapon);
            return cachedItem.addMod(mod);
        }
        default: {
            throw new IllegalArgumentException("Attempted to add weapon mod to a " + weapon.getType().toString());
        }
        }
        
    }

    /**
     * Adds a specified scythe mod to a scythe itemstack
     * 
     * @param scythe - ItemStack representing the scythe to add a mod to 
     * @param mod - ScytheMod to Add
     * @return -1 for invalid input scythe, 0 for all mod slots full, 1 for normal operation
     */
    public static ItemStack addScytheMod(ItemStack scythe, ScytheMod mod) {
        throw new UnsupportedOperationException("Not implemented yet");

    }
    /**
     * Adds a specified armor mod to a armor itemstack
     * 
     * @param armor - ItemStack representing the weapon to add a mod to 
     * @param mod - ArmorMod to Add
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack), null if no space
     */
    public static ItemStack addArmorMod(ItemStack armor, ArmorMod mod) {
        switch(armor.getType()) {
        
        case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
            CachedArmorInfo cachedItem = ToolHandlerPlugin.instance.getCacheManager().getCachedArmorInfo(armor);
            return cachedItem.addMod(mod);
        }
        default: {
            throw new IllegalArgumentException("Attempted to add armor mod to a " + armor.getType().toString());
        }
        
        }
        
    }
    /**
     * Adds a specified tool mod to a tool itemstack
     * 
     * @param tool - ItemStack representing the tool to add a mod to 
     * @param mod - ToolMod to Add
     * @return the ItemStack with the inserted mod (may be different if it was not a nms ItemStack), null if no space
     */
    public static ItemStack addToolMod(ItemStack tool, ToolMod mod) {
        switch(tool.getType()) {
        
        case DIAMOND_AXE: case DIAMOND_PICKAXE: case DIAMOND_SPADE: case IRON_AXE: case IRON_PICKAXE: case IRON_SPADE: case GOLD_AXE: case GOLD_PICKAXE: case GOLD_SPADE: case STONE_AXE: case STONE_PICKAXE: case STONE_SPADE: case WOOD_AXE: case WOOD_PICKAXE: case WOOD_SPADE: {
            CachedToolInfo cachedItem = ToolHandlerPlugin.instance.getCacheManager().getCachedToolInfo(tool);
            return cachedItem.addMod(mod);
        }
        default: {
            throw new IllegalArgumentException("Attempted to add tool mod to a " + tool.getType().toString());
        }
        
        }
        
    }
    /**
     * Adds a mod slot to the specified ItemStack
     * @param item Item to add a mod slot to.
     * @param multiplier multiplies success chance by this number to determine true success chance
     * @return null for breaking, changed itemstack otherwise
     */
    public static ItemStack addModSlot(ItemStack item, double multiplier) {
        CachedItemInfo itemInfo = ToolHandlerPlugin.instance.getCacheManager().getCachedInfo(item);
        //Gets number of currently added (not part of default lore) mod slots
        int modSlotsAdditional = 1;
        modSlotsAdditional += itemInfo.getNumBonusSlots();
        double successChance = 100/(Math.pow(2, modSlotsAdditional));
        int powTen = 0;
        while(successChance % 10 != 0) {
            powTen++;
            successChance *= 10;
        }
        Random rand = new Random();
        int roll = rand.nextInt((int) (100*Math.pow(10, powTen)));
        if(!(roll < successChance)) {
            return null;
        }
        return itemInfo.addModSlot();
    }



}
