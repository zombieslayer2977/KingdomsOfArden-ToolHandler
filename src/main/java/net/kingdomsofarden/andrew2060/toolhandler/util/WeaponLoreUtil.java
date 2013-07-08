package net.kingdomsofarden.andrew2060.toolhandler.util;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.mods.ModManager;
import net.kingdomsofarden.andrew2060.toolhandler.mods.typedefs.WeaponMod;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponLoreUtil {
	public static int getBonusDamage(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
		    GeneralLoreUtil.populateLoreDefaults(weapon);
		    meta = weapon.getItemMeta();
		}
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		String intParse = ChatColor.stripColor(lore.get(2)).replaceAll("[^.0-9]","");
		int bonus = Integer.parseInt(intParse);
		return bonus;
	}
	public static double getLifeSteal(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		String doubleParse = ChatColor.stripColor(lore.get(3)).replaceAll("[^.0-9]","");
		double ls = Double.parseDouble(doubleParse);
		return ls;
	}
	public static double getCritChance(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
        }
		String doubleParse = ChatColor.stripColor(lore.get(4)).replaceAll("[^.0-9]","");
		double crit = Double.parseDouble(doubleParse);
		return crit;
	}
	public static void setBonusDamage(int bonus, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(2);
		lore.add(2, ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttributeColor(bonus) + bonus);
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setLifeSteal(double d, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(3);
		lore.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttributeColor(d) + FormattingUtil.dF.format(d) + ChatColor.GRAY + " %");
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setCritChance(double bonusCrit, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
            GeneralLoreUtil.populateLoreDefaults(weapon);
            meta = weapon.getItemMeta();
        }
		List<String> lore = meta.getLore();
        if(lore.isEmpty() || !lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            GeneralLoreUtil.updateLore(weapon);
            meta = weapon.getItemMeta();
            lore = meta.getLore();
        }
		lore.remove(4);
		lore.add(4, ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttributeColor(bonusCrit) + FormattingUtil.dF.format(bonusCrit) + ChatColor.GRAY + "%");
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	/**
	 * Updates this itemstack's lore after getting all previous values: note that you will have to get an updated copy
	 * of the ItemMeta from the weapon after running this function to update.
	 * @param weapon
	 */
	public static void updateWeaponLore(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLoreDefaults(weapon);
			return;
		}
		List<String> lore = meta.getLore();
		if(lore.get(0).contains(ToolHandlerPlugin.versionIdentifier)) {
            return;
        }
		double improvementQuality = 0.00D;
		int bonusdmg = 0;
		int lifesteal = 0;
		double critchance = 0;
		List<String> modifications = new LinkedList<String>();
		//Once this is true, assumes all lore past is modification lore
		boolean reachedmodifications = false;
		for(String line : lore) {
			if(reachedmodifications) {
				modifications.add(line);
			}
			line = ChatColor.stripColor(line);
			if(line.contains("Quality")) {
				try {
					improvementQuality = Double.parseDouble(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Damage")) {
				try {
					bonusdmg = Integer.parseInt(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Life Steal")) {
				try {
					lifesteal = Integer.parseInt(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Critical")) {
				try {
					critchance = Double.parseDouble(line.replaceAll("[^.0-9]",""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Modifications")) {
				reachedmodifications = true;
			}
		}
		String improvementtext = FormattingUtil.getQualityColor(improvementQuality) + FormattingUtil.dF.format(improvementQuality) + ChatColor.GRAY;
		improvementtext = ChatColor.GRAY + "Improvement Quality: " + improvementQuality + "%";
		String bonusdmgtext = FormattingUtil.getAttributeColor(bonusdmg) + bonusdmg + ChatColor.GRAY;
		bonusdmgtext = ChatColor.GRAY + "Bonus Damage: " + bonusdmgtext;
		String lifestealtext =  FormattingUtil.getAttributeColor(lifesteal) + FormattingUtil.dF.format(lifesteal) + ChatColor.GRAY;
		lifestealtext = ChatColor.GRAY + "Life Steal: " + lifestealtext + " %";
		String crittext = FormattingUtil.getAttributeColor(critchance) + FormattingUtil.dF.format(critchance) + ChatColor.GRAY;
		crittext = ChatColor.GRAY + "Critical Strike Chance: " + crittext + "%";
		List<String> loreUpdated = new ArrayList<String>();
		loreUpdated.add(ToolHandlerPlugin.versionIdentifier + ChatColor.WHITE + "=======Item Statistics=======");
	    loreUpdated.add(improvementtext);
		loreUpdated.add(bonusdmgtext);
		loreUpdated.add(lifestealtext);
		loreUpdated.add(crittext);
		loreUpdated.add(ChatColor.WHITE + "========Modifications========");
		ModManager modMan = ((ToolHandlerPlugin) Bukkit.getPluginManager().getPlugin("Swagserv-ToolHandler")).getModManager();
		for(String toAdd : modifications ) {
            if(!toAdd.contains(ChatColor.GOLD +"")) {
                if(toAdd.contains(ChatColor.DARK_GRAY + "")) {
                    loreUpdated.add(toAdd);
                }
                continue;
            } else {
                loreUpdated.add(toAdd);
                toAdd = ChatColor.stripColor(toAdd);
                String modName = toAdd.replace(" ", "");
                WeaponMod mod = modMan.getWeaponMod(modName);
                for(String desc : mod.getDescription()) {
                    loreUpdated.add(ChatColor.GRAY + "- " + desc);
                }
            }
		}
		meta.setLore(loreUpdated);
		weapon.setItemMeta(meta);
		return;
	}
}
