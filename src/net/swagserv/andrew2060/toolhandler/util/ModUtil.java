package net.swagserv.andrew2060.toolhandler.util;

import java.util.ArrayList;
import java.util.List;

import net.swagserv.andrew2060.toolhandler.mods.typedefs.ArmorMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.ScytheMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.ToolMod;
import net.swagserv.andrew2060.toolhandler.mods.typedefs.WeaponMod;

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
			GeneralLoreUtil.populateLoreDefaults(weapon);
			return new ArrayList<String>();
		}
		List<String> lore = meta.getLore();
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
	 * @return -1 for invalid input weapon, 0 for all mod slots full, 1 for normal operation
	 */
	public static int addWeaponMod(ItemStack weapon, WeaponMod mod) {
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
			GeneralLoreUtil.populateLoreDefaults(weapon);
			meta = weapon.getItemMeta();
		}
		List<String> lore = meta.getLore();
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				if(mod.isSlotRequired()) {
					lore.remove(i);
					lore.add(i, ChatColor.GOLD + mod.getName());	
					for(int x = 0; x < mod.getDescription().length; x++) {
						lore.add(i+x+1,ChatColor.GRAY + "- " + mod.getDescription()[x]);
					}
				}
				meta.setLore(lore);
				weapon.setItemMeta(meta);
				mod.applyToWeapon(weapon);
				return 1;
			} else {
				continue;
			}
		}
		return 0;
	}
	/**
	 * Adds a specified scythe mod to a scythe itemstack
	 * 
	 * @param scythe - ItemStack representing the scythe to add a mod to 
	 * @param mod - ScytheMod to Add
	 * @return -1 for invalid input scythe, 0 for all mod slots full, 1 for normal operation
	 */
	public static int addScytheMod(ItemStack scythe, ScytheMod mod) {
		switch(scythe.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return -1;
			}
		}
		ItemMeta meta = scythe.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(scythe);
			meta = scythe.getItemMeta();
		}
		List<String> lore = meta.getLore();
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				if(mod.isSlotRequired()) {
					lore.add(i, ChatColor.GOLD + mod.getName());	
					for(int x = 0; x < mod.getDescription().length; x++) {
						lore.add(i+x+1+1,ChatColor.GRAY + "- " + mod.getDescription()[x]);
					}			
				}				
				meta.setLore(lore);
				scythe.setItemMeta(meta);
				mod.applyToScythe(scythe);
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
	public static int addArmorMod(ItemStack armor, ArmorMod mod) {
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
			GeneralLoreUtil.populateLoreDefaults(armor);
			meta = armor.getItemMeta();
		}
		List<String> lore = meta.getLore();
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				if(mod.isSlotRequired()) {
					lore.add(i, ChatColor.GOLD + mod.getName());	
					for(int x = 0; x < mod.getDescription().length; x++) {
						lore.add(i+x+1+1,ChatColor.GRAY + "- " + mod.getDescription()[x]);
					}			
				}				
				meta.setLore(lore);
				armor.setItemMeta(meta);
				mod.applyToArmor(armor);
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
	public static int addToolMod(ItemStack tool, ToolMod mod) {
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
			GeneralLoreUtil.populateLoreDefaults(tool);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLoreDefaults(tool);
		}
		for(int i = 6; i < lore.size(); i++) {
			if(lore.get(i).contains("[Empty Slot]")) {
				lore.remove(i);
				if(mod.isSlotRequired()) {
					lore.add(i, ChatColor.GOLD + mod.getName());	
					for(int x = 0; x < mod.getDescription().length; x++) {
						lore.add(i+x+1,ChatColor.GRAY + "- " + mod.getDescription()[x]);
					}				
				}				
				meta.setLore(lore);
				tool.setItemMeta(meta);
				mod.applyToTool(tool);
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
			GeneralLoreUtil.populateLoreDefaults(item);
			meta = item.getItemMeta();
		}
		List<String> lore = meta.getLore();
		//Get the current size of the lore list -> we add a mod slot at the end
		int size = lore.size();
		lore.add(size,ChatColor.DARK_GRAY + "[Empty Slot]");
		return true;
	}
	


}
