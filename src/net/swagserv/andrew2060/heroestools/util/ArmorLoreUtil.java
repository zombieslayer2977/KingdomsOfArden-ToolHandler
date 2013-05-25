package net.swagserv.andrew2060.heroestools.util;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorLoreUtil {
	static DecimalFormat dF = new DecimalFormat("###.##");
	public static double getMagicResistRating(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		String doubleParse = ChatColor.stripColor(lore.get(2)).replace("Magical Resistance Rating:", "").replace("%", "").replace(" ","");
		Double magicalRating = Double.parseDouble(doubleParse)*0.01;
		return magicalRating;
	}

	public static double getHealingBonus(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		String doubleParse = ChatColor.stripColor(lore.get(3)).replace("Healing Bonus:", "").replace("%", "").replace(" ","");
		Double healBonus = Double.parseDouble(doubleParse)*0.01;
		return healBonus;
	}

	public static int getProtBonus(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return 0;
		}
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		String intParse = ChatColor.stripColor(lore.get(4)).replace("Additional Protection:", "").replace("Damage/Hit", "").replace(" ","");
		int protBonus = Integer.parseInt(intParse);
		return protBonus;
	}
	public static void setMagicResistRating(double rating, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(2);
		lore.add(2, ChatColor.GRAY + "Magical Resistance Rating: " + dF.format(rating) + "%");
		meta.setLore(lore);
		armor.setItemMeta(meta);
		return;
	}
	public static void setHealingBonus(double amount, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(3);
		lore.add(3,ChatColor.GRAY + "Healing Bonus: " + dF.format(amount) + "%");
		meta.setLore(lore);
		armor.setItemMeta(meta);
		return;
	}
	public static void setProtBonus(int bonusProt, ItemStack armor) {
		ItemMeta meta = armor.getItemMeta();
		List<String> lore = meta.getLore();
		lore.remove(4);
		lore.add(4, ChatColor.GRAY + "Additional Protection: " + bonusProt + " Damage/Hit");
		meta.setLore(lore);
		armor.setItemMeta(meta);
		return;
	}
}
