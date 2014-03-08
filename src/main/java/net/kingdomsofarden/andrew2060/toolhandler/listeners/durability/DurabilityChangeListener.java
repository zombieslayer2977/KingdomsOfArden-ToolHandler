package net.kingdomsofarden.andrew2060.toolhandler.listeners.durability;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedItemInfo;
import net.kingdomsofarden.andrew2060.toolhandler.util.ImprovementUtil;
import net.kingdomsofarden.andrew2060.toolhandler.util.NbtUtil.ItemStackChangedException;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerItemDamageEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;


public class DurabilityChangeListener implements Listener {
    ToolHandlerPlugin plugin;

    public DurabilityChangeListener(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
    }

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
            CachedItemInfo cached = plugin.getCacheManager().getCachedInfo(hand);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DAMAGE_ALL,cached.getQuality());
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DIG_SPEED,cached.getQuality());
            }
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
            CachedItemInfo cached = plugin.getCacheManager().getCachedArmorInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.PROTECTION_ENVIRONMENTAL, cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.PROTECTION_ENVIRONMENTAL, cached.getQuality());
            return;
        }
        case DIAMOND_SWORD: 
        case IRON_SWORD: 
        case GOLD_SWORD: 
        case STONE_SWORD:
        case WOOD_SWORD: {
            CachedItemInfo cached = plugin.getCacheManager().getCachedWeaponInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DAMAGE_ALL,cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DAMAGE_ALL,cached.getQuality());
            return;
        }
        case DIAMOND_HOE: 
        case IRON_HOE: 
        case GOLD_HOE: 
        case STONE_HOE:
        case WOOD_HOE: {
            CachedItemInfo cached = plugin.getCacheManager().getCachedInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DIG_SPEED, cached.getQuality());
                ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DAMAGE_ALL, cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DIG_SPEED, cached.getQuality());
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DAMAGE_ALL, cached.getQuality());
            return;
        }
        case BOW: {
            CachedItemInfo cached = plugin.getCacheManager().getCachedWeaponInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.ARROW_DAMAGE,cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.ARROW_DAMAGE,cached.getQuality());

            return;
        }
        case DIAMOND_AXE: 
        case IRON_AXE: 
        case GOLD_AXE: 
        case STONE_AXE:
        case WOOD_AXE: {
            CachedItemInfo cached = plugin.getCacheManager().getCachedInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DIG_SPEED, cached.getQuality());
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DAMAGE_ALL, cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DIG_SPEED, cached.getQuality());
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DAMAGE_ALL, cached.getQuality());
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
            CachedItemInfo cached = plugin.getCacheManager().getCachedInfo(item);
            try {
                cached.reduceQuality();
            } catch (ItemStackChangedException e) {
                ImprovementUtil.applyEnchantmentLevel(e.newStack, Enchantment.DIG_SPEED, cached.getQuality());
            }
            ImprovementUtil.applyEnchantmentLevel(item, Enchantment.DIG_SPEED, cached.getQuality());
            return;
        }
        default: {
            return;
        }

        }
    }

}
