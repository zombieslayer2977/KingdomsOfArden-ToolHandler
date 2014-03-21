package net.kingdomsofarden.andrew2060.toolhandler.util;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;

public class ImprovementUtil {
    public static Material getItemType(ItemStack item) {
        switch(item.getType()) {
        case DIAMOND_SWORD:
        case DIAMOND_AXE:
        case DIAMOND_HOE:
        case DIAMOND_SPADE:
        case DIAMOND_PICKAXE:
        case DIAMOND_HELMET:
        case DIAMOND_CHESTPLATE:
        case DIAMOND_LEGGINGS:
        case DIAMOND_BOOTS:
            return Material.DIAMOND;
        case IRON_SWORD:
        case IRON_AXE:
        case IRON_HOE:
        case IRON_SPADE:
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
        case GOLD_SPADE:
        case GOLD_PICKAXE:
        case GOLD_HELMET:
        case GOLD_CHESTPLATE:
        case GOLD_LEGGINGS:
        case GOLD_BOOTS:
            return Material.GOLD_INGOT;
        case STONE_SWORD:
        case STONE_AXE:
        case STONE_HOE:
        case STONE_SPADE:
        case STONE_PICKAXE:
            return Material.STONE;
        case WOOD_SWORD:
        case WOOD_AXE:
        case WOOD_HOE:
        case WOOD_SPADE:
        case WOOD_PICKAXE:
            return Material.WOOD;
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
