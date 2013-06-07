package net.swagserv.andrew2060.toolhandler.listeners.effects;

import org.bukkit.EntityEffect;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Wolf;
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
		LivingEntity lE = event.getHero().getEntity();
		Wolf w = (Wolf) lE.getWorld().spawnEntity(lE.getLocation(), EntityType.WOLF);
		w.playEffect(EntityEffect.WOLF_HEARTS);
		w.remove();
	}

}
