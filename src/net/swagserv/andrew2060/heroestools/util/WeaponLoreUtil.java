package net.swagserv.andrew2060.heroestools.util;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class WeaponLoreUtil {
	static DecimalFormat dF = new DecimalFormat("###.##");
	public static int getBonusDamage(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String intParse = lore.get(2).replace("Bonus Damage:", "").replace(" ","");
		
		int bonus = Integer.parseInt(intParse);
		return bonus;
	}
	public static int getLifeSteal(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String intParse = lore.get(3).replace("Life Steal:", "").replace("Health/Hit","").replace(" ","");
		int ls = Integer.parseInt(intParse);
		return ls;
	}
	public static double getCritChance(ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		String doubleParse = lore.get(4).replace("Critical Strike Chance:", "").replace("%","").replace(" ","");
		double crit = Double.parseDouble(doubleParse);
		return crit;
	}
	public static void setBonusDamage(int bonus, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(2);
		lore.add(2,"Bonus Damage: " + bonus);
		meta.setLore(lore);
		return;
	}
	public static void setLifeSteal(int amount, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(3);
		lore.add(3,"Life Steal: " + amount + " Health/Hit");
		meta.setLore(lore);
		return;
	}
	public static void setCritChance(double currentCrit, ItemStack weapon) {
		ItemMeta meta = weapon.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(4);
		lore.add(4, "Critical Strike Chance: " + dF.format(currentCrit) + "%");
		meta.setLore(lore);
		return;
	}
}
