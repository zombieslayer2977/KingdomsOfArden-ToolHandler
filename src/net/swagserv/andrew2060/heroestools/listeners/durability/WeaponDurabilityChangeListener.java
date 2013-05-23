package net.swagserv.andrew2060.heroestools.listeners.durability;

import net.swagserv.andrew2060.heroestools.util.ImprovementUtil;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class WeaponDurabilityChangeListener implements Listener{
	//Handle Updating on Weapon Hit
	@EventHandler(priority=EventPriority.MONITOR)
	public void onWeaponDamageToUser(WeaponDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		ItemStack i = (((Hero)(event.getDamager())).getPlayer().getItemInHand());

		switch(i.getType()) {
		case DIAMOND_SWORD: 
		case IRON_SWORD: 
		case GOLD_SWORD: 
		case STONE_SWORD: 
		case WOOD_SWORD: {
			double quality = ImprovementUtil.reduceQuality(i, ImprovementUtil.getItemType(i));
			ImprovementUtil.applyEnchantmentLevel(i, Enchantment.DAMAGE_ALL,quality);
			return;
		}
		case BOW: {
			double quality = ImprovementUtil.reduceQuality(i, ImprovementUtil.getItemType(i));
			ImprovementUtil.applyEnchantmentLevel(i, Enchantment.ARROW_DAMAGE,quality);
			return;
		}
		default:
			return;
		}
	}
}	
