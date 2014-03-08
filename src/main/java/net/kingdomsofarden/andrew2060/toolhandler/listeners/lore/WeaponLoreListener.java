package net.kingdomsofarden.andrew2060.toolhandler.listeners.lore;

import java.util.Random;

import net.kingdomsofarden.andrew2060.toolhandler.ToolHandlerPlugin;
import net.kingdomsofarden.andrew2060.toolhandler.cache.types.CachedWeaponInfo;
import net.kingdomsofarden.andrew2060.toolhandler.events.CriticalStrikeEvent;

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
		bonusDamage *= 0.01;
		bonusDamage += 1;
		event.setDamage(event.getDamage() * bonusDamage);
	}
	//Life Steal Handler
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onWeaponDamageLSAndCrit(WeaponDamageEvent event) {
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
		CachedWeaponInfo cached = plugin.getCacheManager().getCachedWeaponInfo(i);
		double critchance = cached.getCritChance();
        Random randGen = new Random();
        double rand = randGen.nextInt(10000)*0.01;
        if(rand < critchance) {
            //Fire a CriticalStrikeEvent
            CriticalStrikeEvent cEvent = new CriticalStrikeEvent(event.getDamage()*1.5, event, p,i,cached.getMods());
            cEvent.callEvent();
            if(!cEvent.isCancelled()) {
                event.setDamage(cEvent.getDamage());
                //Play an iron door bashing sound
                event.getAttackerEntity().getLocation().getWorld().playEffect(event.getEntity().getLocation(), Effect.ZOMBIE_CHEW_IRON_DOOR, null);
            }
        }
		double ls = 0;
		ls = cached.getLifeSteal();
		
		HeroRegainHealthEvent healingEvent = new HeroRegainHealthEvent((Hero) event.getDamager(), event.getDamage()*ls*0.01, null);
		Bukkit.getPluginManager().callEvent(healingEvent);
		try {
			p.setHealth(p.getHealth() + healingEvent.getAmount());
		} catch (IllegalArgumentException e) {
			p.setHealth(p.getMaxHealth());
		}
		return;
	}
}
