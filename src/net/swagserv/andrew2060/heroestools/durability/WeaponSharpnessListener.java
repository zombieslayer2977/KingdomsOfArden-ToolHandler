package net.swagserv.andrew2060.heroestools.durability;

import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.CaveSpider;
import org.bukkit.entity.Player;
import org.bukkit.entity.Skeleton;
import org.bukkit.entity.Spider;
import org.bukkit.entity.Zombie;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.Heroes;
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
		int maxDurability = 0;
		int durability;
		switch(i.getTypeId()) {
		case 276: case 267: case 283: case 272: case 268:
			switch(i.getTypeId()) {
			case 276:
				maxDurability = 1562;
				break;
			case 267:
				maxDurability = 251;
				break;
			case 283: 
				maxDurability = 33;
				break;
			case 272:
				maxDurability = 130;
				break;
			case 268:
				maxDurability = 60;
				break;
			}
			durability = maxDurability - i.getDurability();
			if( durability > maxDurability * 1.68) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 5);
			}
			if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 4);
			}
			if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 3);
			}
			if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 2);
			}
			if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				i.addUnsafeEnchantment(Enchantment.DAMAGE_ALL, 1);
			}
			if(durability < maxDurability*1.0) {
				i.removeEnchantment(Enchantment.DAMAGE_ALL);
				return;
			}         	     
			break;
		case 261:
			maxDurability = 385;
			durability = maxDurability - i.getDurability();
			if( durability > maxDurability * 1.68) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 5);
			}
			if(maxDurability * 1.68 > durability && durability > maxDurability * 1.51) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 4);
			}
			if(maxDurability * 1.51 > durability && durability > maxDurability * 1.34) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 3);
			}
			if(maxDurability * 1.34 > durability && durability > maxDurability * 1.17) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 2);
			}
			if(maxDurability * 1.17 > durability && durability > maxDurability*1.0) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);
				i.addUnsafeEnchantment(Enchantment.ARROW_DAMAGE, 1);
			}
			if(durability < maxDurability*1.0) {
				i.removeEnchantment(Enchantment.ARROW_DAMAGE);   	
			}     
			break;
		}
		double dmg = event.getDamage();
		switch (i.getTypeId()) {
		//Sword Handling For Enchants
		case 276: case 267: case 283: case 272: case 268:
			Map<Enchantment, Integer> appliedEnchantments = i.getEnchantments();
			//Sharpness Handling
			if(appliedEnchantments.containsKey(Enchantment.DAMAGE_ALL)) {
				int enchLevel = appliedEnchantments.get(Enchantment.DAMAGE_ALL);
				double sharpnessModifier = 1+(enchLevel)*4*0.01;
				dmg =(dmg*sharpnessModifier);	
			}
			//Fire Aspect Handling
			if(appliedEnchantments.containsKey(Enchantment.FIRE_ASPECT)) {
				int enchLevel = appliedEnchantments.get(Enchantment.FIRE_ASPECT);
				event.getEntity().setFireTicks(15*enchLevel);

			}
			//Smite Handling
			if(appliedEnchantments.containsKey(Enchantment.DAMAGE_UNDEAD)) {
				int enchLevel = appliedEnchantments.get(Enchantment.DAMAGE_UNDEAD);
				if(event.getEntity() instanceof Skeleton || event.getEntity() instanceof Zombie) {
					dmg = dmg+enchLevel;
				}
			}
			//Arthropod Bane Handling
			if(appliedEnchantments.containsKey(Enchantment.DAMAGE_ARTHROPODS)) {
				int enchLevel = appliedEnchantments.get(Enchantment.DAMAGE_ARTHROPODS);
				if(event.getEntity() instanceof Spider || event.getEntity() instanceof CaveSpider) {
					dmg = dmg+enchLevel;
				}
			}
		case 261:
			//Power Bow Handling
			int powerMultiplier = i.getEnchantmentLevel(Enchantment.ARROW_DAMAGE);
			dmg = (dmg*(1+powerMultiplier*4*0.01));
		}
		event.setDamage((int) dmg);
	}
}	
