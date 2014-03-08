package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedArmorInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModUtil {
	
	/**
	 * @deprecated use nbt caching instead
	 * Gets a list of weapon mods on a weapon
	 * 
	 * @param weapon The ItemStack associated with a weapon
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getWeaponMods(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(weapon);
			return new ArrayList<String>();
		}
		List<String> lore = meta.getLore();
		if(!lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(!mod.contains(ChatColor.GOLD +"")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				String modName = mod.replace(" ", "");
				mods.add(modName);
			}
		}
		return mods;
	}
	/**
	 * @deprecated use nbt caching instead
	 * Gets a list of armor mods on a armor
	 * 
	 * @param armor The ItemStack associated with a armor
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getArmorMods(ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(armor);
			return new ArrayList<String>();
		}
		List<String> lore = meta.getLore();
		if(!lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(armor);
            meta = armor.getItemMeta();
            lore = meta.getLore();
        }
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(!mod.contains(ChatColor.GOLD +"")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				String modName = mod.replace(" ", "");
				mods.add(modName);
			}
		}
		return mods;
	}
	/**
     * @deprecated use nbt caching instead
	 * Gets a list of tool mods on a tool
	 * 
	 * @param tool The ItemStack associated with a tool
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getToolMods(ItemStack tool) {
		ItemMeta meta = tool.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(tool);
			return new ArrayList<String>();
		}
		List<String> lore = meta.getLore();
		if(!lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(tool);
            meta = tool.getItemMeta();
            lore = meta.getLore();
        }
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(!mod.contains(ChatColor.GOLD +"")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				String modName = mod.replace(" ", "");
				mods.add(modName);
			}
		}		
		return mods;
	}
	/**
	 * @deprecated use nbt caching instead
	 * Gets a list of scythe mods on a scythe
	 * 
	 * @param scythe The ItemStack associated with a tool
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getScytheMods(ItemStack scythe) {
		ItemMeta meta = scythe.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(scythe);
			return new ArrayList<String>();
		}
		List<String> lore = meta.getLore();
		if(!lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(scythe);
            meta = scythe.getItemMeta();
            lore = meta.getLore();
        }
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(!mod.contains(ChatColor.GOLD +"")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				String modName = mod.replace(" ", "");
				mods.add(modName);
			}
		}		
		return mods;
	}
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
				break;
			}
			default: {
                throw new IllegalArgumentException("Attempted to add weapon mod to a " + weapon.getType().toString());
			}
		}
		CachedWeaponInfo cachedItem = ToolHandlerPlugin.instance.getCacheManager().getCachedWeaponInfo(weapon);
		return cachedItem.addMod(mod);
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
				break;
			}
			default: {
				throw new IllegalArgumentException("Attempted to add armor mod to a " + armor.getType().toString());
			}
		}
		CachedArmorInfo cachedItem = ToolHandlerPlugin.instance.getCacheManager().getCachedArmorInfo(armor);
        return cachedItem.addMod(mod);
	}
	/**
	 * Adds a specified tool mod to a tool itemstack
	 * 
	 * @param tool - ItemStack representing the tool to add a mod to 
	 * @param mod - ToolMod to Add
	 * @return -1 for invalid input tool, 0 for all mod slots full, 1 for normal operation
	 */
	public static ItemStack addToolMod(ItemStack tool, ToolMod mod) {
		throw new UnsupportedOperationException("Not implemented yet");
	}
	/**
	 * Adds a mod slot to the specified ItemStack
	 * @param item Item to add a mod slot to.
	 * @param multiplier multiplies success chance by this number to determine true success chance
	 * @return false for breaking, true for success
	 */
	public static boolean addModSlot(ItemStack item, double multiplier) {
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
		    return false;
		}
		itemInfo.addModSlot();
		return true;
	}
	


}
