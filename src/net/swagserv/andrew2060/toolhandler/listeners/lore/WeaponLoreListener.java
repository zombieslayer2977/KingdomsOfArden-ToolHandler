package net.swagserv.andrew2060.toolhandler.listeners.lore;

import java.util.Random;

import net.swagserv.andrew2060.toolhandler.events.CriticalStrikeEvent;
import net.swagserv.andrew2060.toolhandler.util.GeneralLoreUtil;
import net.swagserv.andrew2060.toolhandler.util.ImprovementUtil;
import net.swagserv.andrew2060.toolhandler.util.WeaponLoreUtil;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class WeaponLoreListener implements Listener {
	//Bonus Damage Handler
	@EventHandler(priority = EventPriority.LOWEST, ignoreCancelled = true)
	public void onWeaponDamageDmg(WeaponDamageEvent event) {
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		Player p = ((Hero) event.getDamager()).getPlayer();
		ItemStack i = p.getItemInHand();
		switch(i.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return;
			}
		}
		int bonusDamage = 0;
		try {
			bonusDamage = WeaponLoreUtil.getBonusDamage(i);
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			double quality = ImprovementUtil.getQuality(i);
			GeneralLoreUtil.populateLore(i);
			ImprovementUtil.setQuality(i, quality);
		}
		event.setDamage(event.getDamage() + bonusDamage);
	}
	//Life Steal Handler
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeaponDamageLS(WeaponDamageEvent event) {
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		Player p = ((Hero) event.getDamager()).getPlayer();
		ItemStack i = p.getItemInHand();
		switch(i.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return;
			}
		}
		int ls = 0;
		//Update Code
		//TODO: Remove in future for performance reasons
		try {
			ls = WeaponLoreUtil.getLifeSteal(i);
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			double quality = ImprovementUtil.getQuality(i);
			GeneralLoreUtil.populateLore(i);
			ImprovementUtil.setQuality(i, quality);
		}
		HeroRegainHealthEvent healingEvent = new HeroRegainHealthEvent((Hero) event.getDamager(), ls, null);
		Bukkit.getPluginManager().callEvent(healingEvent);
		try {
			p.setHealth(p.getHealth() + healingEvent.getAmount());
		} catch (IllegalArgumentException e) {
			p.setHealth(p.getMaxHealth());
		}
		return;
	}
	//Crit Chance Handler
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeaponDamageCrit(WeaponDamageEvent event) {
		if(!(event.getDamager() instanceof Hero)) {
			return;
		}
		Player p = ((Hero)event.getDamager()).getPlayer();
		ItemStack i = p.getItemInHand();
		switch(i.getType()) {
			case DIAMOND_SWORD:	case IRON_SWORD: case GOLD_SWORD: case STONE_SWORD: case WOOD_SWORD: case BOW: {
				break;
			}
			default: {
				return;
			}
		}
		double critchance = 0;
		//Update Code
		//TODO: Remove in future for performance reasons
		try {
			critchance = WeaponLoreUtil.getCritChance(i);
		} catch (NumberFormatException | IndexOutOfBoundsException | NullPointerException e) {
			double quality = ImprovementUtil.getQuality(i);
			GeneralLoreUtil.populateLore(i);
			ImprovementUtil.setQuality(i, quality);
		}
		Random randGen = new Random();
		double rand = randGen.nextInt(10000)*0.01;
		if(rand <= critchance) {
			//Fire a CriticalStrikeEvent
			CriticalStrikeEvent cEvent = new CriticalStrikeEvent((int) (event.getDamage()*1.2), event, p,i);
			cEvent.callEvent();
			if(!cEvent.isCancelled()) {
				event.setDamage(cEvent.getDamage());
			}
		}
	}
}
