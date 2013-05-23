package net.swagserv.andrew2060.heroestools.util;

import java.util.ArrayList;
import java.util.List;

import net.swagserv.andrew2060.heroestools.mods.WeaponMod;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ModUtil {

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
				lore.add(i, ChatColor.GOLD + mod.getName() + ChatColor.WHITE + " - " + ChatColor.GRAY + mod.getDescription());
				mod.applyToWeapon(weapon);
				return 1;
			} else {
				continue;
			}
		}
		return 0;
	}

}
