package net.swagserv.andrew2060.heroestools.durability;

import net.swagserv.andrew2060.heroestools.util.Util;

import org.bukkit.enchantments.Enchantment;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class WeaponSharpnessListener implements Listener{
	//Handle Durability Based Change of Enchantment Levels
	@EventHandler(priority=EventPriority.MONITOR)
	public void onWeaponDamageToUser(WeaponDamageEvent event) {
		if(event.isCancelled()) {
			return;
		}
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		ItemStack i = (((Hero)(event.getDamager())).getPlayer().getItemInHand());
		int maxDurability = Util.getToolMaxDurability(i.getType());
		if(maxDurability == -1) {
			return;
		}
		int durability = maxDurability - i.getDurability();
		switch(i.getType()) {
		case DIAMOND_SWORD: case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD:
			if( durability > maxDurability * 0.84) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
			}
			if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
			}
			if(maxDurability * 0.68 > durability && durability > maxDurability * 0.52) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			}
			if(maxDurability * 0.52 > durability && durability > maxDurability * 0.36) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			}
			if(maxDurability * 0.36 > durability && durability > maxDurability*0.20) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			}
			if(durability < maxDurability*0.20) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				return;
			}         	     
			return;
		case BOW:
			if( durability > maxDurability * 0.84) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
			}
			if(maxDurability * 0.84 > durability && durability > maxDurability * 0.68) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 4);
			}
			if(maxDurability * 0.68 > durability && durability > maxDurability * 0.52) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
			}
			if(maxDurability * 0.52 > durability && durability > maxDurability * 0.36) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			}
			if(maxDurability * 0.36 > durability && durability > maxDurability*0.20) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			}
			if(durability < maxDurability*0.20) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				return;
			}         	     
			return;
		default:
			return;
		}
	}
}	
