package net.kingdomsofarden.andrew2060.toolhandler.listeners.lore;

import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.events.CriticalStrikeEvent;
import net.kingdomsofarden.andrew2060.toolhandler.util.ModUtil;

import org.bukkit.Bukkit;
import org.bukkit.Effect;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;
import com.herocraftonline.heroes.api.events.WeaponDamageEvent;
import com.herocraftonline.heroes.characters.Hero;

public class WeaponLoreListener implements Listener {
    
    ToolHandlerPlugin plugin;
    public WeaponLoreListener(ToolHandlerPlugin plugin) {
        this.plugin = plugin;
    }
    
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
		double bonusDamage = plugin.getCacheManager().getCachedWeaponInfo(i).getBonusDamage();
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
		double ls = 0;
		ls = plugin.getCacheManager().getCachedWeaponInfo(i).getLifeSteal();
		HeroRegainHealthEvent healingEvent = new HeroRegainHealthEvent((Hero) event.getDamager(), event.getDamage()*ls*0.01, null);
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
		double critchance = plugin.getCacheManager().getCachedWeaponInfo(i).getCritChance();
		Random randGen = new Random();
		double rand = randGen.nextInt(10000)*0.01;
		if(rand < critchance) {
			//Fire a CriticalStrikeEvent
			CriticalStrikeEvent cEvent = new CriticalStrikeEvent(event.getDamage()*1.5, event, p,i,ModUtil.getWeaponMods(i));
			cEvent.callEvent();
			if(!cEvent.isCancelled()) {
				event.setDamage(cEvent.getDamage());
				//Play an iron door bashing sound
				event.getAttackerEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, null);
			}
		}
	}
}
