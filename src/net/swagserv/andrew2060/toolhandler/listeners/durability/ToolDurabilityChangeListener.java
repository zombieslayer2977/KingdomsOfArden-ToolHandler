package net.swagserv.andrew2060.toolhandler.listeners.durability;

import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;
import net.swagserv.andrew2060.toolhandler.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class ToolDurabilityChangeListener implements Listener{
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
			try {
				double quality = ImprovementUtil.reduceQuality(hand, ImprovementUtil.getItemType(hand));
				ImprovementUtil.applyEnchantmentLevel(hand, Enchantment.DIG_SPEED, quality);
			} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
				double quality = ImprovementUtil.getQuality(hand);
				GeneralLoreUtil.populateLore(hand);
				ImprovementUtil.setQuality(hand, quality);
			}
			return;
		default: 
			return;			
		}
	}
	@EventHandler(priority = EventPriority.MONITOR)
	public void onToolUse(BlockBreakEvent event) {
		if(event.isCancelled()) {
			return;
		}
		ItemStack hand = event.getPlayer().getInventory().getItemInHand();
		switch(hand.getType()) {
		case DIAMOND_AXE:
		case DIAMOND_PICKAXE:
		case DIAMOND_HOE:
		case IRON_AXE:
		case IRON_PICKAXE:
		case IRON_HOE:
		case GOLD_AXE:
		case GOLD_PICKAXE:
		case GOLD_HOE:
		case STONE_AXE:
		case STONE_PICKAXE:
		case STONE_HOE:
		case WOOD_AXE:
		case WOOD_PICKAXE:
		case WOOD_HOE:
			try {
				double quality = ImprovementUtil.reduceQuality(hand, ImprovementUtil.getItemType(hand));
				ImprovementUtil.applyEnchantmentLevel(hand, Enchantment.DIG_SPEED, quality);
			} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
				double quality = ImprovementUtil.getQuality(hand);
				GeneralLoreUtil.populateLore(hand);
				ImprovementUtil.setQuality(hand, quality);
			}
			return;
		default:
			return;
		}

		
	}
}
