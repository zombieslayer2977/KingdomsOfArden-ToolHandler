package net.swagserv.andrew2060.heroestools.util;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class GeneralLoreUtil {
	static DecimalFormat dF = new DecimalFormat("###.##");
	private static void populateTool(List<String> templateLoreTool) {
		templateLoreTool.add(0,ChatColor.GRAY + "Improvement Quality: 0.00%");
		templateLoreTool.add(1,ChatColor.WHITE + "========Modifications========");
		templateLoreTool.add(2,ChatColor.DARK_GRAY + "[Empty Slot]");
		templateLoreTool.add(3,ChatColor.DARK_GRAY + "[Empty Slot]");

	}

	private static void populateArmor(List<String> templateLoreArmor) {
		templateLoreArmor.add(0,ChatColor.GRAY + "Improvement Quality: 0.00%");
		templateLoreArmor.add(1,ChatColor.WHITE + "========Statistics========");
		templateLoreArmor.add(2,ChatColor.GRAY + "Magical Resistance Rating: 0.00%");
		templateLoreArmor.add(3,ChatColor.GRAY + "Healing Bonus: 0.00%");
		templateLoreArmor.add(4,ChatColor.GRAY + "Additional Protection: 0 Damage/Hit");
		templateLoreArmor.add(5,ChatColor.WHITE + "========Modifications========");
		templateLoreArmor.add(6,ChatColor.DARK_GRAY + "[Empty Slot]");
		templateLoreArmor.add(7,ChatColor.DARK_GRAY + "[Empty Slot]");
	}
	private static void populateWeapon(List<String> templateLoreWeapon) {
		templateLoreWeapon.add(0,ChatColor.GRAY + "Improvement Quality: 0.00%");
		templateLoreWeapon.add(1,ChatColor.WHITE + "========Statistics========");
		templateLoreWeapon.add(2,ChatColor.GRAY + "Bonus Damage: 0");
		templateLoreWeapon.add(3,ChatColor.GRAY + "Life Steal: 0 Health/Hit");
		templateLoreWeapon.add(4,ChatColor.GRAY + "Critical Strike Chance: 0.00%");
		templateLoreWeapon.add(5,ChatColor.WHITE + "========Modifications========");
		templateLoreWeapon.add(6,ChatColor.DARK_GRAY + "[Empty Slot]");
		templateLoreWeapon.add(7,ChatColor.DARK_GRAY + "[Empty Slot]");
	}
	private static List<String> getToolDefault(ItemStack tool) {
		if(!com.herocraftonline.heroes.util.Util.isWeapon(tool.getType())) {
			return null;
		}
		List<String> defaultLoreTool = new ArrayList<String>();
		populateTool(defaultLoreTool);
		return defaultLoreTool;
	}
	private static List<String> getArmorDefault(ItemStack armor) {
		if(!com.herocraftonline.heroes.util.Util.isArmor(armor.getType())) {
			return null;
		}
		List<String> defaultLoreArmor = new ArrayList<String>();
		populateArmor(defaultLoreArmor);
		switch(armor.getType()) {
		case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: {
			defaultLoreArmor.remove(2);
			defaultLoreArmor.add(2,ChatColor.GRAY + "Magical Resistance Rating: 10.00%");
			break;
		}
		default:
			break;
		}
		return defaultLoreArmor;
	}
	private static List<String> getWeaponDefault(ItemStack weapon) {
		if(!com.herocraftonline.heroes.util.Util.isWeapon(weapon.getType())) {
			return null;
		}
		List<String> defaultLoreWeapon = new ArrayList<String>();
		populateWeapon(defaultLoreWeapon);
		return defaultLoreWeapon;
	}
	public static void populateLore(ItemStack item) {
		Material mat = item.getType();
		switch(mat) {
			case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD:	case WOOD_SWORD: case BOW: {
				ItemMeta meta = item.getItemMeta();
				meta.setLore(getWeaponDefault(item));
				item.setItemMeta(meta);
				return;
			}
			case DIAMOND_PICKAXE: case DIAMOND_AXE: case DIAMOND_HOE: case DIAMOND_SPADE: case IRON_PICKAXE: case IRON_AXE: case IRON_HOE: case IRON_SPADE: case GOLD_PICKAXE: case GOLD_AXE: case GOLD_HOE: case GOLD_SPADE: case STONE_PICKAXE: case STONE_AXE: case STONE_HOE: case STONE_SPADE: case WOOD_PICKAXE: case WOOD_AXE: case WOOD_HOE: case WOOD_SPADE: {
				ItemMeta meta = item.getItemMeta();
				meta.setLore(getToolDefault(item));
				item.setItemMeta(meta);
				return;
			}
			case DIAMOND_HELMET: case DIAMOND_CHESTPLATE: case DIAMOND_LEGGINGS: case DIAMOND_BOOTS: case IRON_HELMET: case IRON_CHESTPLATE: case IRON_LEGGINGS: case IRON_BOOTS: case GOLD_HELMET: case GOLD_CHESTPLATE: case GOLD_LEGGINGS: case GOLD_BOOTS: case CHAINMAIL_HELMET: case CHAINMAIL_CHESTPLATE: case CHAINMAIL_LEGGINGS: case CHAINMAIL_BOOTS: case LEATHER_HELMET: case LEATHER_CHESTPLATE: case LEATHER_LEGGINGS: case LEATHER_BOOTS: {
				ItemMeta meta = item.getItemMeta();
				meta.setLore(getArmorDefault(item));
				item.setItemMeta(meta);
				return;
			}
			default: {
				return;
			}
		}
	}
}
