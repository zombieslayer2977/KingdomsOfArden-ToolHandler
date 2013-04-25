package net.swagserv.andrew2060.heroestools.durability;

import net.swagserv.andrew2060.heroestools.util.Util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class ToolEfficiencyListener implements Listener{
	@EventHandler(priority = EventPriority.MONITOR)
	public void onHoeWeapon(WeaponDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
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
			return;
		default: 
			return;			
		}
		return;
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onToolUse(BlockBreakEvent event) {
		if(event.isCancelled()) {
			return;
		}
		ItemStack hand = event.getPlayer().getInventory().getItemInHand();
		int maxDurability = Util.getToolMaxDurability(hand.getType());
		if(maxDurability == -1) {
			return;
		}
		int durability = maxDurability - hand.getDurability();
		if( durability > maxDurability * 0.84) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 5);
			return;
		}
		if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 4);
			return;
		}
		if(maxDurability * 0.68 > durability && durability > maxDurability * 0.52) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 3);
			return;
		}
		if(maxDurability * 0.52 > durability && durability > maxDurability * 0.36) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 2);
			return;
		}
		if(maxDurability * 0.36 > durability && durability > maxDurability*0.20) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			hand.addUnsafeEnchantment(Enchantment.DIG_SPEED, 1);
			return;
		}
        if(durability < maxDurability*0.20) {
			hand.removeEnchantment(Enchantment.DIG_SPEED);
			return;
        }
        return;
		
	}
}
