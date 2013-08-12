package net.kingdomsofarden.andrew2060.toolhandler.listeners.effects;

import net.kingdomsofarden.andrew2060.toolhandler.effects.ClientEffectSender;
import net.kingdomsofarden.andrew2060.toolhandler.effects.ClientEffectType;

import org.bukkit.entity.LivingEntity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;

import com.herocraftonline.heroes.api.events.HeroRegainHealthEvent;

/**
 * Simple listener to play wolf hearts on heal for the sake of visibility
 * @author Andrew
 */

public class HealingEffectListener implements Listener {
	@EventHandler(priority = EventPriority.MONITOR, ignoreCancelled = true)
	public void onHeroRegainHealth(HeroRegainHealthEvent event) {
		if(!(event.getAmount() > 0)) {
			return;
		}
		LivingEntity lE = (LivingEntity) event.getHero().getEntity();
		if(lE.getHealth() == lE.getMaxHealth()) {
			return;
		}
		ClientEffectSender.playClientEffect(ClientEffectType.HEART, lE.getLocation(), 1F, 10);
	}

}
