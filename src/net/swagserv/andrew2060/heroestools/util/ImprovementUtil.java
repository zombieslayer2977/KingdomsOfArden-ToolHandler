package net.swagserv.andrew2060.heroestools.util;

import java.text.DecimalFormat;
import java.util.List;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import com.herocraftonline.heroes.util.Util;

public class ImprovementUtil {
	static DecimalFormat dF = new DecimalFormat("###.##");
	public static Material getItemType(ItemStack item) {
		switch(item.getType()) {
		case DIAMOND_SWORD:
		case DIAMOND_AXE:
		case DIAMOND_HOE:
		case DIAMOND_PICKAXE:
		case DIAMOND_HELMET:
		case DIAMOND_CHESTPLATE:
		case DIAMOND_LEGGINGS:
		case DIAMOND_BOOTS:
			return Material.DIAMOND;
		case IRON_SWORD:
		case IRON_AXE:
		case IRON_HOE:
		case IRON_PICKAXE:
		case IRON_HELMET:
		case IRON_CHESTPLATE:
		case IRON_LEGGINGS:
		case IRON_BOOTS:
		case CHAINMAIL_HELMET:
		case CHAINMAIL_CHESTPLATE:
		case CHAINMAIL_LEGGINGS:
		case CHAINMAIL_BOOTS:
			return Material.IRON_INGOT;
		case GOLD_SWORD:
		case GOLD_AXE:
		case GOLD_HOE:
		case GOLD_PICKAXE:
		case GOLD_HELMET:
		case GOLD_CHESTPLATE:
		case GOLD_LEGGINGS:
		case GOLD_BOOTS:
			return Material.GOLD_INGOT;
		case STONE_SWORD:
		case STONE_AXE:
		case STONE_HOE:
		case STONE_PICKAXE:
			return Material.STONE;
		case LEATHER_HELMET:
		case LEATHER_CHESTPLATE:
		case LEATHER_LEGGINGS:
		case LEATHER_BOOTS:
			return Material.LEATHER;
		case BOW:
			return Material.BOW;
		default: 
			return Material.AIR;
		}
	}
	public static void setQuality(ItemStack item, double quality) {
		if(!(Util.isWeapon(item.getType()) || Util.isArmor((item.getType())))) {
			return;
		}
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(item);
		}
		List<String> lore = meta.getLore();
		String toAdd = "Improvement Quality: " + dF.format(quality) + "%";
		lore.remove(0);
		lore.add(0, toAdd);
		meta.setLore(lore);
		item.setItemMeta(meta);
	}
	public static double reduceQuality(ItemStack item, Material mat) {
		if(mat.equals(Material.AIR)) {
			return 0;
		}
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(item);
		}
		List<String> lore = meta.getLore();
		if(lore == null) {
			GeneralLoreUtil.populateLore(item);
		}
		if(lore.isEmpty()) {
			lore.add("Improvement Quality: 0.00%");
			meta.setLore(lore);
			item.setItemMeta(meta);
			return 0;
		} else {
			String toAdd = lore.get(0);
			toAdd = toAdd.replace("Improvement Quality: ", "")
				.replace("%", "");
			double quality = Double.parseDouble(toAdd);
			if(quality == 0) {
				return 0;
			}
			switch(mat) {
			case DIAMOND: 
				quality -= 0.1;
				break;
			case IRON_INGOT:
				quality -= 0.2;
				break;
			case GOLD_INGOT:
				quality -= 1.0;
				break;
			case LEATHER: 
				quality -= 0.8;
				break;
			case STONE: 
				quality -= 0.5;
				break;
			case BOW:
				quality -= 0.5;
			default: 
				System.err.println("Material Sent to Blacksmith is Invalid");
			}
			if(quality < 0) {
				quality = 0;
			}
			toAdd = "Improvement Quality: " + dF.format(quality) + "%";
			lore.remove(0);
			lore.add(0, toAdd);
			meta.setLore(lore);
			item.setItemMeta(meta);
			return quality;
		}

	}
	public static double improveQuality(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(item);
		}
		List<String> lore = meta.getLore();
		if(!lore.isEmpty()) {
			String toAdd = lore.get(0);
			toAdd = toAdd.replace("Improvement Quality: ", "")
				.replace("%", "");
			double quality = Double.parseDouble(toAdd);
			if(quality == 100) {
				return -1;
			}
			quality += 2.5;
			if(quality >= 100) {
				quality = 100;
			}
			toAdd = "Improvement Quality: " + dF.format(quality) + "%";
			lore.remove(0);
			lore.add(0, toAdd);
			meta.setLore(lore);
			item.setItemMeta(meta);
			return quality;
		} else {
			GeneralLoreUtil.populateLore(item);
			return 0;
		}
	}
	public static double getQuality(ItemStack item) {
		ItemMeta meta = item.getItemMeta();
		if(!meta.hasLore()) {
			GeneralLoreUtil.populateLore(item);
		}
		List<String> lore = meta.getLore();
		if(!lore.isEmpty()) {
			String quality = lore.get(0);
			quality = quality.replace("Improvement Quality: ", "")
				.replace("%", "");
			return Double.parseDouble(quality);
		} else {
			GeneralLoreUtil.populateLore(item);
			return 0;
		}
	}
	public static void applyEnchantmentLevel(ItemStack item, Enchantment ench) {
		double quality = getQuality(item);
		applyEnchantmentLevel(item, ench, quality);
	}
	public static void applyEnchantmentLevel(ItemStack item, Enchantment ench,
			double quality) {
		if(quality == 0) {
			item.removeEnchantment(ench);
			return;
		} 
		if(quality > 0 && quality <=20) {
			item.addUnsafeEnchantment(ench, 1);
			return;
		}
		if(quality > 20 && quality <= 40) {
			item.addUnsafeEnchantment(ench, 2);
			return;
		}
		if(quality > 40 && quality <= 60) {
			item.addUnsafeEnchantment(ench, 3);
			return;
		}
		if(quality > 60 && quality <= 80) {
			item.addUnsafeEnchantment(ench, 4);
			return;
		}
		if(quality > 80 && quality <= 100) {
			item.addUnsafeEnchantment(ench, 5);
			return;
		}
		return;
	}
}
