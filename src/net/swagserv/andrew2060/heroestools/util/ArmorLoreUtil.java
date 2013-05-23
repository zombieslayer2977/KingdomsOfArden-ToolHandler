package net.swagserv.andrew2060.heroestools.util;

import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ArmorLoreUtil {

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
}
