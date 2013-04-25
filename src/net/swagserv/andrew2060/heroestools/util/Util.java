package net.swagserv.andrew2060.heroestools.util;

import org.bukkit.Material;

public class Util {
	public static int getArmorMaxDurability(Material type) {
		switch(type) {
		case DIAMOND_HELMET: 
			return 364;
		case IRON_HELMET: 
			return 166;
		case GOLD_HELMET: 
			return 78;
		case CHAINMAIL_HELMET:
			return 166;
		case LEATHER_HELMET:
			return 56;
		case DIAMOND_CHESTPLATE: 
			return 529;	
		case IRON_CHESTPLATE: 
			return 242;	
		case GOLD_CHESTPLATE: 
			return 114;	
		case CHAINMAIL_CHESTPLATE: 
			return 242;	
		case LEATHER_CHESTPLATE:
			return 82;
		case DIAMOND_LEGGINGS: 
			return 496;
		case IRON_LEGGINGS: 
			return 226;
		case GOLD_LEGGINGS: 
			return 106;
		case CHAINMAIL_LEGGINGS:
			return 226;
		case LEATHER_LEGGINGS:
			return 76;
		case DIAMOND_BOOTS: 
			return 430;
		case IRON_BOOTS: 
			return 196;
		case GOLD_BOOTS: 
			return 92;
		case CHAINMAIL_BOOTS: 
			return 196;
		case LEATHER_BOOTS:
			return 66;
		default: 
			return -1;
		}
	}
	public static int getToolMaxDurability(Material type) {
		switch(type) {
		case DIAMOND_HOE:
		case DIAMOND_PICKAXE:
		case DIAMOND_AXE:
		case DIAMOND_SPADE:
		case DIAMOND_SWORD:
			return 1562;	
		case IRON_HOE:
		case IRON_PICKAXE: 
		case IRON_AXE:
		case IRON_SPADE:
		case IRON_SWORD: 
			return 251;
		case GOLD_HOE:
		case GOLD_PICKAXE:
		case GOLD_AXE:
		case GOLD_SPADE:
		case GOLD_SWORD:
			return 33;
		case STONE_HOE:
		case STONE_PICKAXE:
		case STONE_AXE:
		case STONE_SPADE:
		case STONE_SWORD:
			return 132;
		case WOOD_HOE:
		case WOOD_PICKAXE: 
		case WOOD_AXE:
		case WOOD_SPADE: 
		case WOOD_SWORD:
			return 60;
		case BOW: 
			return 385;
		default: 
			return -1;
		}
	}
	public static int getMaxDurability(Material type) {
		int max = getToolMaxDurability(type);
		if(max == -1) {
			max = getArmorMaxDurability(type);
		}
		return max;
	}
}
