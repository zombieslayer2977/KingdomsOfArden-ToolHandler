package net.swagserv.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponLoreUtil {
	static DecimalFormat dF = new DecimalFormat("###.##");
	public static int getBonusDamage(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String intParse = ChatColor.stripColor(lore.get(2)).replace("Bonus Damage:", "").replace(" ","");
		int bonus = Integer.parseInt(intParse);
		return bonus;
	}
	public static int getLifeSteal(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String intParse = ChatColor.stripColor(lore.get(3)).replace("Life Steal:", "").replace("Health/Hit","").replace(" ","");
		int ls = Integer.parseInt(intParse);
		return ls;
	}
	public static double getCritChance(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String doubleParse = ChatColor.stripColor(lore.get(4)).replace("Critical Strike Chance:", "").replace("%","").replace(" ","");
		double crit = Double.parseDouble(doubleParse);
		return crit;
	}
	public static void setBonusDamage(int bonus, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(2);
		lore.add(2, ChatColor.GRAY + "Bonus Damage: " + FormattingUtil.getAttributeColor(bonus) + bonus);
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setLifeSteal(int amount, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(3);
		lore.add(3,ChatColor.GRAY + "Life Steal: " + FormattingUtil.getAttributeColor(amount) + amount + ChatColor.GRAY + " Health/Hit");
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setCritChance(double bonusCrit, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(4);
		lore.add(4, ChatColor.GRAY + "Critical Strike Chance: " + FormattingUtil.getAttributeColor(bonusCrit) + dF.format(bonusCrit) + ChatColor.GRAY + "%");
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
			if(line.contains("Quality")) {
				try {
					improvementQuality = Double.parseDouble(ChatColor.stripColor(line).replace("Improvement Quality: ", "").replace("%", ""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Damage")) {
				try {
					bonusdmg = Integer.parseInt(ChatColor.stripColor(line).replace("Bonus Damage: ", ""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Life Steal")) {
				try {
					lifesteal = Integer.parseInt(ChatColor.stripColor(line).replace("Life Steal: ", "").replace(" Health/Hit", ""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Critical")) {
				try {
					critchance = Double.parseDouble(ChatColor.stripColor(line).replace("Critical Strike Chance: ", "").replace("%", ""));
				} catch (NumberFormatException e) {
				}
			}
			if(line.contains("Modifications")) {
				reachedmodifications = true;
			}
		}
		String improvementtext = FormattingUtil.getQualityColor(improvementQuality) + dF.format(improvementQuality) + ChatColor.GRAY;
		improvementtext = ChatColor.GRAY + "Improvement Quality: " + improvementQuality + "%";
		String bonusdmgtext = FormattingUtil.getAttributeColor(bonusdmg) + bonusdmg + ChatColor.GRAY;
		bonusdmgtext = ChatColor.GRAY + "Bonus Damage: " + bonusdmgtext;
		String lifestealtext =  FormattingUtil.getAttributeColor(lifesteal) + lifesteal + ChatColor.GRAY;
		lifestealtext = ChatColor.GRAY + "Life Steal: " + lifestealtext + " Health/Hit";
		String crittext = FormattingUtil.getAttributeColor(critchance) + dF.format(critchance) + ChatColor.GRAY;
		crittext = ChatColor.GRAY + "Critical Strike Chance: " + crittext + "%";
		List<String> loreUpdated = new ArrayList<String>();
		loreUpdated.add(improvementtext);
		loreUpdated.add(ChatColor.WHITE + "=========Statistics==========");
		loreUpdated.add(bonusdmgtext);
		loreUpdated.add(lifestealtext);
		loreUpdated.add(crittext);
		loreUpdated.add(ChatColor.WHITE + "========Modifications========");
		for(String toAdd : modifications ) {
			loreUpdated.add(toAdd);
		}
		meta.setLore(loreUpdated);
		weapon.setItemMeta(meta);
		return;
	}
}
