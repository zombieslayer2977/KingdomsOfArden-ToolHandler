package net.kingdomsofarden.andrew2060.toolhandler.listeners.durability;

import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;


public class DurabilityChangeListener implements Listener {
    //Necessary due to hoe not losing durability when used as a weapon
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onHoeWeapon(WeaponDamageEvent event) {
        if(!(event.getDamager() instanceof Hero)) {
            return;
        }
        Hero h = (Hero) event.getDamager();
        ItemStack hand = h.getPlayer().getInventory().getItemInHand();
        switch(hand.getType()) {
        case DIAMOND_HOE:
        case IRON_HOE:
        case GOLD_HOE:
        case STONE_HOE:
        case WOOD_HOE:
            hand.setDurability((short) (hand.getDurability() + 1));
            double quality = ImprovementUtil.reduceQuality(hand, ImprovementUtil.getItemType(hand));
            ImprovementUtil.applyEnchantmentLevel(hand, Enchantment.DIG_SPEED, quality);
            return;
        default: 
            return;         
        }
    }
    @EventHandler(ignoreCancelled = true, priority = EventPriority.MONITOR)
    public void onDurabilityChange(PlayerItemDamageEvent event) {
        ItemStack item = event.getItem();
        switch(item.getType()) {
        
        case DIAMOND_HELMET: 
        case IRON_HELMET: 
        case CHAINMAIL_HELMET: 
        case GOLD_HELMET: 
        case LEATHER_HELMET: 
        case DIAMOND_CHESTPLATE:
        case IRON_CHESTPLATE:
        case CHAINMAIL_CHESTPLATE:
        case GOLD_CHESTPLATE:
        case LEATHER_CHESTPLATE:
        case DIAMOND_LEGGINGS:
        case IRON_LEGGINGS:
        case CHAINMAIL_LEGGINGS:
        case GOLD_LEGGINGS:
        case LEATHER_LEGGINGS:
        case DIAMOND_BOOTS:
        case IRON_BOOTS:
        case CHAINMAIL_BOOTS:
        case GOLD_BOOTS:
        case LEATHER_BOOTS: {
            double quality = ImprovementUtil.reduceQuality(item, ImprovementUtil.getItemType(item));
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.PROTECTION_ENVIRONMENTAL, quality);
            return;
        }
        case DIAMOND_SWORD: 
        case IRON_SWORD: 
        case GOLD_SWORD: 
        case STONE_SWORD:
        case WOOD_SWORD: 
        case DIAMOND_AXE: 
        case IRON_AXE: 
        case GOLD_AXE: 
        case STONE_AXE:
        case WOOD_AXE: 
        case DIAMOND_HOE: 
        case IRON_HOE: 
        case GOLD_HOE: 
        case STONE_HOE:
        case WOOD_HOE: {
            double quality = ImprovementUtil.reduceQuality(item, ImprovementUtil.getItemType(item));
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DAMAGE_ALL,quality);
            return;
        }
        case BOW: {
            double quality = ImprovementUtil.reduceQuality(item, ImprovementUtil.getItemType(item));
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.ARROW_DAMAGE,quality);
            return;
        }
        case DIAMOND_PICKAXE:
        case IRON_PICKAXE:
        case GOLD_PICKAXE:
        case STONE_PICKAXE:
        case WOOD_PICKAXE:
        case DIAMOND_SPADE:
        case IRON_SPADE:
        case GOLD_SPADE:
        case STONE_SPADE:
        case WOOD_SPADE: {
            double quality = ImprovementUtil.reduceQuality(item, ImprovementUtil.getItemType(item));
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DIG_SPEED, quality);
            return;
        }
        default: {
            return;
        }
        
        }
    }

}
