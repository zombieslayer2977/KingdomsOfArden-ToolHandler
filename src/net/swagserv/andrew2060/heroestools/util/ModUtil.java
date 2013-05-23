package net.swagserv.andrew2060.heroestools.util;

import java.util.ArrayList;
import java.util.List;

import net.swagserv.andrew2060.heroestools.mods.ArmorMod;
import net.swagserv.andrew2060.heroestools.mods.ToolMod;
import net.swagserv.andrew2060.heroestools.mods.WeaponMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.herocraftonline.heroes.util.Util;

public class ModUtil {
	
	/**
	 * Gets a list of weapon mods on a weapon
	 * 
	 * @param weapon The ItemStack associated with a weapon
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getWeaponMods(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(weapon);
			return null;
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(weapon);
			return null;
		}
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(mod.contains("Empty")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				int endName = mod.indexOf(" ");
				String modName = mod.substring(0, endName).replace(" ", "");
				mods.add(modName);
			}
		}
		return mods;
	}
	/**
	 * Gets a list of armor mods on a armor
	 * 
	 * @param armor The ItemStack associated with a armor
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getArmorMods(ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(armor);
			return null;
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(armor);
			return null;
		}
		List<String> mods = new ArrayList<String>();
		for(int i = 6; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(mod.contains("Empty")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				int endName = mod.indexOf(" ");
				String modName = mod.substring(0, endName).replace(" ", "");
				mods.add(modName);
			}
		}
		return mods;
	}
	/**
	 * Gets a list of tool mods on a tool
	 * 
	 * @param tool The ItemStack associated with a tool
	 * @return null for no mods, otherwise an ArrayList of strings containing mod names
	 */
	public static List<String> getToolMods(ItemStack tool) {
		ItemMeta meta = tool.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(tool);
			return null;
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(tool);
			return null;
		}
		List<String> mods = new ArrayList<String>();
		for(int i = 2; i < lore.size(); i++) {
			String mod = lore.get(i);
			if(mod.contains("Empty")) {
				continue;
			} else {
				mod = ChatColor.stripColor(mod);
				int endName = mod.indexOf(" ");
				String modName = mod.substring(0, endName).replace(" ", "");
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
	 * @return -1 for invalid input weapon, 0 for all mod slots full, 1 for normal operation
	 */
	public int addWeaponMod(ItemStack weapon, WeaponMod mod) {
		switch(weapon.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return -1;
			}
		}
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(weapon);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(weapon);
		}
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				if(mod.isSlotRequired()) {
					lore.add(i, ChatColor.GOLD + mod.getName() + ChatColor.WHITE + " - " + ChatColor.GRAY + mod.getDescription());
				}
				mod.applyToWeapon(weapon);
				meta.setLore(lore);
				weapon.setItemMeta(meta);
				return 1;
			} else {
				continue;
			}
		}
		return 0;
	}
	/**
	 * Adds a specified armor mod to a armor itemstack
	 * 
	 * @param armor - ItemStack representing the armor to add a mod to 
	 * @param mod - ArmorMod to Add
	 * @return -1 for invalid input armor, 0 for all mod slots full, 1 for normal operation
	 */
	public int addArmorMod(ItemStack armor, ArmorMod mod) {
		switch(armor.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return -1;
			}
		}
		ItemMeta meta = armor.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(armor);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(armor);
		}
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				
				lore.add(i, ChatColor.GOLD + mod.getName() + ChatColor.WHITE + " - " + ChatColor.GRAY + mod.getDescription());
				mod.applyToArmor(armor);
				meta.setLore(lore);
				armor.setItemMeta(meta);
				return 1;
			} else {
				continue;
			}
		}
		return 0;
	}
	/**
	 * Adds a specified tool mod to a tool itemstack
	 * 
	 * @param tool - ItemStack representing the tool to add a mod to 
	 * @param mod - ToolMod to Add
	 * @return -1 for invalid input tool, 0 for all mod slots full, 1 for normal operation
	 */
	public int addToolMod(ItemStack tool, ToolMod mod) {
		switch(tool.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return -1;
			}
		}
		ItemMeta meta = tool.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(tool);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(tool);
		}
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				lore.add(i, ChatColor.GOLD + mod.getName() + ChatColor.WHITE + " - " + ChatColor.GRAY + mod.getDescription());
				mod.applyToTool(tool);
				meta.setLore(lore);
				tool.setItemMeta(meta);
				return 1;
			} else {
				continue;
			}
		}
		return 0;
	}
	/**
	 * Adds a mod slot to the specified ItemStack
	 * @param item Item to add a mod slot to.
	 * @return false for invalid item, true for normal operation
	 */
	public boolean addModSlot(ItemStack item) {
		if(!(Util.isArmor(item.getType()) || Util.isWeapon(item.getType()))) {
			return false;
		}
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(item);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(item);
		}
		//Get the current size of the lore list -> we add a mod slot at the end
		int size = lore.size();
		lore.add(size,ChatColor.DARK_GRAY + "[Empty Slot]");
		return true;
	}


}
