package net.swagserv.andrew2060.heroesenchants;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class PickaxeEfficiencyListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onPickAxeUse(BlockBreakEvent event) {
		if(event.isCancelled()) {
			return;
		}
		ItemStack hand = event.getPlayer().getInventory().getItemInHand();
		int maxDurability = 0;
		switch(hand.getType()) {
		default:
			return;
		case DIAMOND_PICKAXE: 
			maxDurability = 1562;
			break;
		case IRON_PICKAXE: 
			maxDurability = 251;
			break;
		case GOLD_PICKAXE:
			maxDurability = 33;
			break;
		case STONE_PICKAXE:
			maxDurability = 132;
			break;
		case WOOD_PICKAXE: 
			maxDurability = 60;
			break;
		}
		int durability = maxDurability - hand.getDurability();
		if( durability > maxDurability * 1.68) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
			return;
		}
		if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
			return;
		}
		if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
			return;
		}
		if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
			return;
		}
		if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
			return;
		}
        if(durability < maxDurability*1.0) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			return;
        }
        return;
		
	}
}
