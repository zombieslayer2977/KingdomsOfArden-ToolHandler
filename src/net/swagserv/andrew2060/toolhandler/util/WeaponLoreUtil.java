package net.swagserv.andrew2060.toolhandler.util;

import java.text.DecimalFormat;
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
		lore.add(2, ChatColor.GRAY + "Bonus Damage: " + bonus);
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setLifeSteal(int amount, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(3);
		lore.add(3,ChatColor.GRAY + "Life Steal: " + amount + " Health/Hit");
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
	public static void setCritChance(double bonusCrit, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(4);
		lore.add(4, ChatColor.GRAY + "Critical Strike Chance: " + dF.format(bonusCrit) + "%");
		meta.setLore(lore);
		weapon.setItemMeta(meta);
		return;
	}
}
